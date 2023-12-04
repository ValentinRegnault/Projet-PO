import java.util.ArrayList;

public class Partie {
    private ArrayList<Salle> salles;
    private int indiceSalle;

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

    private Partie () {
        this.salles = new ArrayList<Salle>();
        
        ArrayList<Monstre> monstres = new ArrayList<Monstre>();
        monstres.add(new Monstre("Gobelin", 10, new Aleatoire(null)));
        this.salles.add(new SalleMonstre(monstres));
    
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
}
