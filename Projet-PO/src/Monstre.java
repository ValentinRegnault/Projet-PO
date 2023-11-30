import java.util.ArrayList;

public class Monstre extends Entite{
    
    Pattern pattern;


    public Monstre(String nom, int pvMax, Pattern pattern){
        super(nom, pvMax, 0);
        this.pattern = pattern;
    }

    public void jouerAction(ArrayList<Entite> listeCible){
        this.pattern.jouerAction(this, listeCible);
    }

    @Override
    public String toString() {
        return "Monstre [" + super.toString() +  "]";
    }

    

    

}
