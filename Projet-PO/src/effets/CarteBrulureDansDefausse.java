package effets;

import java.io.IOException;
import java.util.ArrayList;

import main.Carte;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Partie;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute une carte Brûlure dans la défausse.
 * 
 * @see Effet
 */
public class CarteBrulureDansDefausse extends Effet {
    public CarteBrulureDansDefausse() {
        super(0, TypeCible.AUCUN);
    }

    public CarteBrulureDansDefausse(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles, Deck deckRef,
            Heros herosRef, ArrayList<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        try {
            for (int i = 0; i < this.pointEffet; i++) {
                Carte brulure = null;
                brulure = Partie.instancierCarte("Brulure");
                deckRef.ajouterCarteDansDefausse(brulure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Ajoute une Brûlure dans la défausse";
    }

}
