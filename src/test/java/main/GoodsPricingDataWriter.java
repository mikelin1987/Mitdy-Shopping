package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;

public class GoodsPricingDataWriter extends AbstractWriter {

	public void run() throws Exception {
		int count = 1000000;

		Date startTime = new Date();
		System.out.println(startTime);
		String path = "H:/goods_pricing_data.txt";
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

		String createUser = "SYSTEM";
		String createTimeText = "2016-12-31 22:31:15";

		Random random = new Random();

		String newLine = "\r\n";

		for (int i = 1; i <= count; i++) {
			StringBuffer buffer = new StringBuffer();

			int n = 5 + random.nextInt(5);
			BigDecimal discount = new BigDecimal(n / 10.0d);
			BigDecimal price = new BigDecimal(random.nextInt(1000) + random.nextDouble());

			buffer.append(i).append(TERMINATOR);
			buffer.append(createTimeText).append(TERMINATOR);
			buffer.append(createUser).append(TERMINATOR);
			buffer.append(createTimeText).append(TERMINATOR);
			buffer.append(createUser).append(TERMINATOR);
			buffer.append(discount.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
			buffer.append(price.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
			buffer.append(i);

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
