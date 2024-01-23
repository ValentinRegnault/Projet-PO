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
public class Pioche extends Effet {

    public Pioche() {
        super(0, TypeCible.AUCUN);
    }

    public Pioche(int nbCartes, TypeCible typeCible) {
        super(nbCartes, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (int i = 0; i < this.pointEffet; i++) {
            deckRef.piocherCarte();
        }
    }

    @Override
    public String toString() {
        return "Pioche " + this.pointEffet + " cartes";
    }

}
