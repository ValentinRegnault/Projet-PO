import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) throws Exception {

        Hero Bob = new Hero("Bob");
        // System.out.println(jeuCartes.saignee);

        // ArrayList<Effet> effets = new ArrayList<Effet>();
        // effets.add(new AppliqueDegat(5));

        // ArrayList<Action> actions = new ArrayList<Action>();
        // actions.add(new Action("Charge", new ArrayList<>(), effets));

        // Pattern pat = new Cyclique(actions);
        // Monstre monstre = new Monstre("Test", 12, pat);

        // ObjectMapper mapper = new ObjectMapper();
        // mapper.writeValue(new
        // File("./Projet-PO/assets/monstres/PetitSlimePiquant.json"), monstre);

        // Monstre m = mapper.readValue(new
        // File("./Projet-PO/assets/monstres/PetitSlimePiquant.json"), Monstre.class);

        // System.out.println(m);

        // ArrayList<Effet> effetsCibles = new ArrayList<Effet>();
        // effetsCibles.add(new AppliqueDegat(6));

        // Carte carte = new Carte(
        //         "Frappe",
        //         RareteCarte.Commun,
        //         1,
        //         new ArrayList<Effet>(),
        //         effetsCibles,
        //         false,
        //         true,
        //         "Inflige 6 dégats à une cible");
        
        // ObjectMapper mapper = new ObjectMapper();
        // mapper.writeValue(new File("./Projet-PO/assets/cartes/Frappe.json"), carte);

        // Carte c = mapper.readValue(new File("./Projet-PO/assets/cartes/Frappe.json"), Carte.class);
        // System.out.println(c);

        Partie.partie.jouerPartie();

    }
}
