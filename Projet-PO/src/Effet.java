import java.util.ArrayList;


public abstract class Effet implements Cloneable {
    protected int pointEffet;

    public Effet(int pointEffet){
        this.pointEffet = pointEffet;
    }


    protected abstract void appliquerEffet(Entite lanceur, ArrayList<Entite> listeCible);

    public abstract String toString();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
