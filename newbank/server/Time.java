package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;

public class Time {

    static Instant instant = Instant.now();
    boolean n = false;

    public void timer(BufferedReader in, MenuPrinter menuPrinter) {
        while (true) {
            if (Instant.now().isAfter(instant.plusSeconds(180))) {
                //log out
                try {
                    in.close();
                    n = true;
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                menuPrinter.printLogOut();
            }
        }
    }

    public void reset() {
        instant = Instant.now();
    }
}
