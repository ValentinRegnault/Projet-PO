import java.util.ArrayList;

public class Blocage extends Effet {


    public Blocage(int pointEffet){
        super(pointEffet);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage

        Entite cible = lanceur;

            // Fragile : 75% des points de blocage
            if(cible.getStatusPoint(Entite.Status.Fragile) > 0){
                this.pointEffet = (int)Math.floor(0.75*pointEffet);
            }

            cible.setPointBlocage(cible.getPointBlocage()+ pointEffet);
            System.out.println(cible.getNom() + " gagne " + pointEffet + " points de blocage");


        

       
    }


    @Override
    public String toString() {
        return "Donne " + this.pointEffet + " points de blocage au propriétaire de la carte."; 
    }

    
    
}
