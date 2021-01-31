package top.shaozuo.geektime.architecture.week05;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class ConsistencyVsNormalTest {

    private static final int DATA_COUNT = 100_0000;
    private static final String PRE_KEY = "pre_";

    private void initNode(Cluster cluster) {
        for (int i = 1; i <= 10; i++) {
            cluster.addNode(new ServerNode("192.168.0." + i, "c" + i + ".shaozuo.top"));
        }
    }

    private void initNodeData(Cluster cluster) {
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
    }

    private void printStats(Cluster cluster) {
        Map<String, Integer> nodesCount = cluster.nodes().stream().distinct().collect(
                Collectors.groupingBy(ServerNode::getIp, Collectors.summingInt(ServerNode::size)));
        int[] counts = nodesCount.values().stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(counts));
        double standardDeviation = MathUtils.calCStandardDeviation(counts);
        System.out.printf("%s 标准差： %.4f %n", cluster.getClass().getSimpleName(), standardDeviation);
    }

    @Test
    void test_normal() {
        Cluster cluster = new NormalHashCluster();
        initNode(cluster);
        initNodeData(cluster);
        printStats(cluster);
    }

    @Test
    void test_consistency() {
        Cluster cluster = new ConsistencyHashCluster(10);
        initNode(cluster);
        initNodeData(cluster);
        printStats(cluster);
    }

}
