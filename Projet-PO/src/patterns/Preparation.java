package patterns;

import main.Action;
import main.Monstre;

/**
 * Pattern de monstre qui a deux phases : une première phase qui effectue une
 * liste d'action de préparation, puis une deuxième phase qui effectue un autre
 * pattern.
 * 
 * @implNote la phase 1 est représentée par un pattern cyclique qui n'effectura
 *           qu'un seul cycle.
 */
public class Preparation implements Pattern {
    // TODO : changer le pattern cyclique par une liste d'action
    private Cyclique phase1;
    private Pattern phase2;

    public Preparation() {
        this.phase1 = new Cyclique();
        this.phase2 = new Aleatoire();
    }

    /**
     * Détermine dans quel phase on est et retourne le pattern correspondant.
     * @return le pattern actuel
     */
    public Pattern patternActuel() {
        if (this.phase1.getIndice() >= this.phase1.getListeAction().size()) {
            return this.phase1;
        } else {
            return this.phase2;
        }
    }

    @Override
    public void jouerAction(Monstre lanceur) {
        patternActuel().jouerAction(lanceur);
    }

    @Override
    public void actionSuivante() {
        patternActuel().actionSuivante();
    }

    @Override
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
