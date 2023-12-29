package effets;

import java.util.ArrayList;

import main.Entite;
import main.TypeCible;

/**
 * Effet qui applique autant de dégats que le points de blocage de la cible
 * 
 * @see Effet
 */
public class AppliqueDegatParBlocage extends Effet {
    // Les points de dégats sont ceux du lanceur
    public AppliqueDegatParBlocage() {
        super(0, TypeCible.AUCUN);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {

            this.pointEffet = cible.getPointBlocage();

            // Inflige prends en compte les points de blocage et les statuts

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
        return "Inflige x dégâts à un ennemi, avec x le nombre de points de blocage de l’entité qui utilise la carte";
    }

}
