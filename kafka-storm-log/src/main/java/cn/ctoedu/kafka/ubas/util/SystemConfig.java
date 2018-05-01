package cn.ctoedu.kafka.ubas.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemConfig {
	private static Properties mConfig;

	private static Logger log = LoggerFactory.getLogger(SystemConfig.class);
	static {
		mConfig = new Properties();
		try {
			try {
				mConfig.load(SystemConfig.class.getClassLoader().getResourceAsStream("system-config.properties"));
				mConfig.load(SystemConfig.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			} catch (Exception exp1) {
				exp1.printStackTrace();
			}
			log.info("Successfully loaded default properties.");

			if (log.isDebugEnabled()) {
				log.debug("SystemConfig looks like this ...");

				String key = null;
				Enumeration<Object> keys = mConfig.keys();
				while (keys.hasMoreElements()) {
					key = (String) keys.nextElement();
					log.debug(key + "=" + mConfig.getProperty(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private SystemConfig() {
	}

	/**
	 * Retrieve a property value
	 */
	public static String getProperty(String key) {
		return mConfig.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		log.debug("Fetching property [" + key + "=" + mConfig.getProperty(key) + "]");
		String value = SystemConfig.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * Retrieve a property as a boolean ... defaults to false if not present.
	 */
	public static boolean getBooleanProperty(String name) {
		return getBooleanProperty(name, false);
	}

	/**
	 * Retrieve a property as a boolean with specified default if not present.
	 */
	public static boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = SystemConfig.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		return (new Boolean(value)).booleanValue();
	}

	/**
	 * Retrieve a property as a int,defaults to 0 if not present.
	 */
	public static int getIntProperty(String name) {
		return getIntProperty(name, 0);
	}

	/**
	 * Retrieve a property as a int
	 */
	public static int getIntProperty(String name, int defaultValue) {
		String value = SystemConfig.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 
	 */
	public static int[] getIntPropertyArray(String name, int[] defaultValue, String splitStr) {
		String value = SystemConfig.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		try {
			String[] propertyArray = value.split(splitStr);
			int[] result = new int[propertyArray.length];
			for (int i = 0; i < propertyArray.length; i++) {
				result[i] = Integer.parseInt(propertyArray[i]);
			}
			return result;
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 */
	public static boolean[] getBooleanPropertyArray(String name, boolean[] defaultValue, String splitStr) {
		String value = SystemConfig.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		try {
			String[] propertyArray = value.split(splitStr);
			boolean[] result = new boolean[propertyArray.length];
			for (int i = 0; i < propertyArray.length; i++) {
				result[i] = (new Boolean(propertyArray[i])).booleanValue();
			}
			return result;
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 */
	public static String[] getPropertyArray(String name, String[] defaultValue, String splitStr) {
		String value = SystemConfig.getProperty(name);
		if (value == null) {
			return defaultValue;
		}
		try {
			String[] propertyArray = value.split(splitStr);
			return propertyArray;
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 */
	public static String[] getPropertyArray(String name, String splitStr) {
		String value = SystemConfig.getProperty(name);
		if (value == null) {
			return null;
		}
		try {
			String[] propertyArray = value.split(splitStr);
			return propertyArray;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retrieve all property keys
	 */
	public static Enumeration<Object> keys() {
		return mConfig.keys();
	}

	public static Map<String, String> getPropertyMap(String name) {
		String[] maps = getPropertyArray(name, ",");
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String str : maps) {
				String[] array = str.split(":");
				if (array.length > 1) {
					map.put(array[0], array[1]);
				}
			}
		} catch (Exception e) {
			log.error("Get PropertyMap info has error,key is :" + name);
			e.printStackTrace();
		}
		return map;
	}
}
