package main;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.text.html.Option;

public abstract class SalleCombat extends Salle {
	protected ArrayList<Monstre> equipeMonstre;

	public SalleCombat(ArrayList<Monstre> equipeMonstre) {
		this.equipeMonstre = equipeMonstre;
	}

	public ArrayList<Monstre> getEquipeMonstre() {
		return equipeMonstre;
	}

	public boolean combatTermine() {
		return hero.getPv() == 0 || equipeMonstre.stream().allMatch(monstre -> monstre.getPv() <= 0);
	}

	public boolean jouerSalle() {
		lancerCombat();
		Hero hero = Partie.getPartie().getHero();
		System.out.println(hero.getPv() <= 0);

		if (hero.getPv() <= 0) {
			System.out.println("Vous avez perdu !");
			return false;
		} else {
			System.out.println("Félicitations, vous avez vaincu les monstres de cette salle !");
			choisirRecompense();
			return true;
		}
	}

	public void lancerCombat() {
		Hero hero = Partie.getPartie().getHero();

		System.out.println("Vous entrez dans une salle de combat...");
		if (equipeMonstre.size() == 1) {
			System.out.println("Devant vous se dresse un " + equipeMonstre.get(0).getNom() + " avec "
					+ equipeMonstre.get(0).getPv() + " PV !");
		} else {
			System.out.println("Devant vous se dresse une équipe de monstres :");
			for (Monstre monstre : equipeMonstre) {
				System.out.println("- un " + monstre.getNom() + " avec " + monstre.getPv() + " PV !");
			}
		}

		System.out.println("Vous avez " + hero.getPv() + " PV et " + hero.getPointEnergieMax()
				+ " points d'énergie. Dans votre main, vous avez :");

		Partie.getPartie().getMain().stream().forEach(carte -> System.out.println(
				carte.getNom() + " - " + carte.getDescription() + " - " + carte.getCout() + " points d'énergie"));

		while (!combatTermine()) {
			System.out.println();
			System.out.println("---- Début du tour ----");

			// Régénération de l'énergie du hero
			hero.setPointEnergie(hero.getPointEnergieMax());
			// Disparition des points de blocages du hero
			hero.setPointBlocage(0);

			for (int i = 0; i < 5; i++) {
				Partie.getPartie().piocheCarte();
			}

			// Affichage de l'intention des monstres=
			equipeMonstre.stream().forEach(monstre -> System.out.println(monstre.getNom()
					+ " à l'intention de faire l'action " + monstre.getPattern().intention().getNom()));

			boolean tourTermine = false;
			while (!tourTermine) {
				if (combatTermine()) {
					System.out.println("Pas le tempsd de finir le tour que vous avez déjà gagner !");
					break;
				}

				System.out.println();
				System.out.println("Votre main contient " + Partie.getPartie().getMain().size() + " cartes :");
				for (int i = 0; i < Partie.getPartie().getMain().size(); i++) {
					Carte carte = Partie.getPartie().getMain().get(i);
					System.out.println("- [" + i + "] " + carte.getNom() + "\n    Description : "
							+ carte.getDescription() + "\n    Coût : " + carte.getCout() + " points d'énergie");
				}

				int indiceCarte = demanderCarte();
				if (indiceCarte == -1) {
					tourTermine = true;
				} else {
					Carte carte = Partie.getPartie().getMain().get(indiceCarte);
					System.out.println("Vous avez choisi la carte " + carte.getNom());
					carte.jouerCarte();
					retirerMonstresMorts();
				}
			}

			if (combatTermine()) {
				System.out.println("Le combat est terminé !");
				break;
			}

			long nbCarteBrulureDansMain = Partie.getPartie().getMain().stream()
					.filter(carte -> carte.getNom().equals("Brûlure"))
					.count();
			if (nbCarteBrulureDansMain > 0) {
				System.out.println("Vous avez " + nbCarteBrulureDansMain
						+ " cartes Brûlure dans votre main. Vous allez perdre deux points de vie par carte brûlure !");
				hero.setPv(hero.getPv() - 2 * (int) nbCarteBrulureDansMain);
			}

			/// TOUR DES MONSTRES ///
			for (Monstre monstre : equipeMonstre) {
				// Remise a zéro des points de blocage du monstre
				monstre.setPointBlocage(0);
				monstre.jouerAction();
			}

			// Status rituel
			for (Monstre monstre : equipeMonstre) {
				int pointRituel = monstre.getStatusPoint(Entite.Status.Rituel);
				if (pointRituel > 0) {
					monstre.setStatusPoint(Entite.Status.Force, pointRituel);
					monstre.setStatusPoint(Entite.Status.Rituel, pointRituel - 1);
				}
			}

			System.out.println();
			System.out.println("Bilan du tour :");
			System.out.println();

			System.out.println("Vous avez " + hero.getPv() + " PV et " + hero.getPointEnergie() + " points d'énergie.");

			if (hero.status.values().stream().anyMatch(val -> val > 0)) {
				System.out.println("Vous êtes affecté par les status suivants :");
				hero.afficheStatus();
			}

			// Affichage des PV des monstres et leurs status
			for (Monstre monstre : equipeMonstre) {
				if (monstre.status.values().stream().anyMatch(val -> val > 0)) {
					System.out.println(monstre.getNom() + " a " + monstre.getPv() + "/" + monstre.getPvMax()
							+ " PV et est affecté par les status suivants :");

					monstre.afficheStatus();
				}
			}
		}
	}

