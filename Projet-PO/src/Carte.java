import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Carte implements Cloneable {
    private String nom;
    private RareteCarte rarete;
    private int cout;
    private ArrayList<Effet> effetsLanceur;
    private ArrayList<Effet> effetsCible;
    private String description;
    private boolean exile;
    private boolean aCible;

    public Carte() {
        this.nom = "";
        this.rarete = RareteCarte.Commun;
        this.cout = 0;
        this.effetsCible = new ArrayList<Effet>();
        this.effetsLanceur = new ArrayList<Effet>();
        this.exile = false;
        this.aCible = false;
        this.description = "";
    }

    public Carte(String nom, RareteCarte rarete, int cout, ArrayList<Effet> effetsLanceur,
            ArrayList<Effet> effetsCible, boolean exile, boolean aCible, String description) {
        this.nom = nom;
        this.rarete = rarete;
        this.cout = cout;
        this.effetsCible = effetsCible;
        this.effetsLanceur = effetsLanceur;
        this.exile = exile;
        this.aCible = aCible;
        this.description = description;
    }

    public void jouerCarte(Hero lanceur) {
        Monstre cible = null;

        if (this.aCible) {
            System.out.println("Choisissez une cible");
            SalleCombat salle = (SalleCombat) Partie.partie.getSalleActuelle();
            ArrayList<Monstre> listeMonstre = salle.getEquipeMonstre();
            int index = -1;
            Scanner myObj = new Scanner(System.in);

            while (index < 0 || index >= listeMonstre.size()) {

                for (int i = 0; i < listeMonstre.size(); i++) {
                    System.out.println(i + " - " + listeMonstre.get(i).getNom());
                }
                index = myObj.nextInt();
            }

            myObj.close();

            cible = listeMonstre.get(index);

        }

        if (lanceur instanceof Hero hero) {
            hero.setPointEnergie(hero.getPointEnergie() - this.cout);
        }

        for (int i = 0; i < effetsLanceur.size(); i++) {
            effetsLanceur.get(i).appliquerEffet(lanceur, null);
        }

        for (int i = 0; i < effetsCible.size(); i++) {
            effetsCible.get(i).appliquerEffet(lanceur, new ArrayList<Entite>(Arrays.asList(cible)));
        }

        Partie.partie.defausseCarte(Partie.partie.getMain().indexOf(this));

    }

    @Override
    public String toString() {

        return String.format("""
                Carte: %s
                Rareté: %s
                Cout: %d
                """, this.nom, this.rarete, this.cout) + "Description: " + this.description + "\nExile: " + this.exile;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Carte cloned = (Carte) super.clone();

        cloned.effetsLanceur = new ArrayList<>();
        for (Effet effet : this.effetsLanceur) {
            cloned.effetsLanceur.add((Effet) effet.clone());
        }

        cloned.effetsCible = new ArrayList<>();
        for (Effet effet : this.effetsCible) {
            cloned.effetsCible.add((Effet) effet.clone());
        }

        return cloned;
    }

    public String getNom() {
        return nom;
    }

    public RareteCarte getRarete() {
        return rarete;
    }

    public int getCout() {
        return cout;
    }

    public ArrayList<Effet> getEffetsLanceur() {
        return effetsLanceur;
    }

    public ArrayList<Effet> getEffetsCible() {
        return effetsCible;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExile() {
        return exile;
    }

    public boolean isaCible() {
        return aCible;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRarete(RareteCarte rarete) {
        this.rarete = rarete;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public void setEffetsLanceur(ArrayList<Effet> effetsLanceur) {
        this.effetsLanceur = effetsLanceur;
    }

    public void setEffetsCible(ArrayList<Effet> effetsCible) {
        this.effetsCible = effetsCible;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExile(boolean exile) {
        this.exile = exile;
    }

    public void setaCible(boolean aCible) {
        this.aCible = aCible;
    }

}
