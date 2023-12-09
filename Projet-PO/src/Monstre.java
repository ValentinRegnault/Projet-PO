import java.util.Arrays;
import java.util.ArrayList;

enum TypeMontre {
    PetitSlimePiquant,
    PetitSlimeAcide,
    Machouilleur,
    Cultiste,
    SlimePiquant,
    SlimeAcide,
    Hexaghost,
}

public class Monstre extends Entite {
    Pattern pattern;

    public Monstre(String nom, int pvMax, Pattern pattern) {
        super(nom, pvMax, 0);
        this.pattern = pattern;
    }

    public void jouerAction(ArrayList<Entite> listeCible) {
        this.pattern.jouerAction(this, listeCible);
    }

    @Override
    public String toString() {
        return "Monstre [" + super.toString() + "]";
    }

    public static Monstre creerPetitSlimePiquant () {
        ArrayList<Effet> effets = new ArrayList<>();
        effets.add(new AppliqueDegat(5));

        Action charge = new Action("Charge", new ArrayList< >(), effets);
        
        ArrayList<Action> listeActions = new ArrayList<>();
        listeActions.add(charge);

        Cyclique pattern = new Cyclique(listeActions);
        
        return new Monstre("Petit Slime Piquant", 12, pattern);
    }

    public static Monstre creerPetitSlimeAcide () {
        ArrayList<Effet> effetsCharge = new ArrayList<>();
        effetsCharge.add(new AppliqueDegat(5));

        ArrayList<Effet> effetsAcide = new ArrayList<>();
        effetsAcide.add(new Faiblesse(1));


        Action charge = new Action("Charge", new ArrayList< >(), effetsCharge);
        Action lecher = new Action("Lêcher", new ArrayList< >(), effetsAcide);

        ArrayList<Action> listeActions = new ArrayList<>();
        listeActions.add(charge);


        Cyclique pattern = new Cyclique(listeActions);
        
        return new Monstre("Petit Slime Piquant", 12, pattern);
    }
}
