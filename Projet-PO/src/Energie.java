import java.util.ArrayList;

public class Energie extends Effet {

    public Energie(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage
        if(lanceur instanceof Hero hero){
                hero.setPointEnergie(hero.getPointEnergie() + pointEffet);
        }

       
    }



    @Override
    public String toString() {
        return "Vulnerabilité " + this.pointEffet;
    }

    
}
    

