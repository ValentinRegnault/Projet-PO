package effets;
import java.util.ArrayList;

import main.Entite;
import main.TypeCible;

/**
 * Effet qui ajoute des points de fragilité à la cible.
 * @see Effet
 */
public class Fragile extends Effet {

    public Fragile() {
        super(0, TypeCible.AUCUN);
    }

    public Fragile(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.Fragile, cible.getStatusPoint(Entite.Status.Fragile) + this.pointEffet);
            System.out.println(
                    lanceur.getNom() + " donne " + this.pointEffet + " points de fragilité à " + cible.getNom());
        }
    }

    @Override
    public String toString() {
        return "Fragile " + this.pointEffet;
    }

}
