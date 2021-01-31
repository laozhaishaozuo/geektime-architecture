package top.shaozuo.geektime.architecture.week05;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 表示一致性hash算法中存储的节点
 * 
 * @author shaozuo
 *
 */
public class ServerNode {

    private final String ip;
    private final String name;
    private final Map<String, String> store;

    public ServerNode(String ip, String name) {
        super();
        this.ip = ip;
        this.name = name;
        this.store = new HashMap<>();
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ServerNode))
            return false;
        ServerNode other = (ServerNode) obj;
        return Objects.equals(ip, other.ip) && Objects.equals(name, other.name);
    }

    public void put(String key, String data) {
        store.put(key, data);
    }

    public String get(String key) {
        return store.get(key);
    }

    public int size() {
        return store.size();
    }

    @Override
    public String toString() {
        return "ServerNode [ip=" + ip + ", name=" + name + "]";
    }
}
