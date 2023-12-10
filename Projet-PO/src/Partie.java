import java.util.ArrayList;
import java.util.TreeMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.LinkedList;

public class Partie {
    private ArrayList<Salle> salles;
    private int indiceSalle;

    private final TreeMap<String, File> monstresAssetParNom;
    private final TreeMap<String, File> cartesAssetParNom;

    // Deck : Ensemble des cartes du joueur
    private ArrayList<Carte> deck;

    // Main : Cartes actuellement dans la main du joueur
    private ArrayList<Carte> main;

    // Defausse : Pile de cartes utilisées
    private ArrayList<Carte> defausse;

    // Exile : Pile de cartes utilisées ayant le statut Exile
    private ArrayList<Carte> exile;

    // Pioche : Carte dans laquelle le joueur peut piocher
    private LinkedList<Carte> pioche;

    protected Hero hero;

    static Partie partie = new Partie();

    private Partie() {
        this.monstresAssetParNom = new TreeMap<String, File>();

        File dir = new File("Projet-PO/assets/monstres");
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Le répertoire n'existe pas ou ne peut pas être lu");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            for (File file : dir.listFiles()) {
                if (!file.isFile())
                    continue;

                try {
                    Monstre m = mapper.readValue(file, Monstre.class);
                    this.monstresAssetParNom.put(m.nom, file);
                } catch (IOException ioe) {
                    System.out.println("Erreur lors de la lecture du fichier de monstre " + file.getName());
                    ioe.printStackTrace();
                }
            }
        }

        this.cartesAssetParNom = new TreeMap<String, File>();
        dir = new File("Projet-PO/assets/cartes");
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Le répertoire n'existe pas ou ne peut pas être lu");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            for (File file : dir.listFiles()) {
                if (!file.isFile())
                    continue;

                try {
                    Carte c = mapper.readValue(file, Carte.class);
                    this.cartesAssetParNom.put(c.getNom(), file);
                } catch (IOException ioe) {
                    System.out.println("Erreur lors de la lecture du fichier de carte " + file.getName());
                    ioe.printStackTrace();
                }
            }
        }

        System.out.println(this.monstresAssetParNom);
        System.out.println(this.cartesAssetParNom);

        this.salles = new ArrayList<Salle>();
        this.deck = new ArrayList<Carte>();
        this.main = new ArrayList<Carte>();
        this.defausse = new ArrayList<Carte>();
        this.exile = new ArrayList<Carte>();
        this.pioche = new LinkedList<Carte>();
    }

    public void jouerPartie() {
        for (Salle salle : salles) {
            genererPioche();
            creerMain();

            if (!salle.jouerSalle()) {
                System.out.println("Vous avez perdu");
                return;
            }
            main.clear();
            defausse.clear();

            if (salles.get(indiceSalle) instanceof SalleCombat) {
                obtenirRecompense();

            }
            indiceSalle++;

        }

        System.out.println("Vous avez gagné");

    }

    public void piocheCarte() {
        // Si la pioche est vide
        if (pioche.isEmpty()) {
            // Remettre toutes les cartes de la défausse dans la pioche
            while (!defausse.isEmpty()) {
                pioche.push(defausse.pop());
            }

            // Mélanger la pioche
            Collections.shuffle(pioche);
        }

        // Si la pioche n'est pas vide après avoir remis les cartes de la défausse
        if (!pioche.isEmpty()) {
            // Prendre la carte du dessus de la pioche
            Carte carte = pioche.pop();

            // Ajouter cette carte à la main
            main.add(carte);
        }
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

    public void exileCarte(int index) {
        // index de la carte dans la main

        Carte carteUtilise = this.main.get(index);
        this.main.remove(index);

        exile.add(carteUtilise);
    }

    public void ajouterCarte(Carte carte) {
        this.deck.add(carte);
    }

    public void genererPioche() {

        pioche.clear();
        for (Carte carte : deck) {
            try {
                pioche.push((Carte) carte.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(pioche);
    }

    public void creerMain() {
        main.clear();
        for (int i = 0; i < 5; i++) {
            if (!pioche.isEmpty()) {
                main.add(pioche.pop());
            }
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

    public LinkedList<Carte> getPioche() {
        return pioche;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }
}
