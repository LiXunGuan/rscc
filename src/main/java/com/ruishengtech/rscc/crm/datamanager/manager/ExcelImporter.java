package com.ruishengtech.rscc.crm.datamanager.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Wangyao on 2015/7/29.
 */
public class ExcelImporter {

    private static ExcelImporter excelImporter = new ExcelImporter();

    public static ExcelImporter getInstance() {
        return excelImporter;
    }

    private LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();


    final Map<String, Runnable> running = new ConcurrentHashMap<>();

    private ThreadPoolExecutor executor;

    private ExcelImporter() {

		executor = new ThreadPoolExecutor(1, 1, 3600L, TimeUnit.MILLISECONDS,
				taskQueue, Executors.defaultThreadFactory()) {

			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				super.beforeExecute(t, r);
				ExcelTask b = (ExcelTask) r;
				running.put(b.getTableName(), b);
			}

			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				super.afterExecute(r, t);
				ExcelTask b = (ExcelTask) r;
				running.remove(b.getTableName());
			}
		};
    }


    public void exe(Runnable runnable) {
        executor.execute(runnable);
    }

    public String getStatus(String uuid) {

        Runnable r = running.get(uuid);
        if (r != null) {
            ExcelTask b = (ExcelTask) r;
            return b.getProgress();
        }
        for (Runnable runnable : taskQueue) {
        	ExcelTask b = (ExcelTask) runnable;
            if(b.getTableName().equals(uuid)) {
                return "等待中";
            }
        }
        return "导入完成";
    }

    public void stop() {
        executor.shutdownNow();
    }
}
