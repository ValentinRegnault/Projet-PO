package patterns;
import java.util.ArrayList;

import effets.Action;
import main.Monstre;

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

    public void jouerAction(Monstre lanceur) {
        this.actionActuelle().jouerAction(lanceur);
        this.actionSuivante();
    }

    public Action actionActuelle() {

        return this.listeAction.get(this.indice);
    }

    public void actionSuivante() {

        this.indice = ((this.indice + 1) % listeAction.size());
    }

    @Override
    public String toString() {
        return "Cyclique [listeAction=" + listeAction + ", indice=" + indice + "]";
    }

    public Action intention() {
        return this.actionActuelle();
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
