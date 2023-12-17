import java.util.ArrayList;

public class Energie extends Effet {

    public Energie() {
        super(0, TypeCible.AUCUN);
    }

    public Energie(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        // Inflige prends en compte les points de blocage
        for (Entite cible : cibles) {
            if (cible instanceof Hero hero) {
                hero.setPointEnergie(hero.getPointEnergie() + pointEffet);
                System.out.println(hero.getNom() + " gagne " + pointEffet + " points d'énergie");
            }
            else {
                throw new IllegalArgumentException("La cible de l'effet Energie doit être un Hero");
            }
        }
    }

    @Override
    public String toString() {
        return "Le lanceur gagne " + this.pointEffet + " points d'énergie.";
    }

}
