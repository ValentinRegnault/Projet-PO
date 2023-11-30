import java.util.ArrayList;

public class Partie {

    static Partie partie = new Partie();
    
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
    private ArrayList<Salle> salles;
    private int indiceSalle;
    private ArrayList<Carte> deck;
    private ArrayList<Carte> defausse;
    private ArrayList<Carte> exile;
    private ArrayList<Carte> pioche;
}
