package patterns;

import java.util.ArrayList;

import main.Action;
import main.Monstre;

/**
 * Pattern de monstre qui joue une liste d'action dans un ordre définie et
 * recommence eternellement.
 */
public class Cyclique implements Pattern {

    private ArrayList<Action> listeAction;
    private int indice;

    public Cyclique() {
        this.listeAction = new ArrayList<Action>();
        this.indice = 0;
    }

    public Cyclique(ArrayList<Action> listAction) {
        this.listeAction = listAction;
        this.indice = 0;
    }

    @Override
    public void jouerAction(Monstre lanceur) {
        this.actionActuelle().jouerAction(lanceur);
        this.actionSuivante();
    }

    public Action actionActuelle() {
        return this.listeAction.get(this.indice);
    }

    @Override
    public void actionSuivante() {
        this.indice = ((this.indice + 1) % listeAction.size());
    }

    @Override
    public Action intention() {
        return this.actionActuelle();
    }

    @Override
    public String toString() {
        return "Cyclique [listeAction=" + listeAction + ", indice=" + indice + "]";
    }

    public ArrayList<Action> getListeAction() {
        return listeAction;
    }

    public void setListeAction(ArrayList<Action> listeAction) {
        this.listeAction = listeAction;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
