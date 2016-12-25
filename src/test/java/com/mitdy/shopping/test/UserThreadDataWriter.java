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

public class UserThreadDataWriter {

    private static final String TERMINATOR = "#";

    public static void main(String[] args) throws IOException {
        test1();
    }

    protected static void test1() throws FileNotFoundException, IOException {
        int COUNT = 1000000;

        Date startTime = new Date();
        System.out.println(startTime);
        String path = "C:/bigdata.txt";
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2016, 0, 1, 0, 0, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Random random = new Random();

        String s = "subject";
        String c = "content";
        String newLine = "\r\n";

        for (int i = 1; i <= COUNT; i++) {
            StringBuffer buffer = new StringBuffer();

            int userId = 1 + random.nextInt(20000);
            String subject = s + userId;
            String content = c + userId;

            int skew = random.nextInt(365);
            String dateText = format.format(DateUtils.addDays(calendar.getTime(), skew));

            buffer.append(i).append(TERMINATOR);
            buffer.append(userId).append(TERMINATOR);
            buffer.append(subject).append(TERMINATOR);
            buffer.append(content).append(TERMINATOR);
            buffer.append(dateText);
            buffer.append(newLine);
            outputStream.write(buffer.toString().getBytes());
        }

        outputStream.flush();
        outputStream.close();

        Date endTime = new Date();
        System.out.println(endTime);
        System.out.println("Times: " + ((endTime.getTime() - startTime.getTime()) / 1000) + " s");

        System.out.println("Done!");

        // String cmd = "cmd /c C:/\"Program Files (x86)\"/MySQL/\"MySQL Server
        // 5.1\"/bin/mysql.exe -uroot -proot";
        // cmd += "use spring_mvc_jdbc_demo1\r\n";
        // cmd += String.format("LOAD DATA INFILE '%s' INTO TABLE %s CHARACTER
        // SET utf8 FIELDS TERMINATED BY '#'", path, "part_tab_2");

        // Process process = Runtime.getRuntime().exec(cmd);
        //
        // List<String> readLines = IOUtils.readLines(new
        // InputStreamReader(process.getInputStream(), "GBK"));
        // for (String line : readLines) {
        // System.out.println(line);
        // }

    }

}
