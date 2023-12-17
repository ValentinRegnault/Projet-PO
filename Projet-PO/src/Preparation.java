import java.util.ArrayList;
import java.util.TreeMap;

public class Preparation implements Pattern {
    private Cyclique phase1;
    private Pattern phase2;

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

    public void jouerAction(Monstre lanceur) {
        patternActuel().jouerAction(lanceur);
    }

    public void actionSuivante() {
        patternActuel().actionSuivante();
    }

    public Action intention() {
        return patternActuel().intention();
    }

    public Cyclique getPhase1() {
        return phase1;
    }

    public void setPhase1(Cyclique phase1) {
        this.phase1 = phase1;
    }

    public Pattern getPhase2() {
        return phase2;
    }

    public void setPhase2(Pattern phase2) {
        this.phase2 = phase2;
    }

    public String toString() {
        return "Préparation :" + "Phase 1 :" + this.phase1.toString() + "\n" + this.phase2.toString();
    }

}

