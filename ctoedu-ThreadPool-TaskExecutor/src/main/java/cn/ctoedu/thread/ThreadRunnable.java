package cn.ctoedu.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Component
public class ThreadRunnable {

    @Autowired
    private TaskExecutor taskExecutor;

    public void executeThread(String result, CountDownLatch latch) {
        this.taskExecutor.execute(new TaskThread(result, latch));
    }

    private class TaskThread implements Runnable {
        private CountDownLatch latch;
        java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private String result;

        private TaskThread(String result, CountDownLatch latch) {
            super();
            this.result = result;
            this.latch = latch;
        }

        public void run() {
            try {
                for (int i = 0; i < 10000; i++) {
                    // dateTimeFormat.format(new Date());
                }
                System.out.println("现在的时间为：" + dateTimeFormat.format(new Date()) + "    " + result);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (this.latch != null) {
                    latch.countDown();
                }
            }
        }
    }
}
