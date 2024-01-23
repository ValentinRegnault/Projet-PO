package patterns;

import java.util.Map.Entry;
import java.util.Map;
import java.util.TreeMap;

import main.Action;

/**
 * Pattern de monstre qui joue une action aléatoire parmi une liste d'actions possibles.
 */
public class Aleatoire implements Pattern {

    private Map<Double, Action> actionsPossible = new TreeMap<>();

    private Double indice = Math.random();

    public Aleatoire() {
        this.actionsPossible = new TreeMap<>();
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


    public Map<Double, Action> getActionsPossible() {
        return actionsPossible;
    }

    public void setActionsPossible(Map<Double, Action> actionsPossible) {
        this.actionsPossible = actionsPossible;
    }

    public Double getIndice() {
        return indice;
    }

    public void setIndice(Double indice) {
        this.indice = indice;
    }

}
