package com.oauth.model;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.oauth.facade.OURegisterRequest;
import com.oauth.facade.OURegisterResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class UCenterRemoteCall {
//   private static String remoteUrl="http://115.28.172.254:8080/";
	private static String remoteUrl="http://localhost:8080/";
   public static void remoteCallRegister(OURegisterRequest request){
		try {

			URL targetUrl = new URL(remoteUrl+"/usercenter/ouperson/register");

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(request.toString().getBytes());
			outputStream.flush();

			int rcode = httpConnection.getResponseCode();
			if (rcode != 200 && rcode != 204) {
				throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
			}

			httpConnection.disconnect();

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}
   public static OURegisterResponse remoteCallAccount(String login_name) throws ClientProtocolException, IOException, ParseException{
		// 创建HttpClient实例
		HttpClient httpClient = new DefaultHttpClient();
		// 创建Get方法实例
		HttpGet httpGet = new HttpGet(remoteUrl+"ldap-web/usercenter/ouperson/account?login_name=" + login_name);

		HttpResponse response = httpClient.execute(httpGet);

		HttpEntity entity = response.getEntity();
		OURegisterResponse user = null;

		if (entity != null) {
			InputStream instreams = entity.getContent();
			String jsonStr = convertStreamToString(instreams);
			// 将 JSON 字符串转化为对象
			user = JSON.parse(jsonStr, OURegisterResponse.class);
			// 请求完成处理
			httpGet.abort();
		}
		return user;
   }
   private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
