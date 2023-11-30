import java.util.ArrayList;

public class Fragile extends Effet {

    public Fragile(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage
        for (Entite cible : ListeCible) {
            cible.setStatusPoint(Entite.Status.Fragile, cible.getStatusPoint(Entite.Status.Fragile) + this.pointEffet);
            System.out.println(lanceur.getNom() + " donne " + this.pointEffet + " points de fragilité à " + cible.getNom());
        }

       
    }



    @Override
    public String toString() {
        return "Fragile " + this.pointEffet;
    }

    
}
    

