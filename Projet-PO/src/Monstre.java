public class Monstre extends Entite{
    
    Pattern pattern;


    public Monstre(String nom, int pvMax, Pattern pattern){
        super(nom, pvMax, 0);
        this.pattern = pattern;
    }

    public void jouerAction(Entite cible){
        this.pattern.jouerAction(this, cible);
    }

    @Override
    public String toString() {
        return "Monstre [" + super.toString() + "pattern=" + pattern + "]";
    }

    

    

}
