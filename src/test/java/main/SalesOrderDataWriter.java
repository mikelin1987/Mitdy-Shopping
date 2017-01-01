package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SalesOrderDataWriter extends AbstractWriter {

	
	/**
	 * 
	 * ID
CREATE_TIME
CREATE_USER
LAST_UPDATE_TIME
LAST_UPDATE_USER

ACTUAL_AMOUNT
CONTACT_NO
DELIVER_AMOUNT
DISCOUNT_AMOUNT
MEMBER_ID
ORDER_AMOUNT
ORDER_NO
ORDER_STATUS
PAYER_NAME
PAYMENT_TYPE
SUBMIT_TIME

	 * 
	 * @throws Exception
	 */
	
	public void run() throws Exception {
		int count = 1000000;

		Date startTime = new Date();
		System.out.println(startTime);
		String path = "H:/sales_order_data.txt";
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));

		String createUser = "SYSTEM";
		Random random = new Random();

		String createTimeText = "2016-12-31 22:31:15";
		String newLine = "\r\n";

		for (int i = 1; i <= count; i++) {
			StringBuffer buffer = new StringBuffer();

			BigDecimal orderAmount = new BigDecimal(random.nextInt(1000) + random.nextDouble());
			orderAmount = orderAmount.multiply(new BigDecimal(3));
			
			BigDecimal deliverAmount = new BigDecimal(random.nextInt(10) + random.nextDouble());
			BigDecimal actualAmount = deliverAmount.add(orderAmount);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String orderNo = "NO_" + format.format(new Date()) + getRandomNum(4);
			String payerName = "PAYER_" + getRandomString(5);
			
			buffer.append(i).append(TERMINATOR);
			buffer.append(createTimeText).append(TERMINATOR);
			buffer.append(createUser).append(TERMINATOR);
			buffer.append(createTimeText).append(TERMINATOR);
			buffer.append(createUser).append(TERMINATOR);

			buffer.append(actualAmount.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
			buffer.append(getRandomNum(13)).append(TERMINATOR);
			buffer.append(deliverAmount.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
			buffer.append(orderAmount.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
			buffer.append(random.nextInt(100000)).append(TERMINATOR);
			buffer.append(orderAmount.setScale(2, RoundingMode.HALF_UP)).append(TERMINATOR);
			
			buffer.append(orderNo).append(TERMINATOR);
			buffer.append("CREATED").append(TERMINATOR);
			
			buffer.append(payerName).append(TERMINATOR);
			buffer.append("CASH").append(TERMINATOR);
			buffer.append(createTimeText);
			

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
