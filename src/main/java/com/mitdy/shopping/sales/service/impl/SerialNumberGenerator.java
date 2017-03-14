package com.mitdy.shopping.sales.service.impl;

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
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.sales.dto.SerialNumberDTO;
import com.mitdy.shopping.sales.mapper.SerialNumberMapper;

@Service("serialNumberGenerator2")
public class SerialNumberGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SerialNumberGenerator.class);
	private static final String DATE_FORMAT = "yyyyMMdd";
	public static final int MODE_DB = 0;
	public static final int MODE_QUEUE = 1;

	@Autowired
	private SerialNumberMapper serialNumberMapper;

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Value("${serial.number.mode}")
	private int serialNumberMode = 0;

	@Value("${serial.number.prefix}")
	private String prefex;

	@Value("${serial.number.datePattern}")
	private String datePattern;

	@Value("${serial.number.numberPattern}")
	private String numberPattern;

	@Value("${serial.number.initialPoolSize}")
	private int initialPoolSize = 10;

	@Value("${serial.number.maxPoolSize}")
	private int maxPoolSize = 10;

	@Value("${serial.number.minPoolSize}")
	private int minPoolSize = 5;

	private SimpleDateFormat dateFormatter;

	private DecimalFormat decimalFormat;

	private int activeSize = 0;

	private AtomicBoolean running = new AtomicBoolean(true);

	private Map<String, SerialNumberInfo> serialNumberInfoOfDate = new HashMap<String, SerialNumberInfo>();

	private SerialNumberModeGetter serialNumberModeGetter = null;

	/**
	 * 
	 * @Title: init
	 * @Description: 做一些初始化处理，如初始化formatter，初始化serialNumberInfoOfDate，初始化异步生成流水号的线程
	 *               ，初始化校验过期SerialNumberInfo的定时器
	 * @param @throws
	 *            InterruptedException 入参
	 * @return void 返回类型
	 * @author （林柏强）
	 * @throws @date
	 *             Mar 13, 2017 11:16:32 AM
	 * @version V1.0
	 */
	@PostConstruct
	public void init() throws InterruptedException {
		logger.info("SerialNumberGenerator.onCreate()");
		logger.info("serialNumberMode: " + serialNumberMode);
		logger.info("prefex: " + prefex);
		logger.info("datePattern: " + datePattern);

		dateFormatter = new SimpleDateFormat(datePattern);
		decimalFormat = new DecimalFormat(numberPattern);

		if (isQueueMode()) {

			logger.info("numberPattern: " + numberPattern);
			logger.info("initialPoolSize: " + initialPoolSize);
			logger.info("maxPoolSize: " + maxPoolSize);

			if (minPoolSize >= maxPoolSize) {
				throw new IllegalArgumentException(
						"minPoolSize '" + minPoolSize + "' should be less than maxPoolSize '" + maxPoolSize + "'");
			}

			initSerialNumberInfos();

			asyncGenerateSerialNumbers();

			checkExpiredSerialNumberInfo();

			serialNumberModeGetter = new SerialNumberQueueModeGetter();
		} else if (isDBMode()) {
			serialNumberModeGetter = new SerialNumberDBModeGetter();
		} else {
			throw new IllegalArgumentException("Unknown serialNumberMode: '" + serialNumberMode + "'");
		}

	}

	/**
	 * 
	 * @Title: getNextSerialNumber
	 * @Description: 调用此方法可返回当天的下一个序号号，注意：此方法是同步的！
	 * @param @return
	 *            入参
	 * @return String 返回类型
	 * @author （作者）
	 * @throws @date
	 *             Mar 13, 2017 11:23:32 AM
	 * @version V1.0
	 */
