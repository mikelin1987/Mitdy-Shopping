package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

public class GoodsDataWriter extends AbstractWriter {

	public void run() throws Exception {
		int count = 1000000;

		Date startTime = new Date();
		System.out.println(startTime);
		String path = "F:/goods_data.txt";
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

		String createUser = "SYSTEM";

		String createTimeText = "2016-12-31 22:31:15";
		String newLine = "\r\n";

		for (int i = 1; i <= count; i++) {
			StringBuffer buffer = new StringBuffer();

			String name = "Goods_" + getRandomString(8);

			buffer.append(i).append(TERMINATOR);
			buffer.append(createTimeText).append(TERMINATOR);
			buffer.append(createUser).append(TERMINATOR);
			buffer.append(createTimeText).append(TERMINATOR);
			buffer.append(createUser).append(TERMINATOR);
			buffer.append(name).append(TERMINATOR);
			buffer.append(name);

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
