package top.shaozuo.geektime.architecture.week05;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NormalHashClusterTest {

    private static final int DATA_COUNT = 100_0000;
    private static final String PRE_KEY = "normal";

    private Cluster cluster;

    @BeforeEach
    void beforeAll() {
        cluster = buildCluster(10);
        initNodes(cluster);
    }

    private static Cluster buildCluster(int initNodeSize) {
        Cluster cluster = new NormalHashCluster();
        for (int i = 1; i <= initNodeSize; i++) {
            cluster.addNode(new ServerNode("192.168.0." + i, "c" + i + ".shaozuo.top"));
        }
        return cluster;
    }

    private static void initNodes(Cluster cluster) {
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

        System.out.println("数据分布情况：");
        cluster.nodes().forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量:" + node.size());
        });
    }

    @Test
    void testAdd() {
        cluster.addNode(new ServerNode("192.168.1.11", "c1.shaozuo.top"));

        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.getNodeBy(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("add 一个节点之后，缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }

    @Test
    void testRemove() {
        cluster.removeNode(new ServerNode("192.168.0.1", "c1.shaozuo.top"));

        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.getNodeBy(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("remove 一个节点之后，缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }

}
