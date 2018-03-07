package cn.ctoedu.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class ThreadRunnable {

    @Autowired
    private TaskExecutor taskExecutor;

    public void executeThread(List<String> resultList, String param, CountDownLatch latch) {
        this.taskExecutor.execute(new TaskThread(resultList,param, latch));
    }

    private class TaskThread implements Runnable {
        private CountDownLatch latch;
        java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private String param;

        private List<String> resultList;

        private TaskThread(List<String> resultList, String param, CountDownLatch latch) {
            super();
            this.param = param;
            this.latch = latch;
            this.resultList = resultList;
        }

        public void run() {
            try {
                addTotalList(resultList,param);
                System.out.println("现在的时间为：" + dateTimeFormat.format(new Date()) + "    " + param);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (this.latch != null) {
                    latch.countDown();
                }
            }
        }
    }

    private synchronized void addTotalList(List<String> resultList,String item){
        resultList.add(item);
    }
}
