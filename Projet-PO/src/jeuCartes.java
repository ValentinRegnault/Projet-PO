import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class jeuCartes {
    
    static Carte frappe = new Carte("Frappe", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(6))), false);

    static Carte defense = new Carte("Défense", Carte.Rarete.Commun, 1, new ArrayList<Effet>(Arrays.asList(new Blocage(5))), new ArrayList<Effet>(), false);

    static Carte heurt = new Carte("Heurt", Carte.Rarete.Commun, 2, new ArrayList<Effet>(), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(8), new Vulnerable(2))), false);

    // TODO: Même pas mal

    static Carte vagueDeFer = new Carte("Vague de fer", Carte.Rarete.Commun, 1, new ArrayList<Effet>(Arrays.asList(new Blocage(5))), new ArrayList<Effet>(Arrays.asList(new AppliqueDegat(5))), false);

    // TODO : Frappe du pommeau


}
