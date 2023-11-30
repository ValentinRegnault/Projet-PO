import java.util.ArrayList;

public class Energie extends Effet {

    public Energie(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage
        if(lanceur instanceof Hero hero){
                hero.setPointEnergie(hero.getPointEnergie() + pointEffet);
                System.out.println(lanceur.getNom() + " gagne " + pointEffet + " points d'énergie");
        }

       
    }



    @Override
    public String toString() {
        return "Le lanceur gagne " + this.pointEffet + " points d'énergie.";
    }

    
}
    

