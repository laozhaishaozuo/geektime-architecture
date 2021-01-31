package top.shaozuo.geektime.architecture.week05;

public abstract class AbstractCluster implements Cluster {

    protected int hash(String key) {
        return HashUtils.hash(key);
    }
}
