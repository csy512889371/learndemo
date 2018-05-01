package cn.ctoedu.kafka.ubas.main;

import cn.ctoedu.kafka.ubas.stats.MessageBlots;
import cn.ctoedu.kafka.ubas.stats.KafkaSpout;
import cn.ctoedu.kafka.ubas.stats.StatsBlots;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class KafkaTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("kafkaUbasGroup", new KafkaSpout("kafka-ubas"));
		builder.setBolt("messageBlots", new MessageBlots()).shuffleGrouping("kafkaUbasGroup");
		builder.setBolt("kpiCounter", new StatsBlots(), 2).fieldsGrouping("messageBlots", new Fields("attribute"));
		Config config = new Config();
		// config.setDebug(true);
		// storm.messaging.netty.max_retries
		// storm.messaging.netty.max_wait_ms
		if (args != null && args.length > 0) {
			// online commit Topology
			config.put(Config.NIMBUS_HOST, args[0]);
			config.setNumWorkers(3);
			try {
				StormSubmitter.submitTopologyWithProgressBar(KafkaTopology.class.getSimpleName(), config,
						builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Local commit jar
			LocalCluster local = new LocalCluster();
			local.submitTopology("stats", config, builder.createTopology());
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			// local.shutdown();
		}
	}
}
