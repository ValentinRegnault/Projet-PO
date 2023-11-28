import java.util.ArrayList;

public class Vulnerable extends Effet {

    public Vulnerable(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage
        for (Entite cible : ListeCible) {
            cible.setStatusPoint(Entite.Status.Vulnérable, cible.getStatusPoint(Entite.Status.Vulnérable) + this.pointEffet);
        }

       
    }



    @Override
    public String toString() {
        return "Vulnerabilité " + this.pointEffet;
    }

    
}
    

