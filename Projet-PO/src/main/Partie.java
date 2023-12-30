package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.Carte.RareteCarte;

/**
 * La classe la plus abstraite, qui gérent l'ensemble du jeu et coordonne les
 * autres classes.
 * Cette classe en un Singleton, c'est à dire qu'il n'y a qu'une seule instance
 * qui existe, et elle est un attribut statique de la classe elle même. De cette
 * manière, on peut accéder à cette instance depuis n'importe où dans le code
 * avec {@link Partie#getPartie()}.
 */
public class Partie {
	private static final ArrayList<Salle> salles = new ArrayList<>(15);
	private static int indiceSalle = 0;

	/**
	 * Ensembles des fichiers de monstres du jeu, indexés par le nom du monstre.
	 */
	private static final TreeMap<String, File> monstresAssetParNom = new TreeMap<>();
	/**
	 * Ensembles des fichiers de cartes du jeu, indexés par le nom de la carte.
	 */
	private static final TreeMap<String, File> cartesAssetParNom = new TreeMap<>();

	/**
	 * Deck : Ensemble des cartes du joueur
	 */
	private static LinkedList<Carte> deck = new LinkedList<Carte>();

	/**
	 * Main : Cartes actuellement dans la main du joueur
	 */
	private static ArrayList<Carte> main = new ArrayList<Carte>();

	/**
	 * Defausse : Pile de cartes utilisées
	 */
	private static LinkedList<Carte> defausse = new LinkedList<Carte>();

	/**
	 * Exile : Pile de cartes utilisées ayant le statut Exile
	 */
	private static LinkedList<Carte> exile = new LinkedList<Carte>();

	/**
	 * Pioche : Carte dans laquelle le joueur peut piocher
	 */
	private static LinkedList<Carte> pioche = new LinkedList<Carte>();

	private static Scanner scanner = new Scanner(System.in);

	private static Hero hero = new Hero("Bob");


