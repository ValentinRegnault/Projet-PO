package effets;

import java.io.IOException;
import java.util.List;
import main.Carte;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Partie;
import main.Salle.TexteExplicatif;

public class CartePlaieDansDefausse extends Effet {
    public CartePlaieDansDefausse() {
        super(0, TypeCible.AUCUN);
    }

    public CartePlaieDansDefausse(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef, Heros herosRef,
            List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        try {
            for (int i = 0; i < this.pointEffet; i++) {
                Carte brulure = null;
                brulure = Partie.instancierCarte("Plaie");
                deckRef.ajouterCarteDansDefausse(brulure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Ajoute Plaie " + this.pointEffet;
    }
}
