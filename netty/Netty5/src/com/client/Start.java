package com.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * 启动类
 *
 *
 */
public class Start {

	public static void main(String[] args) {

		MultClient client = new MultClient();
		client.init(5);
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			try {
				System.out.println("请输入:");
				String msg = bufferedReader.readLine();
				client.nextChannel().writeAndFlush(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
