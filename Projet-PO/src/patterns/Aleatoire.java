package patterns;

import java.util.Map.Entry;
import java.util.TreeMap;

import main.Action;
import main.Monstre;

/**
 * Pattern de monstre qui joue une action aléatoire parmi une liste d'actions possibles.
 */
public class Aleatoire implements Pattern {

    private TreeMap<Double, Action> actionsPossible = new TreeMap<Double, Action>();

    private Double indice = Math.random();

    public Aleatoire() {
        this.actionsPossible = new TreeMap<Double, Action>();
    }

    @Override
    public Action actionActuelle() {
        for (Entry<Double, Action> entry : this.actionsPossible.entrySet()) {
            if (this.indice <= entry.getKey()) {
                return entry.getValue();
            }
        }
        
        throw new Error(
                "Erreur dans le tirage aléatoire du pattern Aleatoire, aucune carte n'a été tirée");
    }


    @Override
    public void actionSuivante() {
        this.indice = Math.random();
    }

    @Override
    public Action intention() {
        return this.actionActuelle();
    }

    public TreeMap<Double, Action> getActionsPossible() {
        return actionsPossible;
    }

    public void setActionsPossible(TreeMap<Double, Action> actionsPossible) {
        this.actionsPossible = actionsPossible;
    }

    public Double getIndice() {
        return indice;
    }

    public void setIndice(Double indice) {
        this.indice = indice;
    }

}
