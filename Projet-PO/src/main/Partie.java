package main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Partie {
	private ArrayList<Salle> salles = new ArrayList<>(15);
	private int indiceSalle;

	private final TreeMap<String, File> monstresAssetParNom;
	private final TreeMap<String, File> cartesAssetParNom;

	// Deck : Ensemble des cartes du joueur
	private LinkedList<Carte> deck = new LinkedList<Carte>();

	// Main : Cartes actuellement dans la main du joueur
	private ArrayList<Carte> main = new ArrayList<Carte>();

	// Defausse : Pile de cartes utilisées
	private LinkedList<Carte> defausse = new LinkedList<Carte>();

	// Exile : Pile de cartes utilisées ayant le statut Exile
	private LinkedList<Carte> exile = new LinkedList<Carte>();

	// Pioche : Carte dans laquelle le joueur peut piocher
	private LinkedList<Carte> pioche = new LinkedList<Carte>();

	private Scanner scanner = new Scanner(System.in);

	protected Hero hero;

	private static Partie partie = new Partie();

	private Partie() {
		this.monstresAssetParNom = new TreeMap<String, File>();

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
					this.monstresAssetParNom.put(m.nom, file);
				} catch (IOException ioe) {
					System.out.println("Erreur lors de la lecture du fichier de monstre " + file.getName());
					ioe.printStackTrace();
				}
			}

		}

		this.cartesAssetParNom = new TreeMap<String, File>();
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
					this.cartesAssetParNom.put(c.getNom(), file);
				} catch (IOException ioe) {
					System.out.println("Erreur lors de la lecture du fichier de carte " + file.getName());
					ioe.printStackTrace();
				}
			}
		}

		System.out.println(this.deck);

		this.salles = new ArrayList<Salle>();
		this.hero = new Hero("Bob");
	}

	public void jouerPartie() {
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

	private void genererSalles() {
		// [C] -> [C] -> [R] -> [C] -> [C] -> [C] -> [R] -> [C] -> [C] -> [C] -> [R] ->
		// [C] -> [C] -> [R] -> [B]
		this.salles.clear();
		this.salles.add(new SalleMonstre(genererEquipeMonstre(1)));
		this.salles.add(new SalleMonstre(genererEquipeMonstre(2)));
		this.salles.add(new SalleRepos());
		this.salles.add(new SalleMonstre(genererEquipeMonstre(3)));
		this.salles.add(new SalleMonstre(genererEquipeMonstre(4)));
		this.salles.add(new SalleMonstre(genererEquipeMonstre(5)));
		this.salles.add(new SalleRepos());
		this.salles.add(new SalleMonstre(genererEquipeMonstre(6)));
		this.salles.add(new SalleMonstre(genererEquipeMonstre(7)));
		this.salles.add(new SalleMonstre(genererEquipeMonstre(8)));
		this.salles.add(new SalleRepos());
		this.salles.add(new SalleMonstre(genererEquipeMonstre(9)));
		this.salles.add(new SalleMonstre(genererEquipeMonstre(10)));
		this.salles.add(new SalleRepos());
		this.salles.add(new SalleBoss(genererEquipeMonstre(10)));
	}

	public static Monstre instancierMonstre(String name) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Monstre m = mapper.readValue(Partie.getPartie().monstresAssetParNom.get(name), Monstre.class);
			return m;
		} catch (IOException ioe) {
			System.out.println("Erreur lors de la lecture du fichier de monstre " + name);
			ioe.printStackTrace();
			throw ioe;
		}
	}

	public static Carte instancierCarte(String name) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Carte m = mapper.readValue(Partie.getPartie().cartesAssetParNom.get(name), Carte.class);
			return m;
		} catch (IOException ioe) {
			System.out.println("Erreur lors de la lecture du fichier de carte " + name);
			ioe.printStackTrace();
			throw ioe;
		}
	}

	/**
	 * Génère une salle de monstres de difficulté donnée (entre 0 et 10) Dans une
	 * salle de difficulté n, il y a n/2 monstres. Ces monstres sont choisis
	 * aléatoirement de la manière suivante : - on tire n monstre au hasard parmi
	 * l'ensemble du catalogue de monstres - on choisi les n/2 monstres avec le plus
	 * de points de vie.
	 * 
	 * @param difficulte La difficulté de la salle entre 1 et 10
	 * @return Une liste de monstres
	 */
	public ArrayList<Monstre> genererEquipeMonstre(int difficulte) {
		ArrayList<Monstre> monstresTires = new ArrayList<Monstre>();
		while (monstresTires.size() < difficulte) {
			try {
				int randomIndex = (int) (Math.random() * this.monstresAssetParNom.size());

				Monstre m = instancierMonstre(this.monstresAssetParNom.keySet().toArray()[randomIndex].toString());
				monstresTires.add(m);
			} catch (IOException ioe) {
				System.out.println("Erreur lors de la lecture du fichier de monstre.");
				ioe.printStackTrace();
			}
		}

		Collections.sort(monstresTires, (m1, m2) -> m2.getPvMax() - m1.getPvMax());
		return new ArrayList<>(monstresTires.subList(difficulte / 2, difficulte));
	}

	public Carte carteAleatoire() {
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

	public ArrayList<Monstre> getEquipeMonstreActuelle() throws IllegalStateException {
		if (!(salles.get(indiceSalle) instanceof SalleMonstre)) {
			throw new IllegalStateException("Il n'y a pas d'équipe de monstres actuelle.");
		}

		return ((SalleMonstre) salles.get(indiceSalle)).getEquipeMonstre();
	}

	public void piocheCarte() {
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

	public void defausseCarte(int index) {
		// index de la carte dans la main

		Carte carteUtilise = this.main.get(index);
		this.main.remove(index);

		if (carteUtilise.isaExiler()) {
			exile.add(carteUtilise);
		} else {
			defausse.add(carteUtilise);
		}
	}

	public void exileCarte(int index) {
		// index de la carte dans la main

		Carte carteUtilise = this.main.get(index);
		this.main.remove(index);

		exile.add(carteUtilise);
	}

	public void genererPioche() {
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

	public void creerMain() {
		main.clear();
		for (int i = 0; i < 5; i++) {
			if (!pioche.isEmpty()) {
				main.add(pioche.pop());
			}
		}
	}


	public static Partie getPartie() {
		return partie;
	}

	public void videDefausse() {
		this.defausse = new LinkedList<Carte>();
	}

	public void videExile() {
		this.exile = new LinkedList<Carte>();
	}

	public void videMain() {
		this.main = new ArrayList<Carte>();
	}

	public Hero getHero() {
		return hero;
	}

	public ArrayList<Salle> getSalles() {
		return salles;
	}

	public int getIndiceSalle() {
		return indiceSalle;
	}

	public Salle getSalleActuelle() {
		return salles.get(indiceSalle);
	}

	public LinkedList<Carte> getDeck() {
		return deck;
	}

	public LinkedList<Carte> getDefausse() {
		return defausse;
	}

	public LinkedList<Carte> getExile() {
		return exile;
	}

	public LinkedList<Carte> getPioche() {
		return pioche;
	}

	public ArrayList<Carte> getMain() {
		return main;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

}
