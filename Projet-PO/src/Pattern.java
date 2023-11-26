public interface Pattern {
    

    public void jouerAction(Monstre lanceur, Entite cible);

    public abstract void actionSuivante();

    public abstract void afficherIntention();

}
