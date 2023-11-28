import java.util.ArrayList;

public class Faiblesse extends Effet {

    public Faiblesse(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage
        for (Entite cible : ListeCible) {
            cible.setStatusPoint(Entite.Status.Faiblesse, cible.getStatusPoint(Entite.Status.Faiblesse) + this.pointEffet);
        }

       
    }



    @Override
    public String toString() {
        return "Vulnerabilité " + this.pointEffet;
    }

    
}
    

