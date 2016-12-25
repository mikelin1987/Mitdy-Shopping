package com.mitdy.shopping.test;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DataWriter {

    private static final String TERMINATOR = "#";

    public static void main(String[] args) throws IOException {

        test2();
        // test1();
    }

    protected static void test1() throws FileNotFoundException, IOException {
        int COUNT = 500000000;

        Date startTime = new Date();
        System.out.println(startTime);
        String timeString = "2016-12-24 00:50:00";
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("C:/bigdata_1.txt"));

        for (int i = 1; i <= COUNT; i++) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(i).append(TERMINATOR).append(timeString).append(TERMINATOR).append("system")
                    .append(TERMINATOR).append(timeString).append(TERMINATOR).append("system")
                    .append("\r\n");
            outputStream.write(buffer.toString().getBytes());
        }

        outputStream.flush();
        outputStream.close();

        Date endTime = new Date();
        System.out.println(endTime);
        System.out.println("Times: " + ((endTime.getTime() - startTime.getTime()) / 1000) + " s");

        System.out.println("Done!");
    }

    protected static void test2() throws FileNotFoundException, IOException {
        int COUNT = 100000;

        Date startTime = new Date();
        System.out.println(startTime);
        String path = "C:/bigdata_3.txt";
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(1995, 0, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Random random = new Random();
        
        String c2Value = "hello, mysql";
        String newLine = "\r\n";

        for (int i = 1; i <= COUNT; i++) {
            StringBuffer buffer = new StringBuffer();
            int nextInt = random.nextInt(365 * 10);
            String dateText = format.format(DateUtils.addDays(calendar.getTime(), nextInt));

            buffer.append(i).append(TERMINATOR).append(c2Value).append(TERMINATOR).append(dateText)
                    .append(newLine);
            outputStream.write(buffer.toString().getBytes());
        }

        outputStream.flush();
        outputStream.close();

        Date endTime = new Date();
        System.out.println(endTime);
        System.out.println("Times: " + ((endTime.getTime() - startTime.getTime()) / 1000) + " s");

        System.out.println("Done!");

//        String cmd = "cmd /c C:/\"Program Files (x86)\"/MySQL/\"MySQL Server 5.1\"/bin/mysql.exe -uroot -proot";
//        cmd += "use spring_mvc_jdbc_demo1\r\n";
//        cmd += String.format("LOAD DATA INFILE '%s' INTO TABLE %s CHARACTER SET utf8 FIELDS TERMINATED BY '#'", path, "part_tab_2");
        
//        Process process = Runtime.getRuntime().exec(cmd);
//        
//        List<String> readLines = IOUtils.readLines(new InputStreamReader(process.getInputStream(), "GBK"));
//        for (String line : readLines) {
//            System.out.println(line);
//        }
        
    }

}
