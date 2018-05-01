package cn.ctoedu.kafka.ubas.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ctoedu.kafka.ubas.conf.KafkaConfigureAPI.StormParam;
import cn.ctoedu.kafka.ubas.util.CalendarUtils;
import cn.ctoedu.kafka.ubas.util.InetAddressUtils;
import cn.ctoedu.kafka.ubas.util.JedisFactory;
import redis.clients.jedis.Jedis;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;


public class StatsBlots implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -619395076356762569L;

	private static Logger LOG = LoggerFactory.getLogger(StatsBlots.class);

	OutputCollector collector;
	Map<String, Integer> counter;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.counter = new HashMap<String, Integer>();
	}

	public void execute(Tuple input) {
		String key = input.getString(0);
		// Other KPI
		if (!InetAddressUtils.isIPv4(key) && !key.contains(StormParam.APP_ID)) {
			Integer integer = this.counter.get(key);
			if (integer != null) {
				integer += 1;
				this.counter.put(key, integer);
			} else {
				this.counter.put(key, 1);
			}
		}

		// PV
		if (InetAddressUtils.isIPv4(key)) {
			Integer pvInt = this.counter.get(StormParam.PV);
			if (pvInt != null) {
				pvInt += 1;
				this.counter.put(StormParam.PV, pvInt);
			} else {
				this.counter.put(StormParam.PV, 1);
			}
			System.out.println(key + ",pv=" + pvInt);
		}

		// AppId
		if (key.contains(StormParam.APP_ID)) {
			Integer appIdInt = this.counter.get(key);
			if (appIdInt != null) {
				appIdInt += 1;
				this.counter.put(key, appIdInt);
			} else {
				this.counter.put(key, 1);
			}
		}

		try {
			Jedis jedis = JedisFactory.getJedisInstance("real-time");
			for (Entry<String, Integer> entry : this.counter.entrySet()) {

				LOG.info("Bolt stats kpi is [" + entry.getKey() + "|" + entry.getValue().toString() + "]");
				// write result to redis
				jedis.set(CalendarUtils.today() + "_" + entry.getKey(), entry.getValue().toString());

				// write result to mysql
				// ...
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("Jedis error, msg is " + ex.getMessage());
		}
		this.collector.ack(input);
	}

	public void cleanup() {
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
