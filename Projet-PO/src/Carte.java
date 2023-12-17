import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Carte implements Cloneable {
    private String nom;
    private RareteCarte rarete;
    private int cout;
    private ArrayList<Effet> effets;
    private String description;
    private boolean aExiler;

    public Carte() {
        this.nom = "";
        this.rarete = RareteCarte.Commun;
        this.cout = 0;
        this.effets = new ArrayList<Effet>();
        this.aExiler = false;
        this.description = "";
    }

    public Carte(String nom, RareteCarte rarete, int cout, ArrayList<Effet> effets, boolean exile, String description) {
        this.nom = nom;
        this.rarete = rarete;
        this.cout = cout;
        this.effets = effets;
        this.aExiler = exile;
        this.description = description;
    }

    public void jouerCarte() {
        Hero hero = Partie.partie.getHero();

        boolean doitOnSelectionnerUneCible = effets.stream()
                .anyMatch(e -> e.getTypeCible() == TypeCible.SELECTION_JOUEUR);
        Monstre cibleSelectionee = null;
        if (doitOnSelectionnerUneCible) {
            ArrayList<Monstre> equipeMonstre = Partie.partie.getEquipeMonstreActuelle();
            int indice = demanderMonstre(equipeMonstre);
            cibleSelectionee = equipeMonstre.get(indice);
        }

        for (Effet effet : effets) {
            ArrayList<Entite> cibles = new ArrayList<Entite>();
            switch (effet.getTypeCible()) {
                case AUCUN:
                    break;
                case HERO:
                    cibles.add(Partie.partie.getHero());
                    break;
                case TOUS_LES_MONSTRES:
                    cibles.addAll(Partie.partie.getEquipeMonstreActuelle());
                    break;
                case MONSTRE_ALEATOIRE:
                    cibles.add(Partie.partie.getEquipeMonstreActuelle()
                            .get((int) (Math.random() * Partie.partie.getEquipeMonstreActuelle().size())));
                    break;
                case SELECTION_JOUEUR:
                    cibles.add(cibleSelectionee);
                    break;
                case LANCEUR:
                    throw new IllegalStateException(
                            "Une carte contient des effets. Ces effets ont un type de cible. Le type de cible LANCEUR corresponds au monstre qui à effectué l'action, et ne devrait pas être utilisé dans une carte.");
                default:
                    break;
            }

            effet.appliquerEffet(hero, cibles);
        }

        Partie.partie.getHero().setPointEnergie(Partie.partie.getHero().getPointEnergie() - this.cout);
        Partie.partie.defausseCarte(Partie.partie.getMain().indexOf(this));
    }

    private int demanderMonstre(ArrayList<Monstre> equipeMonstre) {
        System.out.println();
        for (int i = 0; i < equipeMonstre.size(); i++) {
            System.out.println("[" + i + "] "
                    + equipeMonstre.get(i).getNom()
                    + " - " + equipeMonstre.get(i).getPv()
                    + "/" + equipeMonstre.get(i).getPvMax()
                    + " PV");
        }
        System.out.println();

        int indiceMonstre = 0;
        boolean indiceValide = false;
        while (!indiceValide) {
            System.out.println("Choisissez un monstre : ");
            indiceMonstre = Partie.partie.getScanner().nextInt();

            if (indiceMonstre >= 0 && indiceMonstre < equipeMonstre.size()) {
                indiceValide = true;
            } else {
                System.out.println("Indice invalide");
            }
        }

        return indiceMonstre;
    }

    @Override
    public String toString() {

        return String.format("""
                Carte: %s
                Rareté: %s
                Cout: %d
                """, this.nom, this.rarete, this.cout) + "Description: " + this.description + "\nExile: "
                + this.aExiler;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Carte cloned = (Carte) super.clone();

        cloned.effets = new ArrayList<Effet>();
        for (Effet effet : this.effets) {
            cloned.effets.add((Effet) effet.clone());
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

    public ArrayList<Effet> getEffets() {
        return effets;
    }

    public String getDescription() {
        return description;
    }

    public boolean isaExiler() {
        return aExiler;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setaExiler(boolean exile) {
        this.aExiler = exile;
    }

    public void setEffets(ArrayList<Effet> effets) {
        this.effets = effets;
    }
}
