package viktor.tsvetkov.conversations.utils;

public final class RandomUtils {
    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
