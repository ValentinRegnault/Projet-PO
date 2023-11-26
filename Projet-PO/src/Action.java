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



    public void jouerAction(Entite lanceur, Entite cible){

        for(int i = 0; i < effetsLanceur.size(); i++){
            effetsLanceur.get(i).appliquerEffet(lanceur);
        }

        for(int i = 0; i < effetsCible.size(); i++){
            effetsCible.get(i).appliquerEffet(cible);
        }
        
    }



    @Override
    public String toString() {
        return "Action [nom=" + nom + ", effetsLanceur=" + effetsLanceur + ", effetsCible=" + effetsCible + "]";
    }


}