	/**
	 * Initialise la partie, en chargeant les fichiers de monstres et de cartes.
	 */
	public static void initPartie() {

		File dir = new File("assets/monstres");

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
					System.out.println("Erreur lors de la lecture du fichier de monstre " + file.getName());
					ioe.printStackTrace();
				}
			}

		}

		dir = new File("assets/cartes");
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
					System.out.println("Erreur lors de la lecture du fichier de carte " + file.getName());
					ioe.printStackTrace();
				}
			}
		}

		hero = new Hero("Bob");
	}

	/**
	 * Initialise les salles et lance la partie, jusqu'à ce que le joueur gagne ou
	 * perde.
	 */
	public static void jouerPartie() {
		genererSalles();
		for (Salle salle : salles) {
			genererPioche();
			boolean victoire = salle.jouerSalle();
			if (!victoire) {
				System.out.println();
				System.out.println("--- Vous avez perdu ---");
				return;
			}
			main.clear();
			defausse.clear();
			indiceSalle++;
		}

		System.out.println("Vous avez gagné");
	}

	/**
	 * Génère les salles du jeu selon le schéma décrit dans le sujet.
	 */
	private static void genererSalles() {
		// [C] -> [C] -> [R] -> [C] -> [C] -> [C] -> [R] -> [C] -> [C] -> [C] -> [R] ->
		// [C] -> [C] -> [R] -> [B]
		salles.clear();
		salles.add(new SalleMonstre(genererEquipeMonstre(1)));
		salles.add(new SalleMonstre(genererEquipeMonstre(2)));
		salles.add(new SalleRepos());
		salles.add(new SalleMonstre(genererEquipeMonstre(3)));
		salles.add(new SalleMonstre(genererEquipeMonstre(4)));
		salles.add(new SalleMonstre(genererEquipeMonstre(5)));
		salles.add(new SalleRepos());
		salles.add(new SalleMonstre(genererEquipeMonstre(6)));
		salles.add(new SalleMonstre(genererEquipeMonstre(7)));
		salles.add(new SalleMonstre(genererEquipeMonstre(8)));
		salles.add(new SalleRepos());
		salles.add(new SalleMonstre(genererEquipeMonstre(9)));
		salles.add(new SalleMonstre(genererEquipeMonstre(10)));
		salles.add(new SalleRepos());
		salles.add(new SalleBoss(genererEquipeMonstre(10)));
	}

	/**
	 * Instancie un monstre depuis un fichier JSON, à partir du nom du monstre.
	 * 
	 * @implNote Cette méthode utile l'attribut {@link Partie#monstresAssetParNom}
	 *           pour retrouver le fichier JSON correspondant au monstre.
	 * @param name le nom du monstre à instancier, tel qu'il est écrit dans le
	 *             fichier JSON du monstre (sensible à la casse, aux espaces
	 *             suplémentaires, etc.)
	 * @return Une instance de Monstre correspondant au monstre tel qu'il est décrit
	 *         dans le fichier JSON.
	 * @throws IOException Si le fichier JSON n'a pas pu être lu.
	 */
	public static Monstre instancierMonstre(String name) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Monstre m = mapper.readValue(monstresAssetParNom.get(name), Monstre.class);
			return m;
		} catch (IOException ioe) {
			System.out.println("Erreur lors de la lecture du fichier de monstre " + name);
			ioe.printStackTrace();
			throw ioe;
		}
	}

	/**
	 * Instancie une carte depuis un fichier JSON, à partir du nom de la carte.
	 * 
	 * @implNote Cette méthode utile l'attribut {@link Partie#cartesAssetParNom}
	 *           pour retrouver le fichier JSON correspondant à la carte.
	 * @param name le nom de la carte à instancier, tel qu'il est écrit dans le
	 *             fichier JSON de la carte (sensible à la casse, aux espaces
	 *             suplémentaires, etc.)
	 * @return Une instance de Carte correspondant à la carte tel qu'elle est
	 *         décrite dans le fichier JSON.
	 * @throws IOException Si le fichier JSON n'a pas pu être lu.
	 */
	public static Carte instancierCarte(String name) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Carte m = mapper.readValue(cartesAssetParNom.get(name), Carte.class);
			return m;
		} catch (IOException ioe) {
			System.out.println("Erreur lors de la lecture du fichier de carte " + name);
			ioe.printStackTrace();
			throw ioe;
		}
	}

	/**
	 * Génère une salle de monstres de difficulté donnée (entre 0 et 10). Dans une
	 * salle de difficulté n, il y a n/2 monstres. Ces monstres sont choisis
	 * aléatoirement de la manière suivante : - on tire n monstre au hasard parmi
	 * l'ensemble du catalogue de monstres - on choisi les n/2 monstres avec le plus
	 * de points de vie.
	 * 
	 * @param difficulte La difficulté de la salle entre 1 et 10
	 * @return Une liste de monstres
	 */
	private static ArrayList<Monstre> genererEquipeMonstre(int difficulte) {
		ArrayList<Monstre> monstresTires = new ArrayList<Monstre>();
		while (monstresTires.size() < difficulte) {
			try {
				int randomIndex = (int) (Math.random() * Partie.monstresAssetParNom.size());

				Monstre m = instancierMonstre(Partie.monstresAssetParNom.keySet().toArray()[randomIndex].toString());
				monstresTires.add(m);
			} catch (IOException ioe) {
				System.out.println("Erreur lors de la lecture du fichier de monstre.");
				ioe.printStackTrace();
			}
		}

		Collections.sort(monstresTires, (m1, m2) -> m2.getPvMax() - m1.getPvMax());
		return new ArrayList<>(monstresTires.subList(difficulte / 2, difficulte));
	}

	/**
	 * Instancie et retourne une carte aléatoire, en fonction de la rareté des
	 * cartes.
	 * 
	 * @apiNote Cette méthode est utilisée notamment pour générer les récompenses de
	 *          combat ({@link SalleCombat#choisirRecompense()}).
	 * @return Une instance de Carte, générée à partir du fichier JSON de la carte.
	 * @implNote Cette méthode utilise l'attribut {@link Partie#cartesAssetParNom}
	 *           pour retrouver les fichiers JSON des cartes et en choisir un
	 *           aléatoirement.
	 */
	public static Carte carteAleatoire() {
		RareteCarte rarete = RareteCarte.Commun;
		double random = (double) (Math.random() * 1);

		// Commun : 60%
		if (random < 0.6) {
			rarete = RareteCarte.Commun;
		}
		// Non Commun : 37%
		else if (random < 0.97) {
			rarete = RareteCarte.NonCommun;
		}
		// Rare : 3%
		else {
			rarete = RareteCarte.Rare;
		}

		final RareteCarte rareteChoisie = rarete;
		ArrayList<Carte> cartesDeRareteChoisie = cartesAssetParNom.keySet().stream()
				.map((String nomCarte) -> {
					try {
						return instancierCarte(nomCarte);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				})
				.filter(carte -> carte.getRarete() == rareteChoisie)
				.collect(Collectors.toCollection(ArrayList::new));

		int randomIndex = (int) (Math.random() * cartesDeRareteChoisie.size());
		return cartesDeRareteChoisie.get(randomIndex);
	}

	/**
	 * Retourne l'équipe de monstre contre laquelle le joueur est en train de se
	 * battre au moment de l'appel. Si le joueur n'est pas en train de se battre
	 * contre une équipe de monstre (n'est pas dans une salle de monstre), une
	 * exception est levée.
	 * 
	 * @implNote Cette méthode accèdes à la salle actuelle et récupère l'équipe de
	 *           monstre ({@link SalleMonstre#getEquipeMonstre()}).
	 * @return
	 * @throws IllegalStateException Si le joueur n'est pas en train de se battre
	 *                               contre une équipe de monstre (n'est pas dans
	 *                               une salle de monstre)
	 */
	public static ArrayList<Monstre> getEquipeMonstreActuelle() throws IllegalStateException {
		if (!(salles.get(indiceSalle) instanceof SalleMonstre)) {
			throw new IllegalStateException("Il n'y a pas d'équipe de monstres actuelle.");
		}

		return ((SalleMonstre) salles.get(indiceSalle)).getEquipeMonstre();
	}

	/**
	 * Fait piocher une carte au joueur dans la pioche. Si la pioche est vide, les
	 * cartes de la défausse sont remises dans la pioche, et la pioche est mélangée,
	 * sinon, la "carte du dessus" de la pioche est ajoutée à la main du joueur.
	 */
	public static void piocherCarte() {
		// Si la pioche est vide
		if (pioche.isEmpty()) {
			// Remettre toutes les cartes de la défausse dans la pioche
			while (!defausse.isEmpty()) {
				pioche.push(defausse.pop());
			}

			// Mélanger la pioche
			Collections.shuffle(pioche);
		}

		// Si la pioche n'est pas vide après avoir remis les cartes de la défausse
		if (!pioche.isEmpty()) {
			// Prendre la carte du dessus de la pioche
			Carte carte = pioche.pop();

			// Ajouter cette carte à la main
			main.add(carte);
			System.out.println("Vous piochez la carte " + carte.getNom() + " - " + carte.getDescription() + " - "
					+ carte.getCout() + " points d'énergie");
		}
	}

	/**
	 * Défausse une carte de la main du joueur. La carte est ajoutée à la défausse
	 * 
	 * @param indice L'indice de la carte dans la main du joueur
	 */
	public static void defausseCarte(int indice) {
		// index de la carte dans la main

		Carte carteUtilise = main.get(indice);
		main.remove(indice);

		if (carteUtilise.isaExiler()) {
			exile.add(carteUtilise);
		} else {
			defausse.add(carteUtilise);
		}
	}

	/**
	 * Ajoute une carte dans la défausse.
	 * @param indice
	 */
	public static void ajouterCarteDefausse(Carte carte) {
		defausse.add(carte);
	}

	/**
	 * Exile une carte de la main du joueur. La carte est ajoutée à la pile d'exile.
	 * 
	 * @param indice
	 */
	private static void exileCarte(int indice) {
		// index de la carte dans la main

		Carte carteUtilise = main.get(indice);
		main.remove(indice);

		exile.add(carteUtilise);
	}

	/**
	 * Génère la pioche à partir du deck du joueur. La pioche est mélangée.
	 */
	public static void genererPioche() {
		pioche.clear();
		for (Carte carte : deck) {
			try {
				pioche.push((Carte) carte.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		Collections.shuffle(pioche);
	}

	/**
	 * Vide la defausse
	 */
	public static void videDefausse() {
		defausse = new LinkedList<Carte>();
	}

	/**
	 * Vide la pile d'exile
	 */
	private static void videExile() {
		exile = new LinkedList<Carte>();
	}

	/**
	 * Vide la main
	 */
	private static void videMain() {
		main = new ArrayList<Carte>();
	}

	public static LinkedList<Carte> getDefausse() {
		return defausse;
	}

	public static LinkedList<Carte> getDeck() {
		return deck;
	}

	public static ArrayList<Carte> getMain() {
		return main;
	}

	public static Hero getHero() {
		return hero;
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public static void ajouterCarteDeck(Carte carte) {
		deck.add(carte);
	}
}
