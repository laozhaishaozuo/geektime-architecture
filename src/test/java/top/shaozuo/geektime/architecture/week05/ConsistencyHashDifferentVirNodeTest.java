package top.shaozuo.geektime.architecture.week05;

import java.util.Map;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class ConsistencyHashDifferentVirNodeTest {

    private static final int DATA_COUNT = 100_0000;
    private static final String PRE_KEY = "consistency";

    private Cluster cluster;

    private void build(int initNodeSize, int extraVirNodeSize) {
        cluster = buildCluster(initNodeSize, extraVirNodeSize);
    }

    private Cluster buildCluster(int initNodeSize, int extraVirNodeSize) {
        Cluster cluster = new ConsistencyHashCluster(extraVirNodeSize);
        for (int i = 1; i <= initNodeSize; i++) {
            cluster.addNode(new ServerNode("192.168.0." + i, "c" + i + ".shaozuo.top"));
        }
        // 通过IntSupplier  和 stream.generate 生成
        Random random = new Random();
        IntSupplier is = new IntSupplier() {
            @Override
            public int getAsInt() {
                return random.nextInt();
            }
        };
        IntStream.generate(is).limit(DATA_COUNT).forEach(index -> {
            ServerNode node = cluster.getNodeBy(PRE_KEY + index);
            node.put(PRE_KEY + index, "Test Data");
        });
        return cluster;
    }

    private void printStats(int initNodeSize, int extraVirNodeSize) {
        Map<String, Integer> nodesCount = cluster.nodes().stream().distinct().collect(
                Collectors.groupingBy(ServerNode::getIp, Collectors.summingInt(ServerNode::size)));
        //        System.out.println("数据分布情况："); System.out.printf("站点\t数量 %n");
        //        nodesCount.entrySet()
        //                .forEach(item -> System.out.printf("%s\t%d%n", item.getKey(), item.getValue()));
        int[] counts = nodesCount.values().stream().mapToInt(Integer::intValue).toArray();
        double standardDeviation = MathUtils.calCStandardDeviation(counts);
        System.out.printf("初始节点数量 ： %d , 额外虚拟节点数量：%d , 标准差： %.4f %n", initNodeSize,
                extraVirNodeSize, standardDeviation);
    }

    @Test
    void test_extra() {
        int[] extras = { 0, 10, 50, 100, 150, 200, 250, 300, 400, 500, 800, 1000 };
        int initNodeSize = 10;
        for (int i = 0, len = extras.length; i < len; i++) {
            int extraVirNodeSize = extras[i];
            build(initNodeSize, extraVirNodeSize);
            printStats(initNodeSize, extraVirNodeSize);
        }

    }

    @Test
    void test_extra_0() {
        int initNodeSize = 10;
        int extraVirNodeSize = 0;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_10() {
        int initNodeSize = 10;
        int extraVirNodeSize = 10;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_50() {
        int initNodeSize = 10;
        int extraVirNodeSize = 0;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_100() {
        int initNodeSize = 10;
        int extraVirNodeSize = 100;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_150() {
        int initNodeSize = 10;
        int extraVirNodeSize = 150;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_200() {
        int initNodeSize = 10;
        int extraVirNodeSize = 200;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_250() {
        int initNodeSize = 10;
        int extraVirNodeSize = 250;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_300() {
        int initNodeSize = 10;
        int extraVirNodeSize = 300;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_400() {
        int initNodeSize = 10;
        int extraVirNodeSize = 400;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_500() {
        int initNodeSize = 10;
        int extraVirNodeSize = 500;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

    @Test
    void test_extra_2000() {
        int initNodeSize = 10;
        int extraVirNodeSize = 2000;
        build(initNodeSize, extraVirNodeSize);
        printStats(initNodeSize, extraVirNodeSize);
    }

}
