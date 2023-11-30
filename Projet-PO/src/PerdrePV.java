import java.util.ArrayList;

public class PerdrePV extends Effet {
    
    public PerdrePV(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage et les statuts
        for (Entite cible : ListeCible) {

            cible.setPv(cible.getPv() - pointEffet);
            System.out.println(lanceur.getNom() + " fait perdre " + this.pointEffet + " points de vie à " + cible.getNom());
        }

       
    }



    @Override
    public String toString() {
        return "Fait perdre " + this.pointEffet + " points de vie à la cible.";
    }

    
}

