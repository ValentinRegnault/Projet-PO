import java.util.ArrayList;

public class PerdrePV extends Effet {
    public PerdrePV() {
        super(0, TypeCible.AUCUN);
    }

    public PerdrePV(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            // Inflige prends en compte les points de blocage et les statuts
            cible.setPv(cible.getPv() - pointEffet);
            System.out.println(
                    lanceur.getNom() + " fait perdre " + this.pointEffet + " points de vie à " + cible.getNom());
        }
    }

    @Override
    public String toString() {
        return "Fait perdre " + this.pointEffet + " points de vie à la cible.";
    }

}
