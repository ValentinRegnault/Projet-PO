import java.util.Arrays;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Monstre extends Entite implements Serializable {
    Pattern pattern;


    public Monstre() {
        super("", 0, 0); // Call to superclass constructor with required arguments
    }

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

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
