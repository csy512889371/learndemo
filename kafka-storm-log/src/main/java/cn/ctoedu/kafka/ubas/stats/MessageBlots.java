package cn.ctoedu.kafka.ubas.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public class MessageBlots implements IRichBolt {


	private static final long serialVersionUID = -2025360103997307370L;

	OutputCollector collector;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	public void execute(Tuple input) {
		String[] line = input.getString(0).split(",");
		for (int i = 0; i < line.length; i++) {
			List<Tuple> a = new ArrayList<Tuple>();
			a.add(input);
			switch (i) {
			case 0:
				this.collector.emit(a, new Values(line[i]));
				break;
			case 3:
				this.collector.emit(a, new Values(line[i]));
				break;
			case 4:
				this.collector.emit(a, new Values(line[i]));
				break;
			case 6:
				this.collector.emit(a, new Values(line[i]));
				break;
			default:
				break;
			}
		}
		this.collector.ack(input);
	}

	public void cleanup() {

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("attribute"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
