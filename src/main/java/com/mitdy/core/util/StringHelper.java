package com.mitdy.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringHelper {

	public static synchronized String makeUUIDWithTime() {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString().replaceAll("-", "");
		StringBuilder sb = new StringBuilder();
		String currentTime = defaultDateFormat.format(new Date());
		sb.append(currentTime).append(uuidStr.substring(currentTime.length(), 32));
		return sb.toString();
	}

	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
