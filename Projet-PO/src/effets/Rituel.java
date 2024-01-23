package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute des points de rituel à la cible.
 * 
 * @see Effet
 */
public class Rituel extends Effet {
    public Rituel() {
        super(0, TypeCible.AUCUN);
    }

    public Rituel(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            cible.setStatusPoint(Entite.Status.RITUEL,
                    cible.getStatusPoint(Entite.Status.RITUEL) + this.pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Ajoute " + this.pointEffet + " points de rituel à la cible.";
    }
}
