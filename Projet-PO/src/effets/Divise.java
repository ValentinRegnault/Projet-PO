package effets;

import java.util.ArrayList;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui divise les PV du héros par 12.
 * 
 * @see Effet
 */
public class Divise extends Effet {
    public Divise() {
        super(0, TypeCible.AUCUN);
    }

    public Divise(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles, Deck deckRef,
            Heros herosRef, ArrayList<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            for (int i = 0; i < this.pointEffet; i++) {
                int degat = (cible.getPv() + 1) / 12;
                cible.setPv(cible.getPv() - degat);
            }
        }
    }

    @Override
    public String toString() {
        return "Inflige H dégâts 6 fois avec H = 1 + (PV du héros) / 12";
    }
}
