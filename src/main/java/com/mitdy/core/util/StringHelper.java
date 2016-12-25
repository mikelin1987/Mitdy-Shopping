package com.mitdy.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 该包存放所有的 公共工具类 比如：util类，helper类
 * 
 * @author pengrx
 *
 */
public class StringHelper {

    /**
     * 生成uuid加入时间处理
     * @return
     */
    public static synchronized String makeUUIDWithTime() {
        SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        String currentTime = defaultDateFormat.format(new Date());
        sb.append(currentTime).append(uuidStr.substring(currentTime.length(), 32));
        return sb.toString();
    }
}
