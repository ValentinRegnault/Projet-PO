import java.io.IOException;
import java.util.ArrayList;

public class CarteBrulureDansDefausse extends Effet {
    public CarteBrulureDansDefausse() {
        super(0, TypeCible.AUCUN);
    }

    public CarteBrulureDansDefausse(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        try {
            for (int i = 0; i < this.pointEffet; i++) {
                Carte brulure = null;
                brulure = Partie.instancierCarte("Brûlure");
                Partie.partie.getDefausse().add(brulure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Ajoute une Brûlure dans la défausse";
    }

}
