package cn.ctoedu.kafka.ubas.util;

import java.util.regex.Pattern;


public class InetAddressUtils {

	private static final Pattern IPV4 = Pattern
			.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

	public static boolean isIPv4(String ip) {
		return IPV4.matcher(ip).matches();
	}
}
