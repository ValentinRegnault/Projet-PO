import java.util.ArrayList;
import java.util.TreeMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Partie {
    private ArrayList<Salle> salles;
    private int indiceSalle;

    private final TreeMap<String, File> monstresAssetParNom;

    // Deck : Ensemble des cartes du joueur
    private ArrayList<Carte> deck;

    // Main : Cartes actuellement dans la main du joueur
    private ArrayList<Carte> main;

    // Defausse : Pile de cartes utilisées
    private ArrayList<Carte> defausse;

    // Exile : Pile de cartes utilisées ayant le statut Exile
    private ArrayList<Carte> exile;

    // Pioche : Carte dans laquelle le joueur peut piocher
    private ArrayList<Carte> pioche;

    protected Hero hero;

    static Partie partie = new Partie();

    private Partie() {

        File[] files = new File("./assets/monstres").listFiles();
        this.monstresAssetParNom = new TreeMap<String, File>();
        ObjectMapper mapper = new ObjectMapper();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Monstre m = mapper.readValue(file, Monstre.class);
                    this.monstresAssetParNom.put(m.nom, file);
                } catch (IOException ioe) {
                    System.out.println("Erreur lors de la lecture du fichier de monstre " + file.getName());
                }
            }
        }

        this.salles = new ArrayList<Salle>();
        this.deck = new ArrayList<Carte>();
        this.main = new ArrayList<Carte>();
        this.defausse = new ArrayList<Carte>();
        this.exile = new ArrayList<Carte>();
        this.pioche = new ArrayList<Carte>();
    }

    public void jouerPartie() {
        for (Salle salle : salles) {
            if (!salle.jouerSalle()) {
                System.out.println("Vous avez perdu");
                return;
            }

            indiceSalle++;
            obtenirRecompense();
        }

        System.out.println("Vous avez gagné");

    }

    public void piocheCarte() {

        Carte carte = pioche.get(pioche.size() - 1);
        // On prends la carte au dessus de la pioche

        pioche.remove(pioche.size() - 1);

        // On ajoute cette carte à la main
        main.add(carte);

    }

    public void defausseCarte(int index) {
        // index de la carte dans la main

        Carte carteUtilise = this.main.get(index);
        this.main.remove(index);

        if (carteUtilise.isExile()) {
            exile.add(carteUtilise);
        } else {
            defausse.add(carteUtilise);
        }
    }

    public void obtenirRecompense() {
        // TODO
    }

    public void videDefausse() {
        this.defausse = new ArrayList<Carte>();
    }

    public void videExile() {
        this.exile = new ArrayList<Carte>();
    }

    public void videMain() {
        this.main = new ArrayList<Carte>();
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }

    public int getIndiceSalle() {
        return indiceSalle;
    }

    public Salle getSalleActuelle() {
        return salles.get(indiceSalle);
    }

    public ArrayList<Carte> getDeck() {
        return deck;
    }

    public ArrayList<Carte> getDefausse() {
        return defausse;
    }

    public ArrayList<Carte> getExile() {
        return exile;
    }

    public ArrayList<Carte> getPioche() {
        return pioche;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }
}
