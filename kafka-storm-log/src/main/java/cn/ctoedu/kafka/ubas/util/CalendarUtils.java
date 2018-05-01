package cn.ctoedu.kafka.ubas.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtils {

	public static String today() {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd");
		return dfs.format(new Date());
	}

}
