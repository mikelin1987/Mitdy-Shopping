package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SalesOrderItemDataWriter extends AbstractWriter {

	
	/**
	 * 
	 * 
ID
CREATE_TIME
CREATE_USER
LAST_UPDATE_TIME
LAST_UPDATE_USER

ACTUAL_UNIT_PRICE
GOODS_DESC
GOODS_ID
GOODS_NAME
QUANTITY
TOTAL_AMOUNT
UNIT_PRICE
ORDER_ID
SALES_ACTIVITY_ITEM_ID

	 * 
	 * @throws Exception
	 */
	
	public void run() throws Exception {
		int count = 1000000;

		Date startTime = new Date();
		System.out.println(startTime);
		String path = "H:/sales_order_item_data.txt";
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

		String createUser = "SYSTEM";
		Random random = new Random();

		String createTimeText = "2016-12-31 22:31:15";
		String newLine = "\r\n";

		int id = 0;
		for (int i = 1; i <= count; i++) {
			
			for (int j = 0; j < 1 + random.nextInt(3); j++) {
				id += 1;
				
				StringBuffer buffer = new StringBuffer();

				BigDecimal price = new BigDecimal(random.nextInt(1000) + random.nextDouble());
				String name = "Goods_" + getRandomString(8);
				int quantity = 1 + random.nextInt(3);
				BigDecimal totalAmount = price.multiply(new BigDecimal(quantity));
				
				buffer.append(id).append(TERMINATOR);
				buffer.append(createTimeText).append(TERMINATOR);
				buffer.append(createUser).append(TERMINATOR);
				buffer.append(createTimeText).append(TERMINATOR);
				buffer.append(createUser).append(TERMINATOR);

				buffer.append(price.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
				buffer.append(name).append(TERMINATOR);
				buffer.append(random.nextInt(100000)).append(TERMINATOR);
				buffer.append(name).append(TERMINATOR);
				
				buffer.append(quantity).append(TERMINATOR);
				buffer.append(totalAmount.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
				buffer.append(price.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
				
				buffer.append(i).append(TERMINATOR);
				buffer.append(0);
				

				buffer.append(newLine);
				outputStream.write(buffer.toString().getBytes());
			}
		}

		System.out.println(id);
		
		outputStream.flush();
		outputStream.close();

		Date endTime = new Date();
		System.out.println(endTime);
		System.out.println("Times: " + ((endTime.getTime() - startTime.getTime()) / 1000) + " s");

		System.out.println("Done!");
	}

}
