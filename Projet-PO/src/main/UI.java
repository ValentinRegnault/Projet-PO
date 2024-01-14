package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ressources.Affichage;
import ressources.AssociationTouches;
import ressources.Config;


public class UI extends Affichable {
    private static final String INDICES_MONSTRES = "AZERTYUIOP";
    private static final String INDICES_CARTES = "QSDFGHJKLM";
    private boolean afficherSelecteurMonsre = false;
    private boolean afficherSelecteurCarte = false;
    private ArrayList<Character> caractereSelecteurMonstre = new ArrayList<>();
    private ArrayList<Character> caractereSelecteurCarte = new ArrayList<>();
    private String texteExplicatif = "";

    public UI() {
        super(0.0, 0.0);
    }

    @Override
    public void afficher() {
        // Affichage texte explicatif
        Affichage.texteCentre(Config.X_MAX / 2.0, Config.Y_MAX - 20.0, texteExplicatif);

        // Affichage pioche
        double padding = 10.0;
        Affichage.texteGauche(padding, padding, "Pioche : " + Partie.getPioche().size());

        // Affichage défausse
        Affichage.texteDroite(Config.X_MAX - padding, padding,
                "Défausse : " + Partie.getDefausse().size());

        // Affichage exilées
        Affichage.texteDroite(Config.X_MAX - padding, padding + 20,
                "Exilées : " + Partie.getExilees().size());

        // Affichage énergie
        Affichage.texteGauche(padding, padding + 20,
                "Energie : " + Partie.getHeros().getPointEnergie());

        if (afficherSelecteurCarte) {
            List<Carte> main = Partie.getMain();
            updateCaracteresCarte();
            System.out.println(caractereSelecteurCarte);

            for (int i = 0; i < main.size(); i++) {
                Carte carte = main.get(i);

                Affichage.selecteur(String.valueOf(caractereSelecteurCarte.get(i)),
                        carte.getX() + Carte.LARGEUR_CARTE / 2,
                        carte.getY() + Carte.HAUTEUR_CARTE + 20, 20, 20);
            }
        }

        if (afficherSelecteurMonsre) {
            List<Monstre> equipeMonstre = Partie.getEquipeMonstreActuelle().get();
            updateCaracteresMonstre();

            for (int i = 0; i < equipeMonstre.size(); i++) {
                Monstre monstre = equipeMonstre.get(i);
                Affichage.selecteur(String.valueOf(caractereSelecteurMonstre.get(i)),
                        monstre.getX() + monstre.getWidth() / 2, monstre.getY(), 20, 20);
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
    public Monstre demanderMonstre() {
        updateCaracteresMonstre();
        afficherSelecteurMonstre(true);
        setTexteExplicatif(
                "Choisissez un monstre à attaquer en appuyant sur une touche associé à un monstre.");

        Monstre monstreSelectionne = null;
        while (monstreSelectionne == null) {
            String touche = AssociationTouches.trouveProchaineEntree();

            // Plutot que d'imbriquer des if les uns dans les autres, je préfère
            // arrêter le tour de boucle en cours et sauter au suivant dès qu'une condition
            // est invalide.
            if (touche == null || touche.length() == 0)
                continue;
            if (!caractereSelecteurMonstre.contains(touche.charAt(0)))
                continue;
            if (Partie.getEquipeMonstreActuelle().get().size() <= caractereSelecteurMonstre
                    .indexOf(touche.charAt(0)))
                continue;

            monstreSelectionne = Partie.getEquipeMonstreActuelle().get()
                    .get(caractereSelecteurMonstre.indexOf(touche.charAt(0)));
        }

        afficherSelecteurMonstre(false);
        return monstreSelectionne;
    }


    /**
     * Demande à l'utilisateur de choisir une carte de sa main. Cette méthode est synchrone (elle
     * bloque l'execution tant que l'utilisateur n'a pas choisi de monstre).
     * 
     * @return la carte choisi par l'utilisateur.
     */
    public Optional<Carte> demanderCarte() {
        updateCaracteresCarte();
        afficherSelecteurCarte(true);
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
            if (Partie.getMain().size() <= caractereSelecteurCarte.indexOf(touche.charAt(0))) {
                setTexteExplicatif(
                        "Touche invalide, veuillez choisir une touche associé à une carte de votre main. Les touches possibles s'affichent au dessus des cartes. Echap pour mettre fin à votre tour.");
                continue;
            }
            if (Partie.getMain().get(caractereSelecteurCarte.indexOf(touche.charAt(0)))
                    .getCout() > Partie.getHeros().getPointEnergie()) {
                setTexteExplicatif(
                        "Vous ne pouvez pas jouer cette carte, vous n'avez pas assez d'énergie. Echap pour mettre fin à votre tour.");
                continue;
            }

            carte = Optional
                    .of(Partie.getMain().get(caractereSelecteurCarte.indexOf(touche.charAt(0))));
            carteChoisie = true;
        }

        afficherSelecteurCarte(false);
        return carte;
    }


    /**
     * Affiche les selecteurs des monstres, met à jour le texte explicatif et raffraichit
     * l'affichage du monde.
     * 
     * @param afficherSelecteurMonsre Si vrai, affiche les selecteurs des monstres.
     */
    public void afficherSelecteurMonstre(boolean afficherSelecteurMonsre) {
        caractereSelecteurMonstre = new ArrayList<>(Partie.getEquipeMonstreActuelle().get().size());
        for (int i = 0; i < caractereSelecteurMonstre.size(); i++) {
            caractereSelecteurMonstre.set(i, INDICES_MONSTRES.charAt(i));
        }
        this.afficherSelecteurMonsre = afficherSelecteurMonsre;
        Partie.afficherLeMonde();
    }

    /**
     * Affiche les selecteurs des cartes, met à jour le texte explicatif et raffraichit l'affichage
     * du monde.
     * 
     * @param afficherSelecteurMonsre Si vrai, affiche les selecteurs des cartes.
     * @return Les caractères associés aux cartes qui permettent de les sélectionner.
     */
    public void afficherSelecteurCarte(boolean afficherSelecteurCarte) {
        caractereSelecteurCarte = new ArrayList<>(Partie.getMain().size());
        for (int i = 0; i < caractereSelecteurCarte.size(); i++) {
            caractereSelecteurCarte.set(i, INDICES_CARTES.charAt(i));
        }
        this.afficherSelecteurCarte = afficherSelecteurCarte;
        Partie.afficherLeMonde();
    }

    private void updateCaracteresMonstre() {
        caractereSelecteurMonstre = new ArrayList<>(Partie.getEquipeMonstreActuelle().get().size());
        for (int i = 0; i < Partie.getEquipeMonstreActuelle().get().size(); i++) {
            caractereSelecteurMonstre.add(INDICES_MONSTRES.charAt(i));
        }
    }

    private void updateCaracteresCarte() {
        caractereSelecteurCarte = new ArrayList<>(Partie.getMain().size());
        for (int i = 0; i < Partie.getMain().size(); i++) {
            caractereSelecteurCarte.add(INDICES_CARTES.charAt(i));
        }
    }

    public String getTexteExplicatif() {
        return texteExplicatif;
    }

    public void setTexteExplicatif(String texteExplicatif) {
        this.texteExplicatif = texteExplicatif;
        Partie.afficherLeMonde();
    }
}
