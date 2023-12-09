import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) throws Exception {
        
        Hero Bob = new Hero("Bob");
        // System.out.println(jeuCartes.saignee);

        ArrayList<Action> actions = new ArrayList<Action>();

        ArrayList<Effet> effets = new ArrayList<Effet>();
        effets.add(new AppliqueDegat(2));
        actions.add(new Action("atq", new ArrayList<>(), effets));

        Pattern pat = new Cyclique(actions);
        Monstre monstre = new Monstre("Test", 10, pat);

        ObjectMapper mapper = new ObjectMapper();

        Partie.partie.jouerPartie();

    }
}
