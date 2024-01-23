package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute des points de fragilité à la cible.
 * 
 * @see Effet
 */
public class Fragile extends Effet {

    public Fragile() {
        super(0, TypeCible.AUCUN);
    }

    public Fragile(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.FRAGILE,
                    cible.getStatusPoint(Entite.Status.FRAGILE) + this.pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Fragile " + this.pointEffet;
    }

}
