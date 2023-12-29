package patterns;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import effets.Action;
import main.Monstre;

public class Aleatoire implements Pattern {
    
    private TreeMap<Double, Action> actionsPossible = new TreeMap<Double, Action>();

    private Double indice = Math.random();

    public Aleatoire(){
        this.actionsPossible = new TreeMap<Double, Action>();
    }
    
    public Action actionActuelle(){
        for(Entry<Double, Action> entry : this.actionsPossible.entrySet()){
            if(this.indice <= entry.getKey()){
                return entry.getValue();
            }
        }

        throw new Error("Erreur dans le tirage aléatoire du pattern Aleatoire, aucune carte n'a été tirée");
    }

    public void jouerAction(Monstre lanceur){
        this.actionActuelle().jouerAction(lanceur);
        this.actionSuivante();
    }

    public void actionSuivante(){
        this.indice = Math.random();
    }

    public Action intention(){
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