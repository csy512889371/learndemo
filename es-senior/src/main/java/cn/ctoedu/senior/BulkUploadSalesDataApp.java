package cn.ctoedu.senior;

import java.net.InetAddress;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class BulkUploadSalesDataApp {
	
	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws Exception {
		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch")
				.build();
		
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300)); 
	
		BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
		
		IndexRequestBuilder indexRequestBuilder = client.prepareIndex("car_shop", "sales", "3") 
				.setSource(XContentFactory.jsonBuilder()
							.startObject()
								.field("brand", "奔驰")
								.field("name", "奔驰C200")
								.field("price", 350000)
								.field("produce_date", "2017-01-20")
								.field("sale_price", 320000)
								.field("sale_date", "2017-01-25")
							.endObject());
		bulkRequestBuilder.add(indexRequestBuilder);
		
		UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate("car_shop", "sales", "1")
				.setDoc(XContentFactory.jsonBuilder()
						.startObject()
							.field("sale_price", 290000)
						.endObject());
		bulkRequestBuilder.add(updateRequestBuilder);
		
		DeleteRequestBuilder deleteReqeustBuilder = client.prepareDelete("car_shop", "sales", "2"); 
		bulkRequestBuilder.add(deleteReqeustBuilder);
		
		BulkResponse bulkResponse = bulkRequestBuilder.get();
		
		for(BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
			System.out.println("version: " + bulkItemResponse.getVersion()); 
		}
		
		client.close();
	}
	
}
