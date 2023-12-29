package effets;

import java.util.ArrayList;

import main.Entite;
import main.TypeCible;

/**
 * Effet qui ajoute des points de vulnérabilité à la cible.
 * 
 * @see Effet
 */
public class Vulnerable extends Effet {

    public Vulnerable() {
        super(0, TypeCible.AUCUN);
    }

    public Vulnerable(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.Vulnérable,
                    cible.getStatusPoint(Entite.Status.Vulnérable) + this.pointEffet);
            System.out.println(
                    lanceur.getNom() + " donne " + this.pointEffet + " points de vulnérabilité à " + cible.getNom());

        }
    }

    @Override
    public String toString() {
        return "Ajoute " + this.pointEffet + " points de vulnérabilité à la cible.";
    }

}
