package com.serial;
import java.io.IOException;
import java.util.Arrays;
/**
 * 自定义序列化协议
 *
 *
 */
public class Serial2Bytes {

	public static void main(String[] args) throws Exception {
		byte[] bytes = toBytes();
		toPlayer(bytes);
	}
	
	
	/**
	 * 序列化
	 * @throws IOException 
	 */
	public static byte[] toBytes() throws IOException{
		
		Player player = new Player(320, 20, "peter");
		player.getSkills().add(1001);
		
		//获取 字节数组
		byte[] byteArray = player.getBytes();
		System.out.println(Arrays.toString(byteArray));
		return byteArray;
	}
	
	
	/**
	 * 反序列化
	 * @param bs
	 * @throws Exception 
	 */
	public static void toPlayer(byte[] bs) throws Exception{
		
		Player player = new Player();
		player.readFromBytes(bs);
		
		//打印
		 System.out.println("playerId:" + player.getPlayerId());
		 System.out.println("age:" + player.getAge());
		 System.out.println("name:" + player.getName());
		 System.out.println("skills:" + (Arrays.toString(player.getSkills().toArray())));
	}

}
