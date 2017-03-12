package com.mitdy.shopping.sales.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.sales.dto.SerialNumberDTO;
import com.mitdy.shopping.sales.mapper.SerialNumberMapper;

@Service("serialNumberGenerator")
public class SerialNumberGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SerialNumberGenerator.class);
    private static final String DATE_FORMAT = "yyyyMMdd";

    @Autowired
    private SerialNumberMapper serialNumberMapper;

    @Value("${serial.number.prefix}")
    private String prefex;

    @Value("${serial.number.date.pattern}")
    private String datePattern;

    @Value("${serial.number.number.pattern}")
    private String numberPattern;

    @Value("${serial.number.number.initialPoolSize}")
    private int initialPoolSize = 100;

    @Value("${serial.number.number.maxPoolSize}")
    private int maxPoolSize = 200;

    @Value("${serial.number.number.minPoolSize}")
    private int minPoolSize = 50;

    private SimpleDateFormat dateFormatter;

    private DecimalFormat decimalFormat;

    private int activeSize = 0;

    private AtomicBoolean running = new AtomicBoolean(true);

    private Map<String, SerialNumberInfo> serialNumberInfoOfDate = new HashMap<String, SerialNumberInfo>();

    @PostConstruct
    public void init() throws InterruptedException {
        logger.info("SerialNumberGenerator.onCreate()");
        logger.info("prefex: " + prefex);
        logger.info("datePattern: " + datePattern);
        logger.info("numberPattern: " + numberPattern);
        logger.info("initialPoolSize: " + initialPoolSize);
        logger.info("maxPoolSize: " + maxPoolSize);
        
        if (minPoolSize >= maxPoolSize) {
            throw new IllegalArgumentException("minPoolSize '" + minPoolSize + "' should be less than maxPoolSize '" + maxPoolSize + "'");
        }

        dateFormatter = new SimpleDateFormat(datePattern);
        decimalFormat = new DecimalFormat(numberPattern);

        initSerialNumberInfos();

        asynchronizeGenerateNumbers();
    }

    public synchronized String getNextSerialNumber() {
        try {

            Date today = new Date();
            String nextSerialNumber = serialNumberInfoOfDate.get(formatDate(today)).numberQueue.take();

            logger.info("nextSerialNumber: {}", nextSerialNumber);
            SerialNumberDTO serialNumberDTO = new SerialNumberDTO();
            serialNumberDTO.setSerialNumberDate(today);
            serialNumberMapper.updateCurrentSerialNumber(serialNumberDTO);

            return nextSerialNumber;
        } catch (InterruptedException e) {
            logger.info("getNextSerialNumber occurred exception: " + e);

            throw new RuntimeException(e);
        }
    }

    private void initSerialNumberInfos() {
        Date today = new Date();
        for (int day = 0; day <= 1; day++) {
            Date targetDay = DateUtils.addDays(today, day);
            serialNumberInfoOfDate.put(formatDate(targetDay), new SerialNumberInfo(new LinkedBlockingQueue<String>(maxPoolSize), false,
                    targetDay));
        }
    }

    @Transactional
    private void batchGenerateSerialNumbers(Date currentDay, int count) throws InterruptedException {
        logger.info("batchGenerateSerialNumbers: " + count);

        if (count > 0) {

            SerialNumberDTO serialNumberDTO =  serialNumberMapper.querySerialNumberByDate(currentDay);
            
            SerialNumberInfo serialNumberInfo = serialNumberInfoOfDate.get(formatDate(currentDay));
            long nextNumber = 1;

            if (serialNumberDTO == null) {
                String serialNo = new StringBuffer(prefex).append(dateFormatter.format(currentDay))
                        .append(decimalFormat.format(nextNumber)).toString();

                serialNumberDTO = new SerialNumberDTO();
                serialNumberDTO.setSerialNumberId(UUID.randomUUID().toString());
                serialNumberDTO.setGeneratedSerialNumber(nextNumber);
                serialNumberDTO.setCurrentSerialNumber(0L);
                serialNumberDTO.setSerialNumberDate(currentDay);
                serialNumberMapper.saveSerialNumber(serialNumberDTO);

                serialNumberInfo.numberQueue.put(serialNo);
                count--;

            } else {

                if (!serialNumberInfo.reload) {
                    nextNumber = serialNumberDTO.getCurrentSerialNumber();
                } else {
                    nextNumber = serialNumberDTO.getGeneratedSerialNumber();
                }

                logger.info("find serialNumberDTO: " + serialNumberDTO);
            }

            serialNumberInfo.reload = true;

            if (count > 0) {

                logger.info("start to add serialNo to numberQueue, nextNumber: {}", nextNumber);

                List<String> tmpList = new ArrayList<String>();

                while (count > 0) {
                    nextNumber += 1;
                    String serialNo = new StringBuffer(prefex).append(dateFormatter.format(currentDay))
                            .append(decimalFormat.format(nextNumber)).toString();

                    tmpList.add(serialNo);
                    count--;
                }

                serialNumberDTO.setGeneratedSerialNumber(nextNumber);
                serialNumberMapper.updateSerialNumber(serialNumberDTO);

                logger.info("after executing updateSerialNumber()");

                for (String serialNo : tmpList) {
                    serialNumberInfo.numberQueue.put(serialNo);
                }
                logger.info("after adding serialNo to numberQueue: {}", serialNumberInfo.numberQueue);

            }
        }

    }

    private String formatDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    private void asynchronizeGenerateNumbers() {

        new Thread() {

            @Override
            public void run() {

                while (running.get()) {

                    synchronized (serialNumberInfoOfDate) {

                        Date today = new Date();

                        Iterator<String> iterator = serialNumberInfoOfDate.keySet().iterator();
                        Date truncateToday = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);

                        while (iterator.hasNext()) {
                            String next = iterator.next();

                            if (DateUtils.truncate(serialNumberInfoOfDate.get(next).date, Calendar.DAY_OF_MONTH).before(truncateToday)) {
                                logger.info("remove before day[{}] element", next);

                                iterator.remove();
                            }
                        }

                        SerialNumberInfo serialNumberInfo = serialNumberInfoOfDate.get(formatDate(today));
                        if (serialNumberInfo == null) {
                            serialNumberInfo = new SerialNumberInfo(new LinkedBlockingQueue<String>(maxPoolSize), false, today);
                            serialNumberInfoOfDate.put(formatDate(today), serialNumberInfo);
                        }

                        int currentQueueSize = serialNumberInfo.numberQueue.size();

                        if (currentQueueSize < minPoolSize) {

                            logger.info("asychronizeGenerateNumbers.numberQueue: {}, minPoolSize: {}", currentQueueSize, minPoolSize);

                            int increment = (currentQueueSize == 0) ? initialPoolSize : (maxPoolSize - currentQueueSize);

                            logger.info("increment: {}", increment);

                            try {
                                batchGenerateSerialNumbers(today, increment);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            activeSize = serialNumberInfo.numberQueue.size();
                            logger.info("activeSize: " + activeSize);
                            logger.info("numberQueue: " + serialNumberInfo.numberQueue);
                        }

                    }

//                    try {
//                        Thread.sleep(100L);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                }
            }

        }.start();
    }

    class SerialNumberInfo {

        LinkedBlockingQueue<String> numberQueue;
        boolean reload = false;
        Date date;

        public SerialNumberInfo(LinkedBlockingQueue<String> numberQueue, boolean reload, Date date) {
            this.numberQueue = numberQueue;
            this.reload = reload;
            this.date = date;
        }

    }

}
