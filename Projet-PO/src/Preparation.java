import java.util.ArrayList;
import java.util.TreeMap;

public class Preparation implements Pattern {
    private Cyclique phase1;
    private Aleatoire phase2;

    public Preparation(ArrayList<Action> phase1ListeActions, TreeMap<Double, Action> phase2ActionsPossible) {
        this.phase1 = new Cyclique(phase1ListeActions);
        this.phase2 = new Aleatoire(phase2ActionsPossible);
    }

    public Preparation() {
        this.phase1 = new Cyclique();
        this.phase2 = new Aleatoire();
    }

    public Pattern patternActuel() {
        if (this.phase1.getIndice() >= this.phase1.getListeAction().size()) {
            return this.phase1;
        } else {
            return this.phase2;
        }
    }

    public void jouerAction(Monstre lanceur, ArrayList<Entite> listeCible) {
        patternActuel().jouerAction(lanceur, listeCible);
    }

    public void actionSuivante() {
        patternActuel().actionSuivante();
    }

    public String genererIntention() {
        return patternActuel().genererIntention();
    }

    public void afficherIntention() {
        patternActuel().afficherIntention();
    }


    public Cyclique getPhase1() {
        return phase1;
    }

    public void setPhase1(Cyclique phase1) {
        this.phase1 = phase1;
    }

    public Aleatoire getPhase2() {
        return phase2;
    }

    public void setPhase2(Aleatoire phase2) {
        this.phase2 = phase2;
    }
}
