package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import effets.Effet;
import effets.TypeCible;
import librairies.StdDraw;
import main.Carte.EtatCarte;
import ressources.Affichage;
import ressources.AssociationTouches;
import ressources.Config;

/**
 * Classe representant une salle de combat, c'est à dire une salle contenant des monstres ou un
 * Boss. Cette classe contient la logique de combat du jeu.
 */
public class SalleCombat extends Salle {
	private static final String INDICES_MONSTRES = "AZERTYUIOP";
	private static final String INDICES_CARTES = "QSDFGHJKLM";
	private boolean afficherSelecteurMonstre = false;
	private boolean afficherSelecteurCarte = false;
	private ArrayList<Character> caractereSelecteurMonstre = new ArrayList<>();
	private ArrayList<Character> caractereSelecteurCarte = new ArrayList<>();

	protected ArrayList<Monstre> equipeMonstre;

	protected SalleCombat(ArrayList<Monstre> equipeMonstre, Deck deck, Heros heros) {
		super(0, 0, deck, heros);
		this.equipeMonstre = equipeMonstre;
	}

	/**
	 * Détermine si le combat est terminé. Le combat est terminé si le hero est mort ou si tous les
	 * monstres sont morts.
	 * 
	 * @return true si le combat est terminé, false sinon.
	 */
	public boolean combatTermine(Heros heros) {
		return heros.getPv() == 0
				|| equipeMonstre.stream().allMatch(monstre -> monstre.getPv() <= 0);
	}

