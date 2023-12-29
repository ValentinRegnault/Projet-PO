import java.io.IOException;
import java.util.ArrayList;

class CarteSlimeDansDefausse extends Effet {
	public CarteSlimeDansDefausse() {
		super(0, TypeCible.AUCUN);
	}

	public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
		try {
			for (int i = 0; i < this.pointEffet; i++) {
				Carte slime = null;
				slime = Partie.instancierCarte("Slime");
				Partie.partie.getDefausse().add(slime);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "Ajoute un Slime dans la défausse";
	}
}