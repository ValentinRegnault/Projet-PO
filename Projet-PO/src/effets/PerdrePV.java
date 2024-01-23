package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui fait perdre des points de vie à la cible, en outrepassant les points de blocage.
 * 
 * @see Effet
 */
public class PerdrePV extends Effet {
    public PerdrePV() {
        super(0, TypeCible.AUCUN);
    }

    public PerdrePV(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            cible.setPv(cible.getPv() - pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Fait perdre " + this.pointEffet + " points de vie à la cible.";
    }

}
