import java.util.ArrayList;

public class Force extends Effet {

    public Force(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage
        for (Entite cible : ListeCible) {
            cible.setStatusPoint(Entite.Status.Force, cible.getStatusPoint(Entite.Status.Force) + this.pointEffet);
        }

       
    }



    @Override
    public String toString() {
        return "Vulnerabilité " + this.pointEffet;
    }

    
}
    

