package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import effets.AppliqueDegat;
import effets.Blocage;
import effets.Effet;
import effets.Faiblesse;
import effets.Force;
import effets.TypeCible;
import librairies.StdDraw;
import main.Carte.RareteCarte;
import patterns.Aleatoire;
import patterns.Cyclique;
import patterns.Preparation;
import ressources.AssociationTouches;
import ressources.Config;

public class App {
    public static void main(String[] args) throws Exception {

        // Partie.jouerPartie();
        // creerPetitSlimeAcide();

        // ObjectMapper mapper = new ObjectMapper();
        // Monstre petitSlimeAcide = mapper.readValue(new
        // File("./assets/monstres/PetitSlimeAcide.json"), Monstre.class);

        // System.out.println(petitSlimeAcide);
        // System.out.println(petitSlimeAcide.getPattern());

        // Monstre machouilleur = mapper.readValue(new
        // File("./assets/monstres/Machouilleur.json"), Monstre.class);

        // System.out.println(machouilleur);
        // System.out.println(machouilleur.getPattern());

        // Monstre petitSlimePiquant = mapper.readValue(new
        // File("./assets/monstres/PetitSlimePiquant.json"), Monstre.class);

        // System.out.println(petitSlimePiquant);
        // System.out.println(petitSlimePiquant.getPattern());

        // creerCarteFrappe();


        Partie partie = new Partie();

        partie.initPartie();

        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));
        partie.deck.ajouterCarteDansDefausse(Partie.instancierCarte("Frappe"));

        partie.jouerPartie();
    }

    private static void creerMachouilleur() {
        // Charge : Inflige 7 dégâts et gagne 5 points de blocage.
        AppliqueDegat degat = new AppliqueDegat(7, TypeCible.HERO);
        Blocage blocage = new Blocage(5, TypeCible.LANCEUR);
        ArrayList<Effet> effetsCharge = new ArrayList<>();
        effetsCharge.add(degat);
        effetsCharge.add(blocage);
        Action charge = new Action("Charge", effetsCharge);

        // Morsure : Inflige 11 degats.
        ArrayList<Effet> effetsMorsure = new ArrayList<>();
        effetsMorsure.add(new AppliqueDegat(11, TypeCible.HERO));
        Action morsure = new Action("Morsure", effetsMorsure);

        // Grondement : Gagne 6 points de blocage et 3 Force
        ArrayList<Effet> effetsGronder = new ArrayList<>();
        effetsGronder.add(new Blocage(6, TypeCible.LANCEUR));
        effetsGronder.add(new Force(3, TypeCible.LANCEUR));
        Action gronder = new Action("Gronder", effetsGronder);

        Preparation pattern = new Preparation();

        // Phase 1
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(charge);
        pattern.setPhase1(actions);

        // Phase 2
        Aleatoire a = new Aleatoire();
        TreeMap<Double, Action> actionsPossibles = new TreeMap<>();
        actionsPossibles.put(0.3, charge);
        actionsPossibles.put(0.25, morsure);
        actionsPossibles.put(0.45, gronder);
        a.setActionsPossible(actionsPossibles);
        pattern.setPhase2(a);

        Monstre m = new Monstre(
                "Mâchouilleur", 40, pattern, "assets" + File.separator + "pictures" + File.separator
                        + "monstres" + File.separator + "PetitSlimeAcide.png",
                0.0, 0.0, 356.0, 259.0);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("./assets/monstres/Machouilleur.json"), m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private static void creerPetitSlimeAcide() {
    // // Charge : Inflige 3 dégats
    // AppliqueDegat degat = new AppliqueDegat(3, TypeCible.HERO);
    // ArrayList<Effet> effetsCharge = new ArrayList<>();
    // effetsCharge.add(degat);
    // Action charge = new Action("Charge", effetsCharge);

    // // Lêcher : Inflige 1 Faible
    // ArrayList<Effet> effetsLecher = new ArrayList<>();
    // effetsLecher.add(new Faiblesse(1, TypeCible.HERO));
    // Action lecher = new Action("Lêcher", effetsLecher);

    // Cyclique pattern = new Cyclique();
    // ArrayList<Action> actions = new ArrayList<>();
    // actions.add(charge);
    // actions.add(lecher);
    // pattern.setListeAction(actions);

    // Monstre m = new Monstre(
    // "Petit Slime Acide", 40, pattern, "assets" + File.separator + "pictures"
    // + File.separator + "monstres" + File.separator + "PetitSlimeAcide.png",
    // 0.0, 0.0, 356.0, 259.0);

    // ObjectMapper mapper = new ObjectMapper();
    // try {
    // mapper.writeValue(new File("./assets/monstres/PetitSlimeAcide.json"), m);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    private static void creerCarteFrappe() {
        ArrayList<Effet> effets = new ArrayList<>();
        effets.add(new AppliqueDegat(6, TypeCible.SELECTION_JOUEUR));

        Carte frappe = new Carte("Frappe", RareteCarte.Commun, 1, effets, false,
                "Inflige 6 dégâts.", Carte.EtatCarte.DANS_PIOCHE);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("./assets/cartes/Frappe.json"), frappe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
