package effets;
import java.util.ArrayList;

import main.Entite;
import main.TypeCible;
import main.Entite.Status;

public class Faiblesse extends Effet {

    public Faiblesse() {
        super(0, TypeCible.AUCUN);
    }

    public Faiblesse(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.Faiblesse,
                    cible.getStatusPoint(Entite.Status.Faiblesse) + this.pointEffet);
            System.out.println(
                    lanceur.getNom() + " donne " + this.pointEffet + " points de faiblesse à " + cible.getNom());
        }
    }

    @Override
    public String toString() {
        return "Faiblesse " + this.pointEffet;
    }
}
