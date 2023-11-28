import java.util.ArrayList;
import java.util.Arrays;


public class Carte {
    enum Rarete {
        Commun,
        NonCommun,
        Rare
    }

    private String nom;
    private Rarete rarete;
    private int cout;
    private ArrayList<Effet> effetsLanceur;
    private ArrayList<Effet> effetsCible;
    private boolean exile;


    public Carte(String nom, Carte.Rarete rarete, int cout, ArrayList<Effet> effetsLanceur, ArrayList<Effet> effetsCible, boolean exile) {
        this.nom = nom;
        this.rarete = rarete;
        this.cout = cout;
        this.effetsCible = effetsCible;
        this.effetsLanceur = effetsLanceur;
        this.exile = exile;
    }

    public void jouerCarte(Entite lanceur, ArrayList<Entite> listeCible){

        if(lanceur instanceof Hero hero){
            hero.setPointEnergie(hero.getPointEnergie() - this.cout);
        }

        for(int i = 0; i < effetsLanceur.size(); i++){
            effetsLanceur.get(i).appliquerEffet(lanceur, null);
        }

        for(int i = 0; i < effetsCible.size(); i++){
            effetsCible.get(i).appliquerEffet(lanceur, listeCible);
        }
        
    }

    @Override
    public String toString() {

        String StringEffetsCible = "";
        String StringEffetsLanceur = "";

        if(effetsLanceur.size() != 0){
            StringEffetsLanceur +=  "effetsLanceur=";

            for(int i = 0; i < effetsLanceur.size(); i++){
            StringEffetsLanceur += effetsLanceur.get(i).toString() + ",";
        }
        }
        

        if(effetsCible.size() != 0){
            StringEffetsCible +=  "effetsCible=";
            for(int i = 0; i < effetsCible.size(); i++){
                        StringEffetsCible += effetsCible.get(i).toString() + ",";
            }
        }
        

        return "Carte [nom=" + nom + ", rarete=" + rarete + ", cout=" + cout + ", " + StringEffetsLanceur
                + " " + StringEffetsCible + " exile=" + exile + "]";
    }

    
}
