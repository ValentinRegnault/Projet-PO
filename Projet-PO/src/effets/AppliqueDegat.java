package effets;
import java.util.ArrayList;

import main.Entite;
import main.TypeCible;

/**
 * Effet qui inflige des dégats à la cible, en brisant d'abord son blocage.
 * @see Effet
 */
public class AppliqueDegat extends Effet {

    public AppliqueDegat() {
        super(0, TypeCible.AUCUN);
    }

    public AppliqueDegat(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {

            int pointBlocageCible = cible.getPointBlocage();

            // Force -> Point de force ajoutés aux dégats
            this.pointEffet += cible.getStatusPoint(Entite.Status.Force);

            // Faiblesse -> dégat à 75%
            if (cible.getStatusPoint(Entite.Status.Faiblesse) > 0) {
                this.pointEffet = (int) Math.floor(this.pointEffet * 0.75);
            }

            // Cible vulnérable - 50% de dégat en plus
            if (cible.getStatusPoint(Entite.Status.Vulnérable) > 0) {
                this.pointEffet = (int) Math.floor(pointEffet * 1.5);
            }

            cible.setPointBlocage(pointBlocageCible - this.pointEffet);
            int degatInflige = Math.abs(pointBlocageCible - this.pointEffet);
            cible.setPv(cible.getPv() - degatInflige);
            System.out.println(lanceur.getNom() + " inflige " + degatInflige + " dégats à " + cible.getNom());
        }
    }

    @Override
    public String toString() {
        return "Inflige " + this.pointEffet + " points de dégats à la cible.";
    }

}
