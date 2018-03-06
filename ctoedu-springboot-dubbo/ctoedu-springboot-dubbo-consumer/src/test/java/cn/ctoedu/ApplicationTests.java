package cn.ctoedu;

import cn.ctoedu.service.ComputeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {

	@Autowired
	ComputeService computeService;

	@Test
	public void testAdd() throws Exception {
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		System.out.println("Dubbo消费结果为：。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。"+computeService.add(100, 200));
		Assert.assertEquals("compute-service:add", new Integer(3), computeService.add(1, 2));
		//Assert.assertEquals("compute-service:add", new Integer(5), computeService.add(1, 2));
	}

}
