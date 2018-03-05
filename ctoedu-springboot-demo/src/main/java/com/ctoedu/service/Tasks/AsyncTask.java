package com.ctoedu.service.Tasks;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTask {
	//@Async所修饰的函数不要定义为static类型，这样异步调用不会生效
	//异步回调不返回数据
	@Async
	 public void dealNoReturnTask(){
		
	  System.out.println("Thread {} deal No Return Task start"+ "    "+Thread.currentThread().getName());
	  try {
	   Thread.sleep(3000);
	  } catch (InterruptedException e) {
	   e.printStackTrace();
	  }
	  System.out.println("Thread {} deal No Return Task end at {}"+"    "+Thread.currentThread().getName()+"    "+System.currentTimeMillis());
	 }
	//异步回调返回数据
	@Async
	public Future<String> dealHaveReturnTask() {
	 try {
	  Thread.sleep(3000);
	 } catch (InterruptedException e) {
	  e.printStackTrace();
	 }
	
	 return new AsyncResult<String>("异步回调返回数据！");
	}
}
