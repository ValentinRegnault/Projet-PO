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
 * Effet qui ajoute une carte Slime dans la défausse.
 * 
 * @see Effet
 */
class CarteSlimeDansDefausse extends Effet {
	public CarteSlimeDansDefausse() {
		super(0, TypeCible.AUCUN);
	}

	@Override
	public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles, Deck deckRef,
			Heros herosRef, ArrayList<Monstre> equipeMonstres, TexteExplicatif texteExplicatif) {
		try {
			for (int i = 0; i < this.pointEffet; i++) {
				Carte slime = null;
				slime = Partie.instancierCarte("Slime");
				deckRef.ajouterCarteDansDefausse(slime);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "Ajoute un Slime dans la défausse";
	}
}
