package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui ajoute des points de vulnérabilité à la cible.
 * 
 * @see Effet
 */
public class Vulnerable extends Effet {

    public Vulnerable() {
        super(0, TypeCible.AUCUN);
    }

    public Vulnerable(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef, Heros herosRef,
            List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            cible.setStatusPoint(Entite.Status.VULNERABLE,
                    cible.getStatusPoint(Entite.Status.VULNERABLE) + this.pointEffet);
        }
    }

    @Override
    public String toString() {
        return "Ajoute " + this.pointEffet + " points de vulnérabilité à la cible.";
    }

}
