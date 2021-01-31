package top.shaozuo.geektime.architecture.week05;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class ConsistencyHashCluster extends AbstractCluster {

    private SortedMap<Integer, ServerNode> allNodes = new TreeMap<>();

    private static final int DEFAULT_EXTRA_VIR_NODE_COUNT = 150;

    private static final String SPLIT = "#";

    private int extralVirNodeCount;

    public ConsistencyHashCluster() {
        this(DEFAULT_EXTRA_VIR_NODE_COUNT);
    }

    public ConsistencyHashCluster(int virNodeCount) {
        super();
        this.extralVirNodeCount = virNodeCount;
    }

    @Override
    public void addNode(ServerNode node) {
        allNodes.put(hash(node.getIp()), node);
        if (extralVirNodeCount > 0) {
            addExtraVirCodes(node);
        }
    }

    private void addExtraVirCodes(ServerNode node) {
        IntStream.range(0, extralVirNodeCount).forEach(index -> {
            int hash = hash(node.getIp() + SPLIT + index);
            allNodes.put(hash, node);
        });
    }

    @Override
    public void removeNode(ServerNode node) {
        allNodes.remove(hash(node.getIp()), node);
        if (extralVirNodeCount > 0) {
            removeExtraNode(node);
        }
    }

    private void removeExtraNode(ServerNode node) {
        IntStream.range(0, extralVirNodeCount).forEach(index -> {
            int hash = hash(node.getIp() + SPLIT + index);
            allNodes.remove(hash);
        });
    }

    @Override
    public ServerNode getNodeBy(String key) {
        int hash = hash(key);
        SortedMap<Integer, ServerNode> subMap = allNodes.tailMap(hash);
        Integer firstKey;
        if (subMap.isEmpty()) {
            firstKey = allNodes.firstKey();
        } else {
            firstKey = subMap.firstKey();
        }
        return allNodes.get(firstKey);
    }

    @Override
    public Collection<ServerNode> nodes() {
        return allNodes.values();
    }
}