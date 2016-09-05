package com.heylichen.concurrency.liveness.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by Chen Li on 2016/9/5.
 * <p>
 * 1. Another form of resource-based deadlock is thread-starvation deadlock. We saw
 * an example of this hazard in Section 8.1.1, where a task that submits a task and
 * waits for its result executes in a single-threaded Executor. In that case, the first
 * task will wait forever, permanently stalling that task and all others waiting to
 * execute in that Executor. Tasks that wait for the results of other tasks are the
 * primary source of thread-starvation deadlock; bounded pools and interdependent
 * tasks do not mix well.
 * <p>
 * 2. what i found is, in the case there are N tasks that submits a task and waits for its result,
 * 2.1) if thread pool size is not greater than N,  thread-starvation deadlock occurs.
 * 2.2) if thread pool size is greater than N,  thread-starvation deadlock will not occur.
 * because a seperate thead is needed for the subtask, when it can't be found, starvation occurs.
 */
public class ThreadStarvingDeadlockDemo {
	private static Logger logger = LoggerFactory.getLogger(ThreadStarvingDeadlockDemo.class);
	//for case 1: thread-starvation deadlock
	private final static CountDownLatch latch = new CountDownLatch(1);
	//for case 2: thread-starvation deadlock, a more generic form
	private static int NUM = 2;
	private final static CountDownLatch latch2 = new CountDownLatch(NUM);

	private static int testCase = 1;

	public static void main(String[] args) throws Exception {
		//change parameter to NUM-1 will trigger starving.
		starvingOrNot(NUM);
		//starving();
	}

	private static void starving() throws Exception {
		testCase = 1;
		logger.info("main start");
		ExecutorService executorService = Executors.newFixedThreadPool(1);

		WeatherReport wr = new WeatherReport(executorService);
		executorService.submit(wr);

		logger.info("main waiting");
		latch.await();
		logger.info("main waiting end.");
		executorService.shutdown();
		logger.info("main end");
	}

	private static void starvingOrNot(int resourceNum) throws Exception {
		if (resourceNum > NUM) {
			logger.info("---------------not starving");
		} else {
			logger.info("---------------starving");
		}
		testCase = 2;
		logger.info("main start");
		ExecutorService executorService = Executors.newFixedThreadPool(resourceNum);

		for (int i = 0; i < NUM; i++) {
			WeatherReport wr = new WeatherReport(executorService);
			executorService.submit(wr);
		}
		logger.info("main waiting");
		latch2.await();
		logger.info("main waiting end.");
		executorService.shutdown();
		logger.info("main end");
	}


	static class WeatherReport implements Runnable {
		private ExecutorService executorService;

		public WeatherReport(ExecutorService executorService) {
			this.executorService = executorService;
		}

		@Override
		public void run() {
			logger.info("start");

			Future<Integer> wf = executorService.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					logger.info("working");
					Thread.sleep(100);
					logger.info("work complete");
					return 21;
				}
			});
			Integer temp = null;
			try {
				temp = wf.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			logger.info("end report :" + temp);
			if (testCase == 1) {
				latch.countDown();
			} else {
				latch2.countDown();
			}

		}
	}

}
