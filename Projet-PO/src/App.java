import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) throws Exception {

        Hero Bob = new Hero("Bob");

        Partie.partie.jouerPartie();

    }
}
