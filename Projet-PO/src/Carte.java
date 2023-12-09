import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Carte implements Cloneable {
    enum Rarete {
        Commun,
        NonCommun,
        Rare
    }

    enum CarteExistante {
        Frappe,
        Defense,
        Heurt,
        MemePasMal,
        VagueDeFer,
        FrappeDuPommeau,
        FrappeDouble,
        Enchainement,
        EpeeBoomerang,
        Manchette,
        Plaquage,
        Saignee,
        Hemokinesie,
        Uppercut,
        VoleeDeCoups,
        VoirRouge,
        Enflammer,
        Desarmement,
        OndeDeChoc,
        Tenacite,
        Gourdin,
        Invincible,
        Offrande,
        FormeDemoniaque
    }

    private String nom;
    private Rarete rarete;
    private int cout;
    private ArrayList<Effet> effetsLanceur;
    private ArrayList<Effet> effetsCible;
    private String description;
    private boolean exile;
    private boolean aCible;

    

    public String getNom() {
        return nom;
    }

    public Rarete getRarete() {
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

    public Carte(String nom, Carte.Rarete rarete, int cout, ArrayList<Effet> effetsLanceur,
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

    public void jouerCarte(Entite lanceur) {
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
        return super.clone();
    }

}
