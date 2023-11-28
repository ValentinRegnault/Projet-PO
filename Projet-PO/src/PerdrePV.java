import java.util.ArrayList;

public class PerdrePV extends Effet {
    
    public PerdrePV(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage et les statuts
        for (Entite cible : ListeCible) {

            cible.setPv(cible.getPv() - pointEffet);
        }

       
    }



    @Override
    public String toString() {
        return "Inflige " + this.pointEffet;
    }

    
}

