public abstract class Effet {
    protected int pointEffet;

    public Effet(int pointEffet){
        this.pointEffet = pointEffet;
    }
    protected abstract void appliquerEffet(Entite cible);

    public abstract String toString();

}
