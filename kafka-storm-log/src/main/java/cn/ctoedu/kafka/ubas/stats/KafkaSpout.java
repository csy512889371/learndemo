package cn.ctoedu.kafka.ubas.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ctoedu.kafka.ubas.conf.KafkaConfigureAPI.KafkaParam;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class KafkaSpout implements IRichSpout {

	private static final long serialVersionUID = -7107773519958260350L;
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSpout.class);

	SpoutOutputCollector collector;
	private ConsumerConnector consumer;
	private String topic;

	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect", KafkaParam.ZK_HOSTS);
		props.put("group.id", KafkaParam.GROUP_ID);
		props.put("zookeeper.session.timeout.ms", "40000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		return new ConsumerConfig(props);
	}

	public KafkaSpout(String topic) {
		this.topic = topic;
	}

	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public void close() {
	}

	public void activate() {
		try {
			this.consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
			Map<String, Integer> topickMap = new HashMap<String, Integer>();
			topickMap.put(topic, new Integer(1));
			Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topickMap);
			KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			while (it.hasNext()) {
				String value = new String(it.next().message());
				LOGGER.info("[ Consumer ] Message is : " + value);
				collector.emit(new Values(value), value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Spout has error,msg is " + ex.getMessage());
		}
	}

	public void deactivate() {

	}

	public void nextTuple() {

	}

	public void ack(Object msgId) {

	}

	public void fail(Object msgId) {

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("KafkaSpout"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
