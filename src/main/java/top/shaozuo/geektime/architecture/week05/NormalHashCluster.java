package top.shaozuo.geektime.architecture.week05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NormalHashCluster extends AbstractCluster {

    protected List<ServerNode> nodes;

    public NormalHashCluster() {
        super();
        nodes = new ArrayList<>();
    }

    @Override
    public void addNode(ServerNode node) {
        this.nodes.add(node);
    }

    @Override
    public void removeNode(ServerNode node) {
        this.nodes.removeIf(o -> o.equals(node));
    }

    @Override
    public ServerNode getNodeBy(String key) {
        int hash = hash(key);
        int index = hash % nodes.size();
        return nodes.get(Math.abs(index));
    }

    @Override
    public Collection<ServerNode> nodes() {
        return nodes;
    }
}