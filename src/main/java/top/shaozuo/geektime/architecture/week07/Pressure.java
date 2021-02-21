package top.shaozuo.geektime.architecture.week07;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.clearspring.analytics.stream.quantile.TDigest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用你熟悉的编程语言写一个 Web 性能压测工具，输入参数：URL，请求总次数，并发数。
 * 
 * 输出参数：平均响应时间，95% 响应时间。
 * 
 * 用这个测试工具以10并发、100 次请求压测 www.baidu.com。
 * 
 * @author shaozuo
 *
 */
public class Pressure {

    private String url;
    private int concurrency;
    private int requests;

    ExecutorService exectuor;
    private long[] times;

    public Pressure(String url, int concurrency, int requests) {
        this.url = url;
        this.concurrency = concurrency;
        this.requests = requests;
        exectuor = Executors.newFixedThreadPool(concurrency);
        times = new long[requests];
    }

    public void test() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < requests; i++) {
            final int index = i;
            exectuor.execute(() -> {
                long startTime = System.currentTimeMillis();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    times[index] = time;
                } catch (Exception e) {
                    //do nothing
                }

            });
        }
        exectuor.shutdown();
        try {
            exectuor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        //System.out.println("total:" + (end - start));
        //计算响应时长
        printTestResult();
    }

    /**
     * 响应时常
     */
    private void printTestResult() {
        List<Long> sortedTimes = LongStream.of(times).sorted().boxed().collect(Collectors.toList());
        System.out.println(sortedTimes);
        //System.out.println(LongStream.of(times).sum());
        System.out.printf("平均响应时间 %.2f ms%n", LongStream.of(times).average().getAsDouble());
        TDigest digest = new TDigest(requests);
        for (long time : times) {
            digest.add(time);
        }
        for (double q : new double[] { 0.10, 0.50, 0.90, 0.95 }) {
            System.out.printf("%.0f%%响应时间  %.0f ms%n", q * 100, digest.quantile(q));
        }
    }

    public static void main(String[] args) {
        new Pressure("http://www.baidu.com", 10, 100).test();
    }
}
