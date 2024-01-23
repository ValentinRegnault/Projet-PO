package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.Carte.RareteCarte;
import ressources.AssociationTouches;
import ressources.Config;

/**
 * La classe la plus abstraite, qui gérent l'ensemble du jeu et coordonne les autres classes.
 */
public class Partie {
	private final ArrayList<Salle> salles = new ArrayList<>(15);

	/**
	 * Ensembles des fichiers de monstres du jeu, indexés par le nom du monstre.
	 */
	private static final TreeMap<String, File> monstresAssetParNom = new TreeMap<>();
	/**
	 * Ensembles des fichiers de cartes du jeu, indexés par le nom de la carte.
	 */
	private static final TreeMap<String, File> cartesAssetParNom = new TreeMap<>();

	private final Heros heros = new Heros("Bob");

	private final Deck deck = new Deck();

	/**
	 * Initialise la partie, en chargeant les fichiers de monstres et de cartes.
	 */
	public void initPartie() {

		AssociationTouches.init();
		Config.init();
		Affichable.initialiserAffichage();

		File dir = new File("assets" + File.separator + "monstres");

		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("Le répertoire n'existe pas ou ne peut pas être lu");
		} else {
			ObjectMapper mapper = new ObjectMapper();
			for (File file : dir.listFiles()) {
				if (!file.isFile())
					continue;

				try {
					Monstre m = mapper.readValue(file, Monstre.class);
					Partie.monstresAssetParNom.put(m.nom, file);
				} catch (IOException ioe) {
					System.out.println(
							"Erreur lors de la lecture du fichier de monstre " + file.getName());
					ioe.printStackTrace();
				}
			}

		}

