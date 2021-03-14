package top.shaozuo.geektime.architecture.week11;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Md5Utils {
    private Md5Utils() {

    }

    private static final String MD5 = "MD5";

    /**
     * 对传入的字符串进行md5运算,不加盐
     * 
     * @param input
     *            输入字符串
     * @return
     */
    public static String md5(String input) {
        return encodeHex(digest(input.getBytes(), null, 1));
    }

    /**
     * 对传入的字符串进行md5运算
     * 
     * @param input
     *            输入字符串
     * @param salt
     *            加盐
     * @return
     */
    public static String md5(String input, String salt) {
        return encodeHex(digest(input.getBytes(), salt.getBytes(), 1));
    }

    /**
     * 对字符串进行散列
     * 
     * @param input
     *            输入byte数组
     * @param salt
     *            加盐
     * @param iterations
     *            迭代次数
     * @return
     */
    private static byte[] digest(byte[] input, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hex编码.
     */
    private static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    /**
     * Hex解码.
     */
    private static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }
}
