package cn.ctoedu.senior;

import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class BoolQuerySearchBrand {
	
	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws Exception {
		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch")
				.build();
		
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));  
	
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("brand", "宝马"))
				.mustNot(QueryBuilders.termQuery("name.raw", "宝马318"))
				.should(QueryBuilders.rangeQuery("produce_date").gte("2017-01-01").lte("2017-01-31"))
				.filter(QueryBuilders.rangeQuery("price").gte(280000).lte(350000));    
		
		SearchResponse searchResponse = client.prepareSearch("car_shop")  
				.setTypes("cars")
				.setQuery(queryBuilder)
				.get();
		
		for(SearchHit searchHit : searchResponse.getHits().getHits()) {
			System.out.println(searchHit.getSourceAsString());  
		}
		
		client.close();
	}
	
}
