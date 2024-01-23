package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute des points de force à la cible.
 * 
 * @see Effet
 */
public class Force extends Effet {

    public Force() {
        super(0, TypeCible.AUCUN);
    }

    public Force(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            cible.setStatusPoint(Entite.Status.FORCE,
                    cible.getStatusPoint(Entite.Status.FORCE) + this.pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Force " + this.pointEffet;
    }

}
