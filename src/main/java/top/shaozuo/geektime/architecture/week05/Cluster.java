package top.shaozuo.geektime.architecture.week05;

import java.util.Collection;

/**
 * 表示集群
 * 
 * @author shaozuo
 *
 */
public interface Cluster {
    /**
     * 向集群中添加一个节点
     * 
     * @param node
     */
    public void addNode(ServerNode node);

    /**
     * 移除集群中一个节点
     * 
     * @param node
     */
    public void removeNode(ServerNode node);

    /**
     * 根据key值获取可能节点
     * 
     * @param key
     * @return
     */
    public ServerNode getNodeBy(String key);

    /**
     * 获取集群中所有的节点
     * 
     * @return
     */
    public Collection<ServerNode> nodes();
}
