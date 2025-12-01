package models;
import java.lang.Thread;

public class Attendre {
    public static void attendreInt(int s)
    {
        int ms = s * 1000;
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void attendreDouble(double s) {
    int ms = (int)(s * 1000); // conversion secondes → millisecondes
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
}
