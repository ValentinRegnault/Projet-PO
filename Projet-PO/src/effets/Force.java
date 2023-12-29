package effets;
import java.util.ArrayList;

import main.Entite;
import main.TypeCible;
import main.Entite.Status;

public class Force extends Effet {

    public Force() {
        super(0, TypeCible.AUCUN);
    }

    public Force(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.Force, cible.getStatusPoint(Entite.Status.Force) + this.pointEffet);
            System.out.println(lanceur.getNom() + " donne " + this.pointEffet + " points de force à " + cible.getNom());
        }
    }

    @Override
    public String toString() {
        return "Force " + this.pointEffet;
    }

}
