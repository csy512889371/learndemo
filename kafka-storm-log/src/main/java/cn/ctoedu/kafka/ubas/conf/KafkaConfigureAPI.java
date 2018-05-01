package cn.ctoedu.kafka.ubas.conf;

import cn.ctoedu.kafka.ubas.util.SystemConfig;

public class KafkaConfigureAPI {

	private static String zkHosts = "";

	static {
		String flag = SystemConfig.getProperty("dev.tag");
		zkHosts = SystemConfig.getProperty(flag + ".kafka.zk.host");
	}

	public interface KafkaConf {
		public static final String CREATE = "--create";
		public static final String DELETE = "--delete";
		public static final String LIST = "--list";
		public static final String PARTITIONS = "--partitions";
		public static final String REPLICATION = "--replication-factor";
		public static final String TOPIC = "--topic";
		public static final String ZK = "--zookeeper";
	}

	public interface KafkaParam {
		public static final String GROUP_ID = "kafkaUbasGroup";
		public static final String TOPIC = "kafka-ubas";
		public static String ZK_HOSTS = zkHosts;
	}

	public interface StormParam {
		public static final String PV = "pv";
		public static final String APP_ID = "appid";
	}

}
