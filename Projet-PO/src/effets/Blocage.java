package effets;

import java.util.List;
import main.Deck;
import main.Entite;
import main.Heros;
import main.Monstre;
import main.Salle.TexteExplicatif;

/**
 * Effet qui donne des points de blocage au propriétaire de la carte.
 * 
 * @see Effet
 */
public class Blocage extends Effet {

    public Blocage(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public Blocage() {
        super(0, TypeCible.AUCUN);
    }

    @Override
    public void appliquerEffet(Entite lanceur, List<Entite> cibles, Deck deckRef, Heros herosRef,
            List<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            // Fragile : 75% des points de blocage
            int blocage = this.pointEffet;
            if (cible.getStatusPoint(Entite.Status.FRAGILE) > 0) {
                blocage = (int) Math.floor(0.75 * pointEffet);
            }

            cible.setPointBlocage(cible.getPointBlocage() + blocage);
            System.out.println(lanceur.getNom() + " gagne " + pointEffet + " points de blocage");
        }
    }

    @Override
    public String toString() {
        return "Donne " + this.pointEffet + " points de blocage au propriétaire de la carte.";
    }

}
