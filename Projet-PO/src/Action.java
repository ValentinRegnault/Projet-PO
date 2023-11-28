import java.util.Arrays;
import java.util.ArrayList;

public class Action {

    private String nom;
    private ArrayList<Effet> effetsLanceur;
    private ArrayList<Effet> effetsCible;

    public Action(String nom, ArrayList<Effet> effetsLanceur, ArrayList<Effet> effetsCible) {
        this.nom = nom;
        this.effetsLanceur = effetsLanceur;
        this.effetsCible = effetsCible;
    }



    public void jouerAction(Entite lanceur, ArrayList<Entite> listeCible){

        for(int i = 0; i < effetsLanceur.size(); i++){
            effetsLanceur.get(i).appliquerEffet(lanceur, null);
        }

        for(int i = 0; i < effetsCible.size(); i++){
            effetsCible.get(i).appliquerEffet(lanceur,listeCible);
        }
        
    }



    @Override
    public String toString() {
        return "Action [nom=" + nom + ", effetsLanceur=" + effetsLanceur + ", effetsCible=" + effetsCible + "]";
    }


}
