file://<HOME>/Bureau/Labo/Java/PO%20-%20L2/Projet%20PO/Projet-PO/src/main/Partie.java
### java.util.NoSuchElementException: next on empty iterator

occurred in the presentation compiler.

action parameters:
offset: 735
uri: file://<HOME>/Bureau/Labo/Java/PO%20-%20L2/Projet%20PO/Projet-PO/src/main/Partie.java
text:
```scala
package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.swing.text.html.Option;
import com.fasterxml.jackson.databind.ObjectMapper;
import librairies.StdDraw;
import main.Carte.EtatCarte;
import main.Carte.RareteCarte;
import ressources.AssociationTouches;
import ressources.Config;

/**
 * La classe la plus abstraite, qui gérent l'ensemble du jeu et coordonne les autres classes.
 * 
 * @apiNote Cette classe contient tout les élements du jeux, et donne l'accès à certains élements
 *   @@       depuis à des instances qu'elle contient, notamment les effets. Les effets impact le jeu
 *          en passant par les méthodes statiques de cette classe prévues à cet usage.
 * @implNote Cette classe est "statique" : tous ses attributs et méthodes sont statiques, et donc
 *           accessibles globalement.
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
	 * Toutes les cartes du joueurs. Les constructeurs {@link Partie#getPioche},
	 * {@link Partie#getMain}, {@link Partie#getDefausse} et {@link Partie#getExile} permettent
	 * d'accéder à des sous-ensembles de ces cartes.
	 */
	private static LinkedList<Carte> toutesLesCartes = new LinkedList<Carte>();

	private static Scanner scanner = new Scanner(System.in);

	private static Heros heros = new Heros("Bob");
	private static Affichable background =
			new Sprite("assets" + File.separator + "pictures" + File.separator + "background.jpg",
					0.0, 0.0, Config.X_MAX, Config.Y_MAX);
	private static UI ui = new UI();

	/**
	 * Initialise la partie, en chargeant les fichiers de monstres et de cartes.
	 */
	public static void initPartie() {

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

		heros = new Heros("Bob");
	}

	/**
	 * Affiche tout les éléments du jeu, en appelant leurs méthodes
	 * {@link Affichable#afficher}.
	 */
	public static void afficherLeMonde() {
		StdDraw.clear();

		ArrayList<Affichable> monde = new ArrayList<>();
		monde.add(background);
		List<Carte> main = getMain();
		monde.addAll(main);

		for (int i = 0; i < main.size(); i++) {
			Carte carte = main.get(i);
			carte.setX(250 + i * (Carte.LARGEUR_CARTE + 40));
			carte.setY(20);
		}

		Optional<ArrayList<Monstre>> equipeActuelle = getEquipeMonstreActuelle();
		if (equipeActuelle.isPresent())
			monde.addAll(equipeActuelle.get());
		
		monde.add(heros);
		monde.add(ui);

		monde.stream().forEach(a -> a.afficher());

		StdDraw.show();
	}

	/**
	 * Initialise les salles et lance la partie, jusqu'à ce que le joueur gagne ou perde.
	 */
	public static void jouerPartie() {
		genererSalles();
		for (Salle salle : salles) {
			genererPioche();
			afficherLeMonde();
			boolean victoire = salle.jouerSalle();
			if (!victoire) {
				ui.setTexteExplicatif("Vous avez perdu :/");
				return;
			}
			indiceSalle++;
		}

		ui.setTexteExplicatif("Victoire !");
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
			Carte m = mapper.readValue(cartesAssetParNom.get(name), Carte.class);
			return m;
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
		ArrayList<Carte> cartesDeRareteChoisie =
				cartesAssetParNom.keySet().stream().map((String nomCarte) -> {
					try {
						return instancierCarte(nomCarte);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}).filter(carte -> carte.getRarete() == rareteChoisie)
						.collect(Collectors.toCollection(ArrayList::new));

		int randomIndex = (int) (Math.random() * cartesDeRareteChoisie.size());
		return cartesDeRareteChoisie.get(randomIndex);
	}

	/**
	 * Retourne l'équipe de monstre contre laquelle le joueur est en train de se battre au moment de
	 * l'appel. Si le joueur n'est pas en train de se battre contre une équipe de monstre (n'est pas
	 * dans une salle de monstre), une exception est levée.
	 * 
	 * @implNote Cette méthode accèdes à la salle actuelle et récupère l'équipe de monstre
	 *           ({@link SalleMonstre#getEquipeMonstre()}).
	 * @return
	 * @throws IllegalStateException Si le joueur n'est pas en train de se battre contre une équipe
	 *         de monstre (n'est pas dans une salle de monstre)
	 */
	public static Optional<ArrayList<Monstre>> getEquipeMonstreActuelle() {
		if (!(salles.get(indiceSalle) instanceof SalleMonstre)) {
			return Optional.empty();
		}

		return Optional.of(((SalleMonstre) salles.get(indiceSalle)).getEquipeMonstre());
	}

	/**
	 * Fait piocher une carte au joueur dans la pioche. Si la pioche est vide, les cartes de la
	 * défausse sont remises dans la pioche, et la pioche est mélangée. Puis, la "carte du dessus"
	 * de la pioche est ajoutée à la main du joueur.
	 */
	public static void piocherCarte() {
		if (getPioche().isEmpty()) {
			toutesLesCartes.stream().filter(c -> c.getEtat() == EtatCarte.DEFAUSSEE)
					.forEach(c -> c.setEtat(EtatCarte.DANS_PIOCHE));
			Collections.shuffle(toutesLesCartes);
		} else {
			Carte carte = getPioche().get(0);
			carte.setEtat(EtatCarte.DANS_MAIN);
			System.out.println("Vous piochez la carte " + carte.getNom() + " - "
					+ carte.getDescription() + " - " + carte.getCout() + " points d'énergie");
		}
	}

	/**
	 * Défausse une carte de la main du joueur. La carte est ajoutée à la défausse
	 * 
	 * @param indice L'indice de la carte dans la main du joueur
	 */
	public static void defausseCarte(int indice) {
		// index de la carte dans la main

		List<Carte> main = getMain();
		Carte carteUtilise = main.get(indice);
		carteUtilise.setEtat(carteUtilise.isaExiler() ? EtatCarte.EXILEE : EtatCarte.DEFAUSSEE);
	}

	/**
	 * Ajoute une carte dans la défausse.
	 * 
	 * @param indice
	 */
	public static void ajouterCarteDefausse(Carte carte) {
		carte.setEtat(EtatCarte.DEFAUSSEE);
	}

	/**
	 * Retire les cartes Brûlure de la main du joueur, et les défausse.
	 */
	public static void defausseCarteBruluresDeLaMain() {
		List<Carte> main = getMain();
		main.stream().filter(carte -> carte.getNom().equals("Brûlure"))
				.forEach(carte -> carte.setEtat(EtatCarte.DEFAUSSEE));
	}

	/**
	 * Change l'état de toutes les cartes pour les mettre dans la pioche, puis mélange la pioche.
	 */
	public static void genererPioche() {
		toutesLesCartes.stream().forEach(c -> c.setEtat(EtatCarte.DANS_PIOCHE));
		Collections.shuffle(toutesLesCartes);
	}


	/**
	 * Retourne un monstre sélectionné par le joueur.
	 * 
	 * @see {@link UI#demanderMonstre()}
	 */
	public static Monstre demanderMonstre() {
		return ui.demanderMonstre();
	}

	/**
	 * Retourne une carte sélectionnée par le joueur.
	 * 
	 * @see {@link UI#demanderCarte()}
	 */
	public static Optional<Carte> demanderCarte() {
		return ui.demanderCarte();
	}

	/**
	 * Change le texte explicatif affiché en haut de l'écran.
	 * 
	 * @param texte le nouveau texte explicatif
	 * 
	 * @see {@link UI#setTexteExplicatif()}
	 */
	public static void setTexteExplicatif(String texte) {
		ui.setTexteExplicatif(texte);
	}

	public static List<Carte> getDeck() {
		// En java, il n'existe pas de liste immutable, donc on retourne une liste
		// non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
		// C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
		// nous avons trouvé en java.
		return Collections.unmodifiableList(toutesLesCartes.stream()
				.filter(carte -> carte.getEtat() != EtatCarte.EXILEE).collect(Collectors.toList()));
	}

	public static List<Carte> getDefausse() {
		// En java, il n'existe pas de liste immutable, donc on retourne une liste
		// non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
		// C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
		// nous avons trouvé en java.
		return Collections.unmodifiableList(
				toutesLesCartes.stream().filter(carte -> carte.getEtat() == EtatCarte.DEFAUSSEE)
						.collect(Collectors.toList()));
	}

	public static List<Carte> getMain() {
		// En java, il n'existe pas de liste immutable, donc on retourne une liste
		// non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
		// C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
		// nous avons trouvé en java.
		return Collections.unmodifiableList(
				toutesLesCartes.stream().filter(carte -> carte.getEtat() == EtatCarte.DANS_MAIN)
						.collect(Collectors.toList()));
	}

	public static List<Carte> getPioche() {
		// En java, il n'existe pas de liste immutable, donc on retourne une liste
		// non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
		// C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
		// nous avons trouvé en java.
		return Collections.unmodifiableList(
				toutesLesCartes.stream().filter(carte -> carte.getEtat() == EtatCarte.DANS_PIOCHE)
						.collect(Collectors.toList()));
	}

	public static List<Carte> getExilees() {
		// En java, il n'existe pas de liste immutable, donc on retourne une liste
		// non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
		// C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
		// nous avons trouvé en java.
		return Collections.unmodifiableList(toutesLesCartes.stream()
				.filter(carte -> carte.getEtat() == EtatCarte.EXILEE).collect(Collectors.toList()));
	}

	public static Heros getHeros() {
		return heros;
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public static void ajouterCarte(Carte carte) {
		toutesLesCartes.add(carte);
	}
}

```



#### Error stacktrace:

```
scala.collection.Iterator$$anon$19.next(Iterator.scala:973)
	scala.collection.Iterator$$anon$19.next(Iterator.scala:971)
	scala.collection.mutable.MutationTracker$CheckedIterator.next(MutationTracker.scala:76)
	scala.collection.IterableOps.head(Iterable.scala:222)
	scala.collection.IterableOps.head$(Iterable.scala:222)
	scala.collection.AbstractIterable.head(Iterable.scala:933)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:168)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.HoverProvider$.hover(HoverProvider.scala:34)
	scala.meta.internal.pc.ScalaPresentationCompiler.hover$$anonfun$1(ScalaPresentationCompiler.scala:342)
```
#### Short summary: 

java.util.NoSuchElementException: next on empty iterator