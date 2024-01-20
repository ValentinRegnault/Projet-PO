package patterns;

import java.util.ArrayList;
import main.Action;
import main.Monstre;

/**
 * Pattern de monstre qui a deux phases : une première phase qui effectue une liste d'action de
 * préparation, puis une deuxième phase qui effectue un autre pattern.
 * 
 * @implNote la phase 1 est représentée par un pattern cyclique qui n'effectura qu'un seul cycle.
 */
public class Preparation implements Pattern {
    private ArrayList<Action> phase1;
    private int indicePhase1;
    private Pattern phase2;

    public Preparation() {
        this.phase1 = new ArrayList<>();
        this.phase2 = new Aleatoire();
    }


    @Override
    public Action actionActuelle() {
        if (indicePhase1 < phase1.size()) {
            return phase1.get(indicePhase1);
        } else {
            return phase2.actionActuelle();
        }
    }

    @Override
    public void actionSuivante() {
        if (indicePhase1 < phase1.size()) {
            indicePhase1++;
        } else {
            phase2.actionSuivante();
        }
    }

    @Override
    public Action intention() {
        if (indicePhase1 < phase1.size()) {
            return phase1.get(indicePhase1);
        } else {
            return phase2.intention();
        }
    }

    public ArrayList<Action> getPhase1() {
        return phase1;
    }

    public void setPhase1(ArrayList<Action> phase1) {
        this.phase1 = phase1;
    }

    public Pattern getPhase2() {
        return phase2;
    }

    public void setPhase2(Pattern phase2) {
        this.phase2 = phase2;
    }

    public String toString() {
        return "Préparation :" + "Phase 1 :" + this.phase1.toString() + "\n"
                + this.phase2.toString();
    }

}
