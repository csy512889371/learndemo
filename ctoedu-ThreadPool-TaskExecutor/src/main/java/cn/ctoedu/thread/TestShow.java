package cn.ctoedu.thread;

import java.util.Date;

public class TestShow {
	java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public void start(String result){
	   System.out.println("现在的时间为："+dateTimeFormat.format(new Date())+"    "+result);
   
   }
}