	private int demanderCarte() {
		int indiceCarte = 0;
		boolean indiceValide = false;
		boolean energieSuffisante = false;
		while (!indiceValide || !energieSuffisante) {
			System.out.println("Vous avez " + Partie.getPartie().getHero().getPointEnergie() + " points d'énergie");
			System.out.println("Choisissez une carte ou entrez -1 pour terminer votre tour");
			indiceCarte = Partie.getPartie().getScanner().nextInt();

			if (indiceCarte == -1) {
				indiceValide = true;
				energieSuffisante = true;
				continue;
			}
			if (indiceCarte < 0 || indiceCarte >= Partie.getPartie().getMain().size()) {
				System.out.println("Indice invalide");
				continue;
			}
			indiceValide = true;
			Carte carte = Partie.getPartie().getMain().get(indiceCarte);
			if (carte.getCout() <= Partie.getPartie().getHero().getPointEnergie()) {
				energieSuffisante = true;
			} else {
				System.out.println("Vous n'avez pas assez d'énergie pour jouer cette carte");
			}
		}

		return indiceCarte;
	}

	private void choisirRecompense() {
		System.out.println("Choisissez une carte de récompense parmis les trois suivante, ou aucune :");
		Carte[] cartes = new Carte[3];
		for (int i = 0; i < 3; i++) {
			cartes[i] = Partie.getPartie().carteAleatoire();
		}

		for (int i = 0; i < 3; i++) {
			Carte carte = cartes[i];
			System.out.println("[" + i + "] " + carte.getNom() + " - " + carte.getDescription() + " - "
					+ carte.getCout() + " points d'énergie");
		}

		System.out.println("[-1] Aucune");
		System.out.println("Votre choix : ");
		int indiceCarte = Partie.getPartie().getScanner().nextInt();
		if (indiceCarte == -1) {
			System.out.println("Vous avez rejeter les cartes de récompense.");
		} else {
			System.out.println("Vous avez choisi la carte " + cartes[indiceCarte].getNom());
			Partie.getPartie().getDeck().add(cartes[indiceCarte]);
		}
	}

	private void retirerMonstresMorts() {
		equipeMonstre.removeIf(monstre -> monstre.getPv() <= 0);
	}

	@Override
	public String toString() {
		return "SalleCombat [equipeMonstre=" + equipeMonstre + "]";
	}
}