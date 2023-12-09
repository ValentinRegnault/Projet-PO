import java.util.ArrayList;
import java.util.TreeMap;

//TODO : Changer le nom ou le mettre dans un package parceque la c'est pas clair "Aleatoire"
public class Aleatoire implements Pattern {
    
    private TreeMap<Double, Action> actionsPossible = new TreeMap<Double, Action>();

    private Double indice = Math.random();

    public Aleatoire(TreeMap<Double, Action> mapActionsPossible){
        // TODO: Doit être testé

        // Creation du dictionnaire avec probabilitées normalisées
        // Action1 : 30%
        // Action2 : 40%
        // Action3 : 30%
        // Le dictionnaire ressemblera à :
        // 0.3 - Action1, 0.7 - Action2, 1. Action2
        Double somme = 0.0;
        for(Double proba : mapActionsPossible.keySet()){
            somme += proba;
            this.actionsPossible.put(somme, mapActionsPossible.get(proba));
        }


    }

 

    public Action actionActuelle(){

        for(Double proba : this.actionsPossible.keySet()){
            if(this.indice <= proba){
                return this.actionsPossible.get(proba);
            }
        }

        // TODO : Ne devrait pas se produire?
        return null;

    }

    public void jouerAction(Monstre lanceur, ArrayList<Entite> listeCible){
        this.actionActuelle().jouerAction(lanceur, listeCible);
        this.actionSuivante();
    }

    public void actionSuivante(){
        this.indice = Math.random();
    }

    public void afficherIntention(){
        System.out.println(this.actionActuelle());

    }

    @Override
    public String toString() {
        String output = "";
        for(Double proba : this.actionsPossible.keySet()){
            output += proba + " - " + this.actionsPossible.get(proba) + "\n";
        }

        return output;
    }

    public String getIntention(){
        return this.actionActuelle().toString();
    }


}
