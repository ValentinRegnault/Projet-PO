package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui inflige des dégats à la cible, en brisant d'abord son blocage.
 * 
 * @see Effet
 */
public class AppliqueDegat extends Effet {

    public AppliqueDegat() {
        super(0, TypeCible.AUCUN);
    }

    public AppliqueDegat(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            // Force -> Point de force ajoutés aux dégats
            int degats = this.pointEffet;
            degats += lanceur.getStatusPoint(Entite.Status.FORCE);

            // Faiblesse -> dégat à 75%
            if (lanceur.getStatusPoint(Entite.Status.FAIBLESSE) > 0) {
                degats = (int) Math.floor(degats * 0.75);
            }

            // Cible vulnérable - 50% de dégat en plus
            if (cible.getStatusPoint(Entite.Status.VULNERABLE) > 0) {
                degats = (int) Math.floor(pointEffet * 1.5);
            }

            int pointBlocageCible = cible.getPointBlocage();
            cible.setPointBlocage(pointBlocageCible - degats);
            if (pointBlocageCible - degats < 0) {
                cible.setPointBlocage(0);
                int degatsRestants = Math.abs(pointBlocageCible - degats);
                cible.setPv(cible.getPv() - degatsRestants);
            }
        }
    }

    @Override
    public String toString() {
        return "Inflige " + this.pointEffet + " points de dégats à la cible.";
    }

}
