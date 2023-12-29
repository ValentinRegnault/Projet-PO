package effets;
import java.util.ArrayList;

import main.Entite;
import main.TypeCible;

public class Divise extends Effet {
    public Divise() {
        super(0, TypeCible.AUCUN);
    }

    public Divise(int pointEffet, TypeCible typeCible) {
        super(pointEffet, typeCible);
    }

    @Override
    public void appliquerEffet(Entite lanceur, ArrayList<Entite> cibles) {
        for (Entite cible : cibles) {
            for (int i = 0; i < this.pointEffet; i++) {
                int degat = (cible.getPv() + 1) / 12;
                cible.setPv(cible.getPv() - degat);
            }
        }
    }

    @Override
    public String toString() {
        return "Inflige H dégâts 6 fois avec H = 1 + (PV du héros) / 12";
    }
}
