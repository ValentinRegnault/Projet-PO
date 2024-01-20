package effets;

import java.util.ArrayList;
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
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles, Deck deckRef,
            Heros herosRef, ArrayList<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.Faiblesse,
                    cible.getStatusPoint(Entite.Status.Faiblesse) + this.pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Faiblesse " + this.pointEffet;
    }
}
