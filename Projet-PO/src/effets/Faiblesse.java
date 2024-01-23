package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute des points de faiblesses à la cible.
 * 
 * @see Effet
 */
public class Faiblesse extends Effet {

    public Faiblesse() {
        super(0, TypeCible.AUCUN);
    }

    public Faiblesse(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef, Heros herosRef,
            List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            cible.setStatusPoint(Entite.Status.FAIBLESSE,
                    cible.getStatusPoint(Entite.Status.FAIBLESSE) + this.pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Faiblesse " + this.pointEffet;
    }
}
