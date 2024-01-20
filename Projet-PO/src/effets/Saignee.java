package effets;

import java.util.ArrayList;
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
public class Saignee extends Effet {
    public Saignee() {
        super(0, TypeCible.AUCUN);
    }

    public Saignee(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles, Deck deckRef,
            Heros herosRef, ArrayList<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        herosRef.setPv(herosRef.getPv() - 3);
        herosRef.setPointEnergie(herosRef.getPointEnergie() + 2);
    }

    @Override
    public String toString() {
        return "Fait perdre " + this.pointEffet + " points de vie à la cible.";
    }

}
