package Expriments;

/* Created by AMXPC on 2017/1/5. */

import DBConnect.ConnectionPool;
import DBConnect.DBConnector;
import ThreadPool.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExprimentExecutor {
    private ArrayList<Runnable> runnables;
    private ConnectionPool oracleConnectionPool;
    private ConnectionPool timesTenConnectionPool;
    private static ExprimentExecutor exprimentExecutor;

    private ExprimentExecutor() {
        initExprimentExecutor();
    }

    public static void init() {
        exprimentExecutor = new ExprimentExecutor();
    }

    private void initExprimentExecutor() {
        runnables = new ArrayList<>();
    }

    private void oraclePoolInit() {
        oracleConnectionPool = new ConnectionPool("dbcpOracle.properties", DBConnector.ORACLE);
    }

    private void oraclePoolClose() {
        oracleConnectionPool = null;
    }

    private void timestenPoolInit() {
        timesTenConnectionPool = new ConnectionPool("dbcpTimesTen.properties", DBConnector.TIMESTEN);
    }

    private void timestenPoolClose() {
        timesTenConnectionPool = null;
    }

    /*    private long SerialExecute(ArrayList<Runnable> runnable) {

            long s_MSec = System.currentTimeMillis();
            long tmp_MSec = System.currentTimeMillis();
            for(int i = 0; i < 100000; i++) {
                runnable.get(i).run();
                if(i % 1000 == 0) {
                    System.out.println(i + " " + (System.currentTimeMillis() - tmp_MSec));
                    tmp_MSec = System.currentTimeMillis();
                }
            }
            long e_Msec = System.currentTimeMillis();

            return (e_Msec - s_MSec);
        }*/

    private long serialExecute(ArrayList<Runnable> runnable, int times) {

        long s_MSec = System.currentTimeMillis();

        ThreadPoolExecutor singleThread = ThreadPoolFactory.getSingleThreadExecutor();
        for (int i = 1; i < times; i++) {
            singleThread.execute(runnable.get(i));
        }
        singleThread.shutdown();
        await(singleThread);

        long e_Msec = System.currentTimeMillis();
        return (e_Msec - s_MSec);
    }

    private long parallelExecute(ArrayList<Runnable> runnable, int times) {

        long s_MSec = System.currentTimeMillis();

        ThreadPoolExecutor fixedThreadPool = ThreadPoolFactory.getFixedThreadPool(50);
        for (int i = 1; i < times; i++) {
            fixedThreadPool.execute(runnable.get(i));
        }
        fixedThreadPool.shutdown();
        await(fixedThreadPool);

        long e_Msec = System.currentTimeMillis();
        return (e_Msec - s_MSec);
    }

    private void await(ThreadPoolExecutor threadPoolExecutor) {
        do {
            try {
                threadPoolExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
                System.out.println(threadPoolExecutor.getQueue().size() + " " + threadPoolExecutor.getActiveCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!threadPoolExecutor.getQueue().isEmpty() || threadPoolExecutor.getActiveCount() != 0);
    }

    public void doSerialSimpleExp(int times) {
        System.out.println("Prepare Serial Simple Expriments On Oracle");
        runnables.clear();
        oraclePoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new SimpleExp(oracleConnectionPool));
        }
        System.out.println("Start Serial Simple Expriments On Oracle");
        System.out.println("On Oracle: " + serialExecute(runnables, times) + " ms");
        System.out.println("Finish Serial Simple Expriments On Oracle");
        oraclePoolClose();

        System.out.println("Prepare Serial Simple Expriments On TimesTen");
        runnables.clear();
        timestenPoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new SimpleExp(timesTenConnectionPool));
        }
        System.out.println("Start Serial Simple Expriments On TimesTen");
        System.out.println("On TimesTen: " + serialExecute(runnables, times) + " ms");
        System.out.println("Finish Serial Simple Expriments On TimesTen");
        timestenPoolClose();

        System.out.println("Serial Simple Expriments Finished");
        runnables.clear();
    }

    public void doSerialDepositExp(int times) {
        System.out.println("Prepare Serial Deposit Expriments On Oracle");
        runnables.clear();
        oraclePoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new DepositExp(i, oracleConnectionPool));
        }
        System.out.println("Start Serial Deposit Expriments On Oracle");
        System.out.println("On Oracle: " + serialExecute(runnables, times) + " ms");
        System.out.println("Finish Serial Deposit Expriments On Oracle");
        oraclePoolClose();

        System.out.println("Prepare Serial Deposit Expriments On TimesTen");
        runnables.clear();
        timestenPoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new DepositExp(i, timesTenConnectionPool));
        }
        System.out.println("Start Serial Deposit Expriments On TimesTen");
        System.out.println("On TimesTen: " + serialExecute(runnables, times) + " ms");
        System.out.println("Finish Serial Deposit Expriments On TimesTen");
        timestenPoolClose();

        System.out.println("Serial Deposit Expriments Finished");
        runnables.clear();
    }

    public void doSerialComplexExp(int times) {
        System.out.println("Prepare Serial Complex Expriments On Oracle");
        runnables.clear();
        oraclePoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new ComplexExp(oracleConnectionPool));
        }
        System.out.println("Start Serial Complex Expriments On Oracle");
        System.out.println("On Oracle: " + serialExecute(runnables, times) + " ms");
        System.out.println("Finish Serial Complex Expriments On Oracle");
        oraclePoolClose();

        System.out.println("Prepare Serial Complex Expriments On TimesTen");
        runnables.clear();
        timestenPoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new ComplexExp(timesTenConnectionPool));
        }
        System.out.println("Start Serial Complex Expriments On TimesTen");
        System.out.println("On TimesTen: " + serialExecute(runnables, times) + " ms");
        System.out.println("Finish Serial Complex Expriments On TimesTen");
        timestenPoolClose();

        System.out.println("Serial Complex Expriments Finished");
        runnables.clear();
    }

    public void doParallelSimpleExp(int times) {
        System.out.println("Prepare Parallel Simple Expriments On Oracle");
        runnables.clear();
        oraclePoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new SimpleExp(oracleConnectionPool));
        }
        System.out.println("Start Parallel Simple Expriments On Oracle");
        System.out.println("On Oracle: " + parallelExecute(runnables, times) + " ms");
        System.out.println("Finish Parallel Simple Expriments On Oracle");
        oraclePoolClose();

        System.out.println("Prepare Parallel Simple Expriments On TimesTen");
        runnables.clear();
        timestenPoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new SimpleExp(timesTenConnectionPool));
        }
        System.out.println("Start Parallel Simple Expriments On TimesTen");
        System.out.println("On TimesTen: " + parallelExecute(runnables, times) + " ms");
        System.out.println("Finish Parallel Simple Expriments On TimesTen");
        timestenPoolClose();

        System.out.println("Parallel Simple Expriments Finished");
        runnables.clear();
    }

    public void doParallelDepositExp(int times) {
/*        System.out.println("Prepare Parallel Deposit Expriments On Oracle");
        runnables.clear();
        oraclePoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new DepositExp(i, oracleConnectionPool));
        }
        System.out.println("Start Parallel Deposit Expriments On Oracle");
        System.out.println("On Oracle: " + parallelExecute(runnables, times) + " ms");
        System.out.println("Finish Parallel Deposit Expriments On Oracle");
        oraclePoolClose();*/

        System.out.println("Prepare Parallel Deposit Expriments On TimesTen");
        runnables.clear();
        timestenPoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new DepositExp(i, timesTenConnectionPool));
        }
        System.out.println("Start Parallel Deposit Expriments On TimesTen");
        System.out.println("On TimesTen: " + parallelExecute(runnables, times) + " ms");
        System.out.println("Finish Parallel Deposit Expriments On TimesTen");
        timestenPoolClose();

        System.out.println("Parallel Deposit Expriments Finished");
        runnables.clear();
    }

    public void doParallelComplexExp(int times) {
        System.out.println("Prepare Parallel Complex Expriments On Oracle");
        runnables.clear();
        oraclePoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new ComplexExp(oracleConnectionPool));
        }
        System.out.println("Start Parallel Complex Expriments On Oracle");
        System.out.println("On Oracle: " + parallelExecute(runnables, times) + " ms");
        System.out.println("Finish Parallel Complex Expriments On Oracle");
        oraclePoolClose();

        System.out.println("Prepare Parallel Complex Expriments On TimesTen");
        runnables.clear();
        timestenPoolInit();
        for (int i = 0; i < times; i++) {
            runnables.add(new ComplexExp(timesTenConnectionPool));
        }
        System.out.println("Start Parallel Complex Expriments On TimesTen");
        System.out.println("On TimesTen: " + parallelExecute(runnables, times) + " ms");
        System.out.println("Finish Parallel Complex Expriments On TimesTen");
        timestenPoolClose();
        System.out.println("Parallel Complex Expriments Finished");
        runnables.clear();
    }

    public static ExprimentExecutor getExprimentExecutor() {
        return exprimentExecutor;
    }
}
