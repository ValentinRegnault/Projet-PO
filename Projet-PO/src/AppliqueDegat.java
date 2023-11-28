import java.util.ArrayList;

public class AppliqueDegat extends Effet {
    
    public AppliqueDegat(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> ListeCible){

        // Inflige prends en compte les points de blocage et les statuts
        for (Entite cible : ListeCible) {

            int pointBlocageCible = cible.getPointBlocage();

            // Force -> Point de force ajoutés aux dégats
            this.pointEffet += lanceur.getStatusPoint(Entite.Status.Force);

            // Faiblesse -> dégat à 75% 
            if(lanceur.getStatusPoint(Entite.Status.Faiblesse) > 0){
                this.pointEffet = (int)Math.floor(this.pointEffet*0.75);
            }

            // Cible vulnérable - 50% de dégat en plus
            if(cible.getStatusPoint(Entite.Status.Vulnérable) > 0){
                this.pointEffet = (int)Math.floor(pointEffet*1.5);
            }

            

            


            cible.setPointBlocage(pointBlocageCible - this.pointEffet);
            int degatInflige = Math.abs(pointBlocageCible - this.pointEffet);
            cible.setPv(cible.getPv() - degatInflige);
        }

       
    }



    @Override
    public String toString() {
        return "Inflige " + this.pointEffet;
    }

    
}

