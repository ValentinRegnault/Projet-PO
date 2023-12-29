package effets;
import java.util.ArrayList;

import main.Entite;
import main.TypeCible;
import main.Entite.Status;

public class Blocage extends Effet {

    public Blocage(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public Blocage() {
        super(0, TypeCible.AUCUN);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            // Fragile : 75% des points de blocage
            if (cible.getStatusPoint(Entite.Status.Fragile) > 0) {
                this.pointEffet = (int) Math.floor(0.75 * pointEffet);
            }

            cible.setPointBlocage(cible.getPointBlocage() + pointEffet);
            System.out.println(lanceur.getNom() + " gagne " + pointEffet + " points de blocage");
        }
    }

    @Override
    public String toString() {
        return "Donne " + this.pointEffet + " points de blocage au propriétaire de la carte.";
    }

}
