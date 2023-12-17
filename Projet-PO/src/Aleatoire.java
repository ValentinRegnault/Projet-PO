import java.util.ArrayList;
import java.util.TreeMap;

//TODO : Changer le nom ou le mettre dans un package parceque la c'est pas clair "Aleatoire"
public class Aleatoire implements Pattern {
    
    private TreeMap<Double, Action> actionsPossible = new TreeMap<Double, Action>();

    private Double indice = Math.random();

    public Aleatoire(){
        this.actionsPossible = new TreeMap<Double, Action>();
    }
    
    public Action actionActuelle(){
        double cumul = 0.0;
        for(Double proba : this.actionsPossible.keySet()){
            cumul += proba;
            if(this.indice <= cumul){
                return this.actionsPossible.get(proba);
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

    @Override
    public String toString() {
        String output = "";
        for(Double proba : this.actionsPossible.keySet()){
            output += proba + " - " + this.actionsPossible.get(proba) + "\n";
        }

        return output;
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
