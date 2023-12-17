import java.util.ArrayList;

public class Pioche extends Effet {

    public Pioche() {
        super(0, TypeCible.AUCUN);
    }

    public Pioche(int nbCartes, TypeCible typeCible ) {
        super(nbCartes, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (int i = 0; i < this.pointEffet; i++) {
            Partie.partie.piocheCarte();
        }
    }

    @Override
    public String toString() {
        return "Pioche " + this.pointEffet + " cartes";
    }

}
