import java.util.Arrays;
import java.util.ArrayList;

public class Action implements java.io.Serializable {

    private String nom;
    private ArrayList<Effet> effetsLanceur;
    private ArrayList<Effet> effetsCible;

    public Action () {
        this.nom = "";
        this.effetsLanceur = new ArrayList<Effet>();
        this.effetsCible = new ArrayList<Effet>();
    }

    public Action(String nom, ArrayList<Effet> effetsLanceur, ArrayList<Effet> effetsCible) {
        this.nom = nom;
        
        if ( effetsLanceur == null){
            this.effetsLanceur = new ArrayList<Effet>();
        } else {
            this.effetsLanceur = effetsLanceur;
        }

        if (effetsCible == null){
            this.effetsCible = new ArrayList<Effet>();
        } else {
            this.effetsCible = effetsCible;
        }


        
    }



    public void jouerAction(Entite lanceur, ArrayList<Entite> listeCible){

        if (listeCible == null){
            listeCible = new ArrayList<Entite>();
        }

        for(int i = 0; i < effetsLanceur.size(); i++){
            effetsLanceur.get(i).appliquerEffet(lanceur, listeCible);
        }

        for(int i = 0; i < effetsCible.size(); i++){
            effetsCible.get(i).appliquerEffet(lanceur,listeCible);
        }
        
    }



    @Override
    public String toString() {
        return "Action [nom=" + nom + ", effetsLanceur=" + effetsLanceur + ", effetsCible=" + effetsCible + "]";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Effet> getEffetsLanceur() {
        return effetsLanceur;
    }

    public void setEffetsLanceur(ArrayList<Effet> effetsLanceur) {
        this.effetsLanceur = effetsLanceur;
    }

    public ArrayList<Effet> getEffetsCible() {
        return effetsCible;
    }

    public void setEffetsCible(ArrayList<Effet> effetsCible) {
        this.effetsCible = effetsCible;
    }

    
}
