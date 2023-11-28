import java.util.ArrayList;

public interface Pattern {
    

    public void jouerAction(Monstre lanceur, ArrayList<Entite> listeCible);

    public abstract void actionSuivante();

    public abstract void afficherIntention();

}
