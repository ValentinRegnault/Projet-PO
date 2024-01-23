package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

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

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {

            int degat = lanceur.getPointBlocage() * this.pointEffet;

            // Force -> Point de force ajoutés aux dégats
            degat += cible.getStatusPoint(Entite.Status.FORCE);

            // Faiblesse -> dégat à 75%
            if (cible.getStatusPoint(Entite.Status.FAIBLESSE) > 0) {
                degat = (int) Math.floor(degat * 0.75);
            }

            // Cible vulnérable - 50% de dégat en plus
            if (cible.getStatusPoint(Entite.Status.VULNERABLE) > 0) {
                degat = (int) Math.floor(degat * 1.5);
            }

            int pointBlocageCible = cible.getPointBlocage();
            cible.setPointBlocage(pointBlocageCible - degat);
            if (pointBlocageCible - degat < 0) {
                cible.setPointBlocage(0);
                int degatsRestants = Math.abs(pointBlocageCible - degat);
                cible.setPv(cible.getPv() - degatsRestants);
            }
        }
    }

    @Override
    public String toString() {
        return "Inflige x dégâts à un ennemi, avec x le nombre de points de blocage de l’entité qui utilise la carte";
    }

}
