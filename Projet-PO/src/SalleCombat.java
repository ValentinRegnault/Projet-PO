import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class SalleCombat extends Salle {
    protected ArrayList<Monstre> equipeMonstre;

    public SalleCombat(ArrayList<Monstre> equipeMonstre) {
        this.equipeMonstre = equipeMonstre;
    }

    public ArrayList<Monstre> getEquipeMonstre() {
        return equipeMonstre;
    }

    public boolean combatTermine() {
        Hero hero = Partie.partie.getHero();
        if (hero.getPv() == 0) {
            return true;
        } else {
            for (Monstre monstre : equipeMonstre) {
                if (monstre.getPv() != 0) {
                    return false;
                }
            }
        }

        return true;

    }

    public boolean jouerSalle() {
        lancerCombat();
        Hero hero = Partie.partie.getHero();
        if (hero.getPv() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public void lancerCombat() {
        Hero hero = Partie.partie.getHero();
        // False -> Hero mort
        // True -> Victoire du hero
        while (!combatTermine()) {
            // Régénération de l'énergie du hero
            hero.setPointEnergie(hero.getPointEnergieMax());
            // Disparition des points de blocages du hero
            hero.setPointBlocage(0);

            // Affichage des PV
            System.out.println("PV Hero : " + hero.getPv() + "/" + hero.getPvMax());
            // Affichage des status
            hero.afficheStatus();

            // Affichage des PV des monstres et leurs status
            for (Monstre monstre : equipeMonstre) {
                System.out.println(
                        monstre.getNom() + " : " + monstre.getPv() + "/" + monstre.getPvMax());
                monstre.afficheStatus();
            }

            // Affichage de l'intention des monstres
            for (int i = 0; i < equipeMonstre.size(); i++) {
                System.out.println(i + ": " + equipeMonstre.get(i).getNom() + " - "
                        + equipeMonstre.get(i).genererIntention());
            }

            // Affichage du nombre de carte dans la pioche
            System.out.println("Cartes dans la pioche : " + Partie.partie.getPioche().size());

            // Affichage du nombre de carte dans la défausse
            System.out.println("Cartes dans la défausse : " + Partie.partie.getDefausse().size());

            // Affichage du nombre de carte dans l'exile
            System.out.println("Cartes dans l'exile : " + Partie.partie.getExile().size());

            /// TOUR DU HERO ///

            boolean finTour = false;
            while (!finTour) {
                // Affichage de l'énergie du héros
                System.out.println("Énergie: " + hero.getPointEnergie());

                // Affichage des cartes
                for (int i = 0; i < Partie.partie.getMain().size(); i++) {
                    System.out.println(i + " - " + Partie.partie.getMain().get(i).getNom());
                }

                // Demander au héros de choisir une carte ou de terminer le tour
                System.out.println("Choisissez une carte ou entrez -1 pour terminer votre tour");
                Scanner myObj = new Scanner(System.in);
                int index = myObj.nextInt();

                // Si le héros a choisi de terminer son tour
                if (index == -1) {
                    finTour = true;
                }

                else if (index >= 0 && index < Partie.partie.getMain().size()) {
                    // Si le héros a choisi une carte valide
                    Carte carteChoisie = Partie.partie.getMain().get(index);

                    // Si le héros a assez d'énergie pour jouer la carte
                    if (hero.getPointEnergie() >= carteChoisie.getCout()) {
                        // Jouer la carte
                        carteChoisie.jouerCarte(hero);
                    } else {
                        // Si le héros n'a pas assez d'énergie
                        System.out.println(
                                "Vous n'avez pas assez d'énergie pour jouer cette carte");
                    }
                }

                myObj.close();

                /// TOUR DES MONSTRES ///
                for (Monstre monstre : equipeMonstre) {
                    // Remise a zéro des points de blocage du monstre
                    monstre.setPointBlocage(0);
                    monstre.jouerAction(new ArrayList<Entite>(Arrays.asList(hero)));
                }

                /// TODO : Faire baisser les status

            }

        }

    }

    @Override
    public String toString() {
        return "SalleCombat [equipeMonstre=" + equipeMonstre + "]";
    }
}
