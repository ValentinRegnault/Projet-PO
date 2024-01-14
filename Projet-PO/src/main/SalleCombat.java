package main;

import java.util.ArrayList;
import java.util.Optional;
import main.Carte.EtatCarte;
import ressources.Affichage;
import ressources.AssociationTouches;

/**
 * Classe representant une salle de combat, c'est à dire une salle contenant des monstres ou un
 * Boss. Cette classe contient la logique de combat du jeu.
 */
public abstract class SalleCombat extends Salle {
	protected ArrayList<Monstre> equipeMonstre;

	protected SalleCombat(ArrayList<Monstre> equipeMonstre) {
		this.equipeMonstre = equipeMonstre;
	}

	/**
	 * Détermine si le combat est terminé. Le combat est terminé si le hero est mort ou si tous les
	 * monstres sont morts.
	 * 
	 * @return true si le combat est terminé, false sinon.
	 */
	public boolean combatTermine() {
		return hero.getPv() == 0
				|| equipeMonstre.stream().allMatch(monstre -> monstre.getPv() <= 0);
	}

	/**
	 * Lance le combat, et donne sa récompense au hero si il gagne.
	 * 
	 * @see {@link #lancerCombat()}
	 * @see {@link #choisirRecompense()}
	 */
	@Override
	public boolean jouerSalle() {
		try {
			lancerCombat();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Heros hero = Partie.getHeros();

		if (hero.getPv() <= 0) {
			System.out.println("Vous avez perdu !");
			return false;
		} else {
			System.out.println("Félicitations, vous avez vaincu les monstres de cette salle !");
			choisirRecompense();
			return true;
		}
	}

	/**
	 * Déroule le combat et prends fin quand il est terminé. Cette méthode contient la majorité de
	 * la logique de combat du jeu.
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void lancerCombat() throws InterruptedException {
		Heros hero = Partie.getHeros();

		// Affichage des monstres pour le premier tour
		Partie.setTexteExplicatif("Vous pénétrez dans une salle de combat !");

		while (!combatTermine()) {

			// --- Tour du hero

			// Régénération de l'énergie du hero
			hero.setPointEnergie(hero.getPointEnergieMax());
			// Disparition des points de blocages du hero
			hero.setPointBlocage(0);

			// Pioche de 5 cartes
			for (int i = 0; i < 5; i++) {
				Partie.piocherCarte();
			}

			Partie.setTexteExplicatif(
					"C'est à votre tour de jouer. Vos point d'énergie ont été rechargé et vos points de blocage ont été réinitialisé. Vous piochez 5 cartes. ENTER pour continuer.");

			String touche = null;
			while (touche == null || !touche.equals("Entree"))
				touche = AssociationTouches.trouveProchaineEntree();

			while (!combatTermine()) {
				Optional<Carte> carte = Partie.demanderCarte();
				if (carte.isEmpty())
					break;

				carte.get().jouerCarte();
				retirerMonstresMorts();
			}
			Partie.defausserTouteLesCartes();

			long nbCarteBrulureDansMain =
					Partie.getMain().stream().filter(c -> c.getNom().equals("Brûlure")).count();
			if (nbCarteBrulureDansMain > 0) {
				Partie.setTexteExplicatif("Vous avez " + nbCarteBrulureDansMain
						+ " cartes Brûlure dans votre main. Vous perdez donc "
						+ 2 * nbCarteBrulureDansMain
						+ " PV, et les cartes Brûlure sont défaussées.");
				hero.setPv(hero.getPv() - 2 * (int) nbCarteBrulureDansMain);
				Partie.defausseCarteBruluresDeLaMain();
				wait(1500);
			}

			// --- Tour des monstres

			for (Monstre monstre : equipeMonstre) {
				monstre.setPointBlocage(0);
				monstre.jouerAction();
			}

			// --- Fin du tour

			// Transformation des points de rituel des monstres en point de force
			for (Monstre monstre : equipeMonstre) {
				int pointRituel = monstre.getStatusPoint(Entite.Status.Rituel);
				if (pointRituel > 0) {
					monstre.setStatusPoint(Entite.Status.Force, pointRituel);
					monstre.setStatusPoint(Entite.Status.Rituel, pointRituel - 1);
				}
			}
		}
	}

	/**
	 * Demande au joueur de choisir une carte de récompense parmis trois cartes aléatoires.
	 */
	private void choisirRecompense() {
		Carte[] cartes = new Carte[3];
		for (int i = 0; i < 3; i++) {
			cartes[i] = Partie.carteAleatoire();
		}

		String[] cartesString = new String[3];
		for (int i = 0; i < 3; i++) {
			Carte carte = cartes[i];
			cartesString[i] = carte.toString();
		}

		int indiceCarte = Affichage.popup(
				"Félicitation, vous avez vaincus tous les monstres de cette salles. Choisissez une récompense parmi ces cartes :",
				cartesString, 0);

		if (indiceCarte == -1) {
			System.out.println("Vous avez rejeter les cartes de récompense.");
		} else {
			System.out.println("Vous avez choisi la carte " + cartes[indiceCarte].getNom());
			Partie.setTexteExplicatif("Vous avez choisi la carte " + cartes[indiceCarte].getNom());
			Partie.ajouterCarte(cartes[indiceCarte]);
			cartes[indiceCarte].setEtat(EtatCarte.DANS_PIOCHE);
		}
	}

	/**
	 * Retire les monstres morts de l'équipe de monstre.
	 */
	private void retirerMonstresMorts() {
		equipeMonstre.removeIf(monstre -> monstre.getPv() <= 0);
	}

	public ArrayList<Monstre> getEquipeMonstre() {
		return equipeMonstre;
	}

	@Override
	public String toString() {
		return "SalleCombat [equipeMonstre=" + equipeMonstre + "]";
	}
}
