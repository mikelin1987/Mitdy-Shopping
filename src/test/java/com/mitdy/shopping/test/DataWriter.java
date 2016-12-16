package com.mitdy.shopping.test;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter {

    private static final String TERMINATOR = "#";
    private static final int COUNT = 100000000;

    public static void main(String[] args) throws IOException {

        Date startTime = new Date();
        System.out.println(startTime);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(
                "F:/bigdata.txt"));

        for (int i = 1; i <= COUNT; i++) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(i).append(TERMINATOR).append("2016-12-16 00:50:00").append(TERMINATOR).append("system").append(TERMINATOR)
                    .append("2016-12-16 00:50:00").append(TERMINATOR).append("system").append("\r\n");
            outputStream.write(buffer.toString().getBytes());
        }

        outputStream.flush();
        outputStream.close();

        Date endTime = new Date();
        System.out.println(endTime);
        System.out.println("Times: " + ((endTime.getTime() - startTime.getTime()) / 1000) + " s");

        System.out.println("Done!");
    }

}
