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

                if (hero.getPv() == 0) {
                        return false;
                } else {
                        return true;
                }

        }

        public void lancerCombat() {
                // False -> Hero mort
                // True -> Victoire du hero
                while (!combatTermine()) {

                        // Tour Joueur
                        // Affichage des cartes
                        System.out.println("Cartes en main : ");
                        for (int i = 0; i < Partie.partie.getMain().size(); i++) {
                                System.out.println(i + " - " + Partie.partie.getMain().get(i).getNom());
                        }

                        Scanner myObj = new Scanner(System.in);
                        int index = -1;
                        // Choix de la carte
                        while (index < 0 || index >= Partie.partie.getMain().size()) {
                                System.out.println("Choisissez une carte");
                                index = myObj.nextInt();
                        }

                        // On joue la carte
                        Partie.partie.getMain().get(index).jouerCarte(hero);

                        // Tour monstre
                        for (Monstre monstre : equipeMonstre) {
                                monstre.jouerAction(new ArrayList<Entite>(Arrays.asList(hero)));
                        }

                }

        }
}