	/**
	 * Lance le combat, et donne sa récompense au hero si il gagne.
	 * 
	 * @see {@link #jouerCombat()}
	 * @see {@link #choisirRecompense()}
	 */
	@Override
	public boolean jouerSalle() {
		try {
			jouerCombat();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (herosRef.getPv() <= 0) {
			setTexteExplicatif("Vous avez perdu !");
			return false;
		} else {
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
	public synchronized void jouerCombat() throws InterruptedException {
		setTexteExplicatif("Vous pénétrez dans une salle de combat !");

		while (!combatTermine(herosRef)) {

			equipeMonstre.stream().forEach(monstre -> monstre.actionSuivante());
			// --- Tour du hero

			// Régénération de l'énergie du hero
			herosRef.setPointEnergie(herosRef.getPointEnergieMax());
			// Disparition des points de blocages du hero
			herosRef.setPointBlocage(0);

			// Pioche de 5 cartes
			for (int i = 0; i < 5; i++) {
				deckRef.piocherCarte();
			}

			setTexteExplicatif(
					"C'est à votre tour de jouer. Vos point d'énergie ont été rechargé et vos points de blocage ont été réinitialisé. Vous piochez 5 cartes. ENTER pour continuer.");

			String touche = null;
			while (touche == null || !touche.equals("Entree"))
				touche = AssociationTouches.trouveProchaineEntree();

			Optional<Carte> carte;
			do {
				carte = demanderCarte();
				if (carte.isEmpty())
					break;

				appliquerEffets(carte.get().getEffets(), herosRef);
				herosRef.setPointEnergie(herosRef.getPointEnergie() - carte.get().getCout());
				carte.get()
						.setEtat(carte.get().isaExiler() ? EtatCarte.EXILEE : EtatCarte.DEFAUSSEE);
				retirerMonstresMorts();
			} while (!combatTermine(herosRef) && carte.isPresent());

			long nbCarteBrulureDansMain =
					deckRef.getMain().stream().filter(c -> c.getNom().equals("Brulure")).count();
			if (nbCarteBrulureDansMain > 0) {
				setTexteExplicatif("Vous avez " + nbCarteBrulureDansMain
						+ " cartes Brûlure dans votre main. Vous perdez donc "
						+ 2 * nbCarteBrulureDansMain + " PV. ENTER pour continuer.");
				herosRef.setPv(herosRef.getPv() - 2 * (int) nbCarteBrulureDansMain);
				while (!AssociationTouches.trouveProchaineEntree().equals("Entree"));

			}

			deckRef.defausserTouteLesCartes();


			// --- Tour des monstres

			for (Monstre monstre : equipeMonstre) {
				monstre.setPointBlocage(0);
				setTexteExplicatif(monstre.nom + " fait l'action "
						+ monstre.actionActuelle().getNom() + ". ENTER pour continuer.");
				while (!AssociationTouches.trouveProchaineEntree().equals("Entree"));
				appliquerEffets(monstre.actionActuelle().getEffets(), monstre);

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
	 * Pour chaque effet en paramètres, calcul les cibles dont il a besoin et appel sa méthode
	 * appliqueEffet. Si un effet de type SELECTION_JOUEUR est présent, demande à l'utilisateur de
	 * choisir une cible, et la passe en paramètre de tous les effets qui ont le type de cible
	 * SELECTION_JOUEUR.
	 * 
	 * @see {@link Effet#appliquerEffet()}
	 * @param effets
	 */
	private void appliquerEffets(ArrayList<Effet> effets, Entite lanceur) {
		boolean doitOnSelectionnerUneCible =
				effets.stream().anyMatch(e -> e.getTypeCible() == TypeCible.SELECTION_JOUEUR);
		Monstre cibleSelectionee = null;
		if (doitOnSelectionnerUneCible) {
			cibleSelectionee = demanderMonstre(equipeMonstre);
		}

		for (Effet effet : effets) {
			ArrayList<Entite> cibles = new ArrayList<>();
			switch (effet.getTypeCible()) {
				case AUCUN:
					break;
				case HERO, LANCEUR:
					cibles.add(herosRef);
					break;
				case TOUS_LES_MONSTRES:
					cibles.addAll(equipeMonstre);
					break;
				case MONSTRE_ALEATOIRE:
					cibles.add(equipeMonstre.get((int) (Math.random() * equipeMonstre.size())));
					break;
				case SELECTION_JOUEUR:
					cibles.add(cibleSelectionee);
					break;
				default:
					break;
			}

			effet.appliquerEffet(lanceur, cibles, deckRef, herosRef, equipeMonstre,
					texteExplicatif);
		}
	}


	@Override
	public void afficher() {
		StdDraw.clear();
		super.afficher();

		herosRef.afficher();
		deckRef.afficher();

		for (Monstre monstre : equipeMonstre) {
			monstre.afficher();
		}

		afficherUI(deckRef, equipeMonstre, herosRef);
		StdDraw.show();
	}

	public void afficherUI(Deck deck, List<Monstre> equipeMonstreActuelle, Heros heros) {
		// Affichage pioche
		double padding = 10.0;
		Affichage.texteGauche(padding, padding, "Pioche : " + deck.getPioche().size());

		// Affichage défausse
		Affichage.texteDroite(Config.X_MAX - padding, padding,
				"Défausse : " + deck.getDefausse().size());

		// Affichage exilées
		Affichage.texteDroite(Config.X_MAX - padding, padding + 20,
				"Exilées : " + deck.getExilees().size());

		// Affichage énergie
		Affichage.texteGauche(padding, padding + 20, "Energie : " + heros.getPointEnergie());

		if (afficherSelecteurCarte) {
			List<Carte> main = deck.getMain();
			associerCaracteresCarte(main.size());
			System.out.println(caractereSelecteurCarte);

			for (int i = 0; i < main.size(); i++) {
				Carte carte = main.get(i);

				Affichage.selecteur(String.valueOf(caractereSelecteurCarte.get(i)),
						carte.getX() + Carte.LARGEUR_CARTE / 2,
						carte.getY() + Carte.HAUTEUR_CARTE + 20, 20, 20);
			}
		}

		if (afficherSelecteurMonstre) {
			associerCaracteresMonstre(equipeMonstreActuelle.size());

			for (int i = 0; i < equipeMonstreActuelle.size(); i++) {
				Monstre monstre = equipeMonstreActuelle.get(i);
				Affichage.selecteur(String.valueOf(caractereSelecteurMonstre.get(i)),
						monstre.getX() + monstre.getWidth() / 2,
						monstre.getY() + monstre.getHeight() + 20, 20, 20);
			}
		}
	}

	/**
	 * Demande à l'utilisateur de choisir un monstre parmi l'équipe de monstre actuellement dans la
	 * salle. Cette méthode est synchrone (elle bloque l'execution tant que l'utilisateur n'a pas
	 * choisi de monstre).
	 * 
	 * @return le monstre choisi par l'utilisateur.
	 */
	public Monstre demanderMonstre(List<Monstre> equipeMonstreActuelle) {
		associerCaracteresMonstre(equipeMonstreActuelle.size());
		afficherSelecteurMonstre(true, equipeMonstreActuelle.size());
		setTexteExplicatif(
				"Choisissez un monstre à attaquer en appuyant sur une touche associé à un monstre.");

		Monstre monstreSelectionne = null;
		while (monstreSelectionne == null) {
			String touche = AssociationTouches.trouveProchaineEntree();

			// Plutot que d'imbriquer des if les uns dans les autres, ou faire une condition
			// d'arrêt incompréhensible, nous préfèrerons arrêter le tour de boucle en cours
			// et sauter au suivant dès qu'une condition est invalide.
			if (touche == null || touche.length() == 0)
				continue;
			if (!caractereSelecteurMonstre.contains(touche.charAt(0)))
				continue;
			if (equipeMonstreActuelle.size() <= caractereSelecteurMonstre.indexOf(touche.charAt(0)))
				continue;

			monstreSelectionne =
					equipeMonstreActuelle.get(caractereSelecteurMonstre.indexOf(touche.charAt(0)));
		}

		afficherSelecteurMonstre(false, equipeMonstreActuelle.size());
		return monstreSelectionne;
	}


	/**
	 * Demande à l'utilisateur de choisir une carte de sa main. Cette méthode est synchrone (elle
	 * bloque l'execution tant que l'utilisateur n'a pas choisi de monstre).
	 * 
	 * @return la carte choisi par l'utilisateur.
	 */
	public Optional<Carte> demanderCarte() {
		associerCaracteresCarte(deckRef.getMain().size());
		afficherSelecteurCarte(true, deckRef.getMain().size());
		setTexteExplicatif(
				"Choisissez une carte à jouer en appuyant sur une touche associé à une carte.");

		System.out.println("Demande d'une carte");

		Optional<Carte> carte = Optional.empty();
		boolean carteChoisie = false;
		while (!carteChoisie) {

			String touche = AssociationTouches.trouveProchaineEntree();

			System.out.println("Touche : " + touche);

			// Plutot que d'imbriquer des if les uns dans les autres, je préfère
			// arrêter le tour de boucle en cours et sauter au suivant dès qu'une condition
			// est invalide.
			if (touche == null || touche.length() == 0) {
				setTexteExplicatif("Touche invalide");
				continue;
			}
			if (touche.equals("ESCAPE"))
				carteChoisie = true;
			if (!caractereSelecteurCarte.contains(touche.charAt(0))) {
				setTexteExplicatif(
						"Touche invalide, veuillez choisir une touche associé à une carte de votre main. Les touches possibles s'affichent au dessus des cartes. Echap pour mettre fin à votre tour.");
				continue;
			}
			if (deckRef.getMain().size() <= caractereSelecteurCarte.indexOf(touche.charAt(0))) {
				setTexteExplicatif(
						"Touche invalide, veuillez choisir une touche associé à une carte de votre main. Les touches possibles s'affichent au dessus des cartes. Echap pour mettre fin à votre tour.");
				continue;
			}
			if (deckRef.getMain().get(caractereSelecteurCarte.indexOf(touche.charAt(0)))
					.getCout() > herosRef.getPointEnergie()) {
				setTexteExplicatif(
						"Vous ne pouvez pas jouer cette carte, vous n'avez pas assez d'énergie. Echap pour mettre fin à votre tour.");
				continue;
			}

			carte = Optional
					.of(deckRef.getMain().get(caractereSelecteurCarte.indexOf(touche.charAt(0))));
			carteChoisie = true;
		}

		afficherSelecteurCarte(false, deckRef.getMain().size());
		return carte;
	}


	/**
	 * Affiche les selecteurs des monstres, met à jour le texte explicatif et raffraichit
	 * l'affichage du monde.
	 * 
	 * @param afficherSelecteurMonsre Si vrai, affiche les selecteurs des monstres.
	 */
	public void afficherSelecteurMonstre(boolean afficherSelecteurMonsre, int nbMonstres) {
		caractereSelecteurMonstre = new ArrayList<>(nbMonstres);
		for (int i = 0; i < caractereSelecteurMonstre.size(); i++) {
			caractereSelecteurMonstre.set(i, INDICES_MONSTRES.charAt(i));
		}
		this.afficherSelecteurMonstre = afficherSelecteurMonsre;
		afficher();
	}

	/**
	 * Affiche les selecteurs des cartes, met à jour le texte explicatif et raffraichit l'affichage
	 * du monde.
	 * 
	 * @param afficherSelecteurMonstre Si vrai, affiche les selecteurs des cartes.
	 * @return Les caractères associés aux cartes qui permettent de les sélectionner.
	 */
	public void afficherSelecteurCarte(boolean afficherSelecteurCarte, int nbCartes) {
		caractereSelecteurCarte = new ArrayList<>(nbCartes);
		for (int i = 0; i < caractereSelecteurCarte.size(); i++) {
			caractereSelecteurCarte.set(i, INDICES_CARTES.charAt(i));
		}
		this.afficherSelecteurCarte = afficherSelecteurCarte;
		afficher();
	}


	/**
	 * Créer un tableau avec les caractères qui permettent de sélectionner les monstres, avec un
	 * caractère par indice qui correspond à un monstre.
	 * 
	 * @param nbMonstres le nombre de monstres dans la salle.
	 */
	private void associerCaracteresMonstre(int nbMonstres) {
		caractereSelecteurMonstre = new ArrayList<>(nbMonstres);
		for (int i = 0; i < nbMonstres; i++) {
			caractereSelecteurMonstre.add(INDICES_MONSTRES.charAt(i));
		}
	}

	/**
	 * Créer un tableau avec les caractères qui permettent de sélectionner les cartes, avec un
	 * caractère par indice qui correspond à une carte.
	 * 
	 * @param nbCartes
	 */
	private void associerCaracteresCarte(int nbCartes) {
		caractereSelecteurCarte = new ArrayList<>(nbCartes);
		for (int i = 0; i < nbCartes; i++) {
			caractereSelecteurCarte.add(INDICES_CARTES.charAt(i));
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
			setTexteExplicatif("Vous avez choisi la carte " + cartes[indiceCarte].getNom());
			deckRef.ajouterCarteDansDefausse(cartes[indiceCarte]);
			cartes[indiceCarte].setEtat(EtatCarte.DANS_PIOCHE);
		}
	}

	/**
	 * Retire les monstres morts de l'équipe de monstre.
	 */
	private void retirerMonstresMorts() {
		equipeMonstre.removeIf(monstre -> monstre.getPv() <= 0);
	}
}
