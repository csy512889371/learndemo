package com.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class JAVA2Bytes {

	public static void main(String[] args) throws Exception {
		byte[] bytes = toBytes();
		toPlayer(bytes);
	}
	
	
	/**
	 * 序列化
	 * @throws IOException 
	 */
	public static byte[] toBytes() throws IOException{
		
		Player player = new Player(101, 20, "peter");
		player.getSkills().add(1001);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		
		//写入对象
		objectOutputStream.writeObject(player);
		
		//获取 字节数组
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		System.out.println(Arrays.toString(byteArray));
		return byteArray;
	}
	
	
	/**
	 * 反序列化
	 * @param bs
	 * @throws Exception 
	 */
	public static void toPlayer(byte[] bs) throws Exception{
		
		ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bs));
		Player player = (Player)inputStream.readObject();
		
		//打印
		 System.out.println("playerId:" + player.getPlayerId());
		 System.out.println("age:" + player.getAge());
		 System.out.println("name:" + player.getName());
		 System.out.println("skills:" + (Arrays.toString(player.getSkills().toArray())));
	}

}
