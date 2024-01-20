package effets;

import java.util.ArrayList;
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
public class Force extends Effet {

    public Force() {
        super(0, TypeCible.AUCUN);
    }

    public Force(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles, Deck deckRef,
            Heros herosRef, ArrayList<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage
            cible.setStatusPoint(Entite.Status.Force,
                    cible.getStatusPoint(Entite.Status.Force) + this.pointEffet);
            System.out.println(lanceur.getNom() + " donne " + this.pointEffet
                    + " points de force à " + cible.getNom());
        }
    }

    @Override
    public String toString() {
        return "Force " + this.pointEffet;
    }

}