//	 @Transactional(propagation = Propagation.REQUIRED)
	public String getNextSerialNumber() {
//		 return serialNumberModeGetter.get();
		String num = null;
		try {
			while (num == null) {
				num = getNumberByMode();
				// Thread.sleep(500L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return num;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private String getNumberByMode() {
		return serialNumberModeGetter.get();
	}

	/**
	 * 
	 * @Title: initSerialNumberInfos
	 * @Description: 初始化serialNumberInfoOfDate，key为指定格式化后的日期（String类型），值为SerialNumberInfo
	 * @param 入参
	 * @return void 返回类型
	 * @author （林柏强）
	 * @throws @date
	 *             Mar 13, 2017 11:18:26 AM
	 * @version V1.0
	 */
	private void initSerialNumberInfos() {
		Date today = new Date();
		serialNumberInfoOfDate.put(formatDate(today),
				new SerialNumberInfo(new LinkedBlockingQueue<String>(maxPoolSize), false, today));
	}

	/**
	 * 
	 * @Title: batchGenerateSerialNumbers
	 * @Description: 批量生成指定日期及指定数量的流水号，并更新数据库，注意：生成的流水号会放进serialNumberInfoOfDate中
	 *               ，避免每次查询DB来取得下一个流水号
	 * @param @param
	 *            currentDay
	 * @param @param
	 *            count
	 * @param @throws
	 *            InterruptedException 入参
	 * @return void 返回类型
	 * @author （林柏强）
	 * @throws @date
	 *             Mar 13, 2017 11:30:42 AM
	 * @version V1.0
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void batchGenerateSerialNumbers(Date currentDay, int count) throws InterruptedException {
		logger.info("batchGenerateSerialNumbers: " + count);

		if (count > 0) {

			SerialNumberDTO serialNumberDTO = serialNumberMapper.querySerialNumberByDate(currentDay);
			SerialNumberInfo serialNumberInfo = serialNumberInfoOfDate.get(formatDate(currentDay));
			long nextNumber = 1;

			if (serialNumberDTO == null) {
				String serialNo = new StringBuffer(prefex).append(dateFormatter.format(currentDay))
						.append(decimalFormat.format(nextNumber)).toString();

				// 如果找不到指定日期的SerialNumberInfo，则新增之
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

				logger.info("find serialNumberPojo: " + serialNumberDTO);
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

				// 更新generatedSerialNumber至DB，使得下次根据generatedSerialNumber生成更多的流水号，注意：必须先更新DB，后放数据进numberQueue
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

	/**
	 * 
	 * @Title: asyncGenerateSerialNumbers
	 * @Description: 创建一个线程不断循环来检查当天是否需要生成流水号
	 * @param 入参
	 * @return void 返回类型
	 * @author （林柏强）
	 * @throws @date
	 *             Mar 13, 2017 11:35:36 AM
	 * @version V1.0
	 */
	private void asyncGenerateSerialNumbers() {

		new Thread() {

			@Override
			public void run() {

				while (running.get()) {

					synchronized (serialNumberInfoOfDate) {

						Date today = new Date();

						SerialNumberInfo serialNumberInfo = serialNumberInfoOfDate.get(formatDate(today));
						if (serialNumberInfo == null) {
							serialNumberInfo = new SerialNumberInfo(new LinkedBlockingQueue<String>(maxPoolSize), false,
									today);
							serialNumberInfoOfDate.put(formatDate(today), serialNumberInfo);
						}

						int currentQueueSize = serialNumberInfo.numberQueue.size();

						if (currentQueueSize < minPoolSize) {

							logger.info("asychronizeGenerateNumbers.numberQueue: {}, minPoolSize: {}", currentQueueSize,
									minPoolSize);

							int increment = (currentQueueSize == 0) ? initialPoolSize
									: (maxPoolSize - currentQueueSize);

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

				}
			}

		}.start();
	}

	/**
	 * 
	 * @Title: checkExpiredNumberInfo
	 * @Description: 定时从serialNumberInfoOfDate中移除今天以前的SerialNumber队列
	 * @param 入参
	 * @return void 返回类型
	 * @author （林柏强）
	 * @throws @date
	 *             Mar 13, 2017 10:26:41 AM
	 * @version V1.0
	 */
	private void checkExpiredSerialNumberInfo() {

		Runnable runnable = new Runnable() {

			public void run() {
				logger.info("checkExpiredNumberInfo");

				Iterator<String> iterator = serialNumberInfoOfDate.keySet().iterator();
				Date truncateToday = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

				while (iterator.hasNext()) {
					String next = iterator.next();

					if (DateUtils.truncate(serialNumberInfoOfDate.get(next).date, Calendar.DAY_OF_MONTH)
							.before(truncateToday)) {
						logger.info("remove before day[{}] element", next);

						iterator.remove();
					}
				}
			}
		};
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		// 每隔一天执行一次
		service.scheduleAtFixedRate(runnable, 10 * 1000, 1, TimeUnit.DAYS);
	}

	private String formatDate(Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}

	private boolean isQueueMode() {
		return MODE_QUEUE == serialNumberMode;
	}

	private boolean isDBMode() {
		return MODE_DB == serialNumberMode;
	}

	/**
	 * 
	 * @ClassName: SerialNumberInfo
	 * @Description: 自定久的流水号复合类
	 * @author (作者)
	 * @date Mar 13, 2017 11:31:20 AM
	 * @version V1.0
	 */
	class SerialNumberInfo {

		// 存储流水号的阻塞式队列
		LinkedBlockingQueue<String> numberQueue;

		// 用于记录该天内是否从DB中重新加载过numberQueue，如果加载过，则reload为true
		boolean reload = false;

		// 流水号关联的日期
		Date date;

		public SerialNumberInfo(LinkedBlockingQueue<String> numberQueue, boolean reload, Date date) {
			this.numberQueue = numberQueue;
			this.reload = reload;
			this.date = date;
		}

	}

	abstract class SerialNumberModeGetter {

		public abstract int getMode();

		public abstract String get();

	}

	class SerialNumberDBModeGetter extends SerialNumberModeGetter {

		@Override
		public int getMode() {
			return MODE_DB;
		}

		@Override
		public String get() {

			String serialNo = null;
			Date currentDay = new Date();
			long nextNumber = 1;
			int result = 0;

			logger.info("{} before getting result: {}", Thread.currentThread().getId(), result);

			// 这里需要使用select for update的方式锁表
			// 明确指定主键，并且有此笔资料，row lock；主键不明确，table lock
			SerialNumberDTO serialNumberDTO = serialNumberMapper.querySerialNumberByDate(currentDay);
			logger.info("{} serialNumberDTO: {}", serialNumberDTO);

			if (serialNumberDTO == null) {

				serialNumberDTO = new SerialNumberDTO();
				serialNumberDTO.setSerialNumberId(UUID.randomUUID().toString());
				serialNumberDTO.setGeneratedSerialNumber(0L);
				serialNumberDTO.setCurrentSerialNumber(nextNumber);
				serialNumberDTO.setSerialNumberDate(currentDay);
				try {
					result = serialNumberMapper.saveSerialNumber(serialNumberDTO);
				} catch (Exception e) {
					result = 0;
				}
			} else {
				result = serialNumberMapper.updateCurrentSerialNumber(serialNumberDTO);

				if (result > 0) {
					nextNumber = serialNumberDTO.getCurrentSerialNumber() + 1;
					serialNo = new StringBuffer(prefex).append(dateFormatter.format(currentDay))
							.append(decimalFormat.format(nextNumber)).toString();
				}
			}

			logger.info("{} after getting result: {}", Thread.currentThread().getId(), result);

			return serialNo;
		}

	}

	class SerialNumberQueueModeGetter extends SerialNumberModeGetter {

		@Override
		public int getMode() {
			return MODE_QUEUE;
		}

		@Override
		public String get() {
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
	}

}
