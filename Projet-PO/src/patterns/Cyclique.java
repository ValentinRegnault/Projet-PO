package patterns;

import java.util.List;
import java.util.ArrayList;

import main.Action;

/**
 * Pattern de monstre qui joue une liste d'action dans un ordre définie et recommence eternellement.
 */
public class Cyclique implements Pattern {

    private List<Action> listeAction;
    private int indice;

    public Cyclique() {
        this.listeAction = new ArrayList<>();
        this.indice = 0;
    }

    public Cyclique(List<Action> listAction) {
        this.listeAction = listAction;
        this.indice = 0;
    }

    @Override
    public Action actionActuelle() {
        return this.listeAction.get(this.indice);
    }

    @Override
    public void actionSuivante() {
        this.indice = ((this.indice + 1) % listeAction.size());
    }

    @Override
    public String toString() {
        return "Cyclique [listeAction=" + listeAction + ", indice=" + indice + "]";
    }

    public List<Action> getListeAction() {
        return listeAction;
    }

    public void setListeAction(List<Action> listeAction) {
        this.listeAction = listeAction;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
