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
		Hero hero = Partie.partie.getHero();
		System.out.println(hero.getPv() <= 0);
		return hero.getPv() > 0;
	}

	public void lancerCombat() {
		Hero hero = Partie.partie.getHero();

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

		Partie.partie.getMain().stream().forEach(carte -> System.out.println(
				carte.getNom() + " - " + carte.getDescription() + " - " + carte.getCout() + " points d'énergie"));

		while (!combatTermine()) {
			System.out.println();
			System.out.println("---- Début du tour ----");

			// Régénération de l'énergie du hero
			hero.setPointEnergie(hero.getPointEnergieMax());
			// Disparition des points de blocages du hero
			hero.setPointBlocage(0);

			for (int i = 0; i < 5; i++) {
				Partie.partie.piocheCarte();
			}

			// Affichage de l'intention des monstres=
			equipeMonstre.stream().forEach(monstre -> System.out.println(monstre.getNom()
					+ " à l'intention de faire l'action " + monstre.getPattern().intention().getNom()));

			boolean tourTermine = false;
			while (!tourTermine) {
				if(combatTermine()) {
					System.out.println("Pas le tempsd de finir le tour que vous avez déjà gagner !");
					break;
				}

				System.out.println();
				System.out.println("Votre main contient " + Partie.partie.getMain().size() + " cartes :");
				for (int i = 0; i < Partie.partie.getMain().size(); i++) {
					Carte carte = Partie.partie.getMain().get(i);
					System.out.println("- [" + i + "] " + carte.getNom() + "\n    Description : "
							+ carte.getDescription() + "\n    Coût : " + carte.getCout() + " points d'énergie");
				}

				int indiceCarte = demanderCarte();
				if (indiceCarte == -1) {
					tourTermine = true;
				} else {
					Carte carte = Partie.partie.getMain().get(indiceCarte);
					System.out.println("Vous avez choisi la carte " + carte.getNom());
					carte.jouerCarte();
					retirerMonstresMorts();
				}
			}

			if(combatTermine()) {
				System.out.println("Le combat est terminé !");
				break;
			}

			/// TOUR DES MONSTRES ///
			for (Monstre monstre : equipeMonstre) {
				// Remise a zéro des points de blocage du monstre
				monstre.setPointBlocage(0);
				monstre.jouerAction();
			}

			// TODO update les status

			System.out.println();
			System.out.println("Bilan du tour :");
			System.out.println();

			System.out.println("Vous avez " + hero.getPv() + " PV et " + hero.getPointEnergie() + " points d'énergie.");
			System.out.println("Vous êtes affecté par les status suivants :");
			hero.afficheStatus();

			// Affichage des PV des monstres et leurs status
			for (Monstre monstre : equipeMonstre) {
				System.out.println(monstre.getNom() + " a " + monstre.getPv() + "/" + monstre.getPvMax()
						+ " PV et est affecté par les status suivants :");
				monstre.afficheStatus();
			}
		}
	}

	private int demanderCarte() {
		int indiceCarte = 0;
		boolean indiceValide = false;
		boolean energieSuffisante = false;
		while (!indiceValide || !energieSuffisante) {
			System.out.println("Vous avez " + Partie.partie.getHero().getPointEnergie() + " points d'énergie");
			System.out.println("Choisissez une carte ou entrez -1 pour terminer votre tour");
			indiceCarte = Partie.partie.getScanner().nextInt();

			if (indiceCarte == -1) {
				indiceValide = true;
				energieSuffisante = true;
				continue;
			}
			if (indiceCarte < 0 || indiceCarte >= Partie.partie.getMain().size()) {
				System.out.println("Indice invalide");
				continue;
			}
			indiceValide = true;
			Carte carte = Partie.partie.getMain().get(indiceCarte);
			if (carte.getCout() <= Partie.partie.getHero().getPointEnergie()) {
				energieSuffisante = true;
			} else {
				System.out.println("Vous n'avez pas assez d'énergie pour jouer cette carte");
			}
		}

		return indiceCarte;
	}

	private void retirerMonstresMorts() {
		equipeMonstre.removeIf(monstre -> monstre.getPv() <= 0);
	}

	@Override
	public String toString() {
		return "SalleCombat [equipeMonstre=" + equipeMonstre + "]";
	}
}
