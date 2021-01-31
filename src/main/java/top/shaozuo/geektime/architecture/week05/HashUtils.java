package top.shaozuo.geektime.architecture.week05;

public final class HashUtils {

    private HashUtils() {

    }

    /***
     * http://www.isthe.com/chongo/tech/comp/fnv/
     */
    private static final int FNV_32_PRIME = 16777619;
    private static final int FNV_32_INIT = (int) 2166136261L;

    private static int fnv1a32(final byte[] k) {
        int rv = FNV_32_INIT;
        final int len = k.length;
        for (int i = 0; i < len; i++) {
            rv ^= k[i];
            rv *= FNV_32_PRIME;
        }
        return rv;
    }

    private static int fnv1a32(String key) {
        int rv = FNV_32_INIT;
        final int len = key.length();
        for (int i = 0; i < len; i++) {
            rv ^= key.charAt(i);
            rv *= FNV_32_PRIME;
        }
        return rv;
    }

    public static int hash(String key) {
        return fnv1a32(key);
    }
}
