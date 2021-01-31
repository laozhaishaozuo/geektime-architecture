package top.shaozuo.geektime.architecture.week05;

public final class MathUtils {
    private MathUtils() {

    }

    public static double calCStandardDeviation(int[] array) {
        int sum = 0;
        int len = array.length;
        for (int i = 0; i < len; i++) {
            sum += array[i]; //求出数组的总和
        }
        double average = sum / len; //求出数组的平均数
        double total = 0;
        for (int i = 0; i < len; i++) {
            total += (array[i] - average) * (array[i] - average); //求出方差，如果要计算方差的话这一步就可以了
        }
        return Math.sqrt(total / len);
    }
}
