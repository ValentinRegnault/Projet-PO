package main;

import java.io.File;
import ressources.Affichage;
import ressources.Config;

/**
 * Classe abstraite représentant une salle du jeu. Une salle contient le héro, qui est déplacé de
 * salle en salle par Partie.
 */
public abstract class Salle extends Affichable {
    protected Deck deckRef;
    protected Heros herosRef;

    private Affichable background =
            new Sprite("assets" + File.separator + "pictures" + File.separator + "background.jpg",
                    0.0, 0.0, Config.X_MAX, Config.Y_MAX);

    protected TexteExplicatif texteExplicatif = new TexteExplicatif("");

    public class TexteExplicatif {
        private String texteExplicatif = "";

        public TexteExplicatif(String texteExplicatif) {
            this.texteExplicatif = texteExplicatif;
        }

        public String getTexteExplicatif() {
            return texteExplicatif;
        }

        public void setTexteExplicatif(String texteExplicatif) {
            this.texteExplicatif = texteExplicatif;
        }
    }

    protected Salle(double x, double y, Deck deckRef, Heros herosRef) {
        super(x, y);
        this.deckRef = deckRef;
        this.herosRef = herosRef;
    }

    public abstract boolean jouerSalle();

    public TexteExplicatif getTexteExplicatif() {
        return texteExplicatif;
    }

    /**
     * Change le texte explicatif de la salle. Attention, cette methode ne change pas l'instance de
     * TexteExplicatif mais bien le texte qu'elle contient.
     * 
     * @param texteExplicatif
     */
    public void setTexteExplicatif(String texteExplicatif) {
        this.texteExplicatif.setTexteExplicatif(texteExplicatif);
        afficher();
    }

    @Override
    public void afficher() {
        background.afficher();
        Affichage.texteCentre(Config.X_MAX / 2.0, Config.Y_MAX - 20.0, texteExplicatif.getTexteExplicatif());
    }
}
