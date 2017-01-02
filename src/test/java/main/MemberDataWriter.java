package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;

public class MemberDataWriter extends AbstractWriter {

	public void run() throws Exception {
		int count = 100000;
		
		Date startTime = new Date();
        System.out.println(startTime);
        String path = "F:/member_data.txt";
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2016, 0, 1, 0, 0, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Random random = new Random();

        String createUser = "SYSTEM";
        
        String createTimeText = "2016-12-31 22:15:15";
        String[] genderTexts = new String [] {"MALE", "FEMALE"};
        String newLine = "\r\n";

        for (int i = 1; i <= count; i++) {
            StringBuffer buffer = new StringBuffer();

            int skew = random.nextInt(3650);
            String dateText = format.format(DateUtils.addDays(calendar.getTime(), skew));

            String name = getRandomString(8);
            
            buffer.append(i).append(TERMINATOR);
            buffer.append(createTimeText).append(TERMINATOR);
            buffer.append(createUser).append(TERMINATOR);
            buffer.append(createTimeText).append(TERMINATOR);
            buffer.append(createUser).append(TERMINATOR);
            buffer.append(dateText).append(TERMINATOR);
            buffer.append(genderTexts[i % 2]).append(TERMINATOR);
            buffer.append(name).append(TERMINATOR);
            buffer.append(name).append(TERMINATOR);
            buffer.append(name.toUpperCase());
            
            buffer.append(newLine);
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
