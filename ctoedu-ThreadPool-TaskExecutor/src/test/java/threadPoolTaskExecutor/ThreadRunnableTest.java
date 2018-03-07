package threadPoolTaskExecutor;

import cn.ctoedu.thread.ThreadRunnable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring.xml")
public class ThreadRunnableTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    ThreadRunnable threadRunnable;

    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 11; i++) {
            threadRunnable.executeThread(resultList,"架构师成长之路" + i, latch);
        }
        latch.await();
        System.out.println("执行完毕了吗！");

        System.out.println(resultList.size());
    }

}
