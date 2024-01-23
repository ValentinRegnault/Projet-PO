package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute des points d'énergie au héros.
 * 
 * @see Effet
 */
public class Energie extends Effet {

    public Energie() {
        super(0, TypeCible.AUCUN);
    }

    public Energie(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef,
            Heros herosRef, List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            if (cible instanceof Heros hero) {
                hero.setPointEnergie(hero.getPointEnergie() + pointEffet);
            } else {
                throw new IllegalArgumentException("La cible de l'effet Energie doit être un Hero");
            }
        }
    }

    @Override
    public String toString() {
        return "Le lanceur gagne " + this.pointEffet + " points d'énergie.";
    }

}