		dir = new File("assets" + File.separator + "cartes");
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("Le répertoire n'existe pas ou ne peut pas être lu");
		} else {
			ObjectMapper mapper = new ObjectMapper();
			for (File file : dir.listFiles()) {
				if (!file.isFile())
					continue;

				try {
					Carte c = mapper.readValue(file, Carte.class);
					Partie.cartesAssetParNom.put(c.getNom(), file);
				} catch (IOException ioe) {
					System.out.println(
							"Erreur lors de la lecture du fichier de carte " + file.getName());
					ioe.printStackTrace();
				}
			}
		}
	}

	/**
	 * Initialise les salles et lance la partie, jusqu'à ce que le joueur gagne ou perde.
	 */
	public void jouerPartie() {
		genererSalles();
		for (Salle salle : salles) {
			deck.genererPioche();
			boolean victoire = salle.jouerSalle();
			if (!victoire) {
				return;
			}
		}
	}

	/**
	 * Génère les salles du jeu selon le schéma décrit dans le sujet.
	 */
	private void genererSalles() {
		// [C] -> [C] -> [R] -> [C] -> [C] -> [C] -> [R] -> [C] -> [C] -> [C] -> [R] ->
		// [C] -> [C] -> [R] -> [B]
		salles.clear();
		salles.add(new SalleCombat(genererEquipeMonstre(1), deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(2), deck, heros));
		salles.add(new SalleRepos(deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(3), deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(4), deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(5), deck, heros));
		salles.add(new SalleRepos(deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(6), deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(7), deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(8), deck, heros));
		salles.add(new SalleRepos(deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(9), deck, heros));
		salles.add(new SalleCombat(genererEquipeMonstre(10), deck, heros));
		salles.add(new SalleRepos(deck, heros));
		ArrayList<Monstre> boss = new ArrayList<>();
		try {
			Monstre hexaghost = instancierMonstre("Hexaghost");
			hexaghost.setX(Config.X_MAX - hexaghost.getWidth() - 50);
			hexaghost.setY(Config.Y_MAX / 2.0 - hexaghost.getHeight() / 2.0);
			boss.add(hexaghost);
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier de monstre Hexaghost");
			e.printStackTrace();
		}
		salles.add(new SalleCombat(boss, deck, heros));
	}

	/**
	 * Instancie un monstre depuis un fichier JSON, à partir du nom du monstre.
	 * 
	 * @implNote Cette méthode utile l'attribut {@link Partie#monstresAssetParNom} pour retrouver le
	 *           fichier JSON correspondant au monstre.
	 * @param name le nom du monstre à instancier, tel qu'il est écrit dans le fichier JSON du
	 *        monstre (sensible à la casse, aux espaces suplémentaires, etc.)
	 * @return Une instance de Monstre correspondant au monstre tel qu'il est décrit dans le fichier
	 *         JSON.
	 * @throws IOException Si le fichier JSON n'a pas pu être lu.
	 */
	public static Monstre instancierMonstre(String name) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(monstresAssetParNom.get(name), Monstre.class);
		} catch (IOException ioe) {
			System.out.println("Erreur lors de la lecture du fichier de monstre " + name);
			ioe.printStackTrace();
			throw ioe;
		}
	}

	/**
	 * Instancie une carte depuis un fichier JSON, à partir du nom de la carte.
	 * 
	 * @implNote Cette méthode utile l'attribut {@link Partie#cartesAssetParNom} pour retrouver le
	 *           fichier JSON correspondant à la carte.
	 * @param name le nom de la carte à instancier, tel qu'il est écrit dans le fichier JSON de la
	 *        carte (sensible à la casse, aux espaces suplémentaires, etc.)
	 * @return Une instance de Carte correspondant à la carte tel qu'elle est décrite dans le
	 *         fichier JSON.
	 * @throws IOException Si le fichier JSON n'a pas pu être lu.
	 */
	public static Carte instancierCarte(String name) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(cartesAssetParNom.get(name), Carte.class);
		} catch (IOException ioe) {
			System.out.println("Erreur lors de la lecture du fichier de carte " + name);
			ioe.printStackTrace();
			throw ioe;
		}
	}

	/**
	 * Génère une salle de monstres de difficulté donnée (entre 0 et 10). Dans une salle de
	 * difficulté n, il y a n/2 monstres. Ces monstres sont choisis aléatoirement de la manière
	 * suivante : 1) on tire n monstre au hasard parmi l'ensemble du catalogue de monstres 2) on
	 * choisi les n/2 monstres avec le plus de points de vie.
	 * 
	 * @param difficulte La difficulté de la salle entre 1 et 10
	 * @return Une liste de monstres
	 */
	private static ArrayList<Monstre> genererEquipeMonstre(int difficulte) {
		ArrayList<Monstre> monstresTires = new ArrayList<>();
		while (monstresTires.size() < difficulte) {
			try {
				int randomIndex = (int) (Math.random() * Partie.monstresAssetParNom.size());

				Monstre m = instancierMonstre(
						Partie.monstresAssetParNom.keySet().toArray()[randomIndex].toString());

				if (m.getNom().equals("Hexaghost")) // On ne veut pas de boss dans les salles
					continue;

				monstresTires.add(m);
			} catch (IOException ioe) {
				System.out.println("Erreur lors de la lecture du fichier de monstre.");
				ioe.printStackTrace();
			}
		}

		Collections.sort(monstresTires, (m1, m2) -> m2.getPvMax() - m1.getPvMax());

		ArrayList<Monstre> equipeMonstre =
				new ArrayList<>(monstresTires.subList(difficulte / 2, difficulte));



		double largeurCumuleeDesMonstres = 0;
		double deuxTiersDeLaLargeur = Config.X_MAX * 2.0 / 3.0;
		double hauteurLigne = 100.0;
		double premiereLigneY = (Config.Y_MAX / 3.0);

		for (Monstre m : equipeMonstre) {
			largeurCumuleeDesMonstres += m.getWidth() + Math.random() * 20;
			m.setX(Config.X_MAX - (largeurCumuleeDesMonstres % deuxTiersDeLaLargeur));
			int ligneActuelle = (int) (largeurCumuleeDesMonstres / deuxTiersDeLaLargeur);
			m.setY(premiereLigneY - ligneActuelle * hauteurLigne);
		}


		return equipeMonstre;
	}

	/**
	 * Instancie et retourne une carte aléatoire, en fonction de la rareté des cartes.
	 * 
	 * @apiNote Cette méthode est utilisée notamment pour générer les récompenses de combat
	 *          ({@link SalleCombat#choisirRecompense()}).
	 * @return Une instance de Carte, générée à partir du fichier JSON de la carte.
	 * @implNote Cette méthode utilise l'attribut {@link Partie#cartesAssetParNom} pour retrouver
	 *           les fichiers JSON des cartes et en choisir un aléatoirement.
	 */
	public static Carte carteAleatoire() {
		RareteCarte rarete = null;
		double random = Math.random() * 1;

		// Commun : 60%
		if (random < 0.6) {
			rarete = RareteCarte.COMMUN;
		}
		// Non Commun : 37%
		else if (random < 0.97) {
			rarete = RareteCarte.NON_COMMUN;
		}
		// Rare : 3%
		else {
			rarete = RareteCarte.RARE;
		}



		final RareteCarte rareteChoisie = rarete;

		ArrayList<Carte> cartePossibles =
				cartesAssetParNom.keySet().stream().map((String nomCarte) -> {
					try {
						return instancierCarte(nomCarte);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}).filter(carte -> carte.getRarete() == rareteChoisie).filter(Carte::isPositive)
						.collect(Collectors.toCollection(ArrayList::new));

		int randomIndex = (int) (Math.random() * cartePossibles.size());
		return cartePossibles.get(randomIndex);
	}

	public Deck getDeck() {
		return deck;
	}

	public Heros getHeros() {
		return heros;
	}

}
