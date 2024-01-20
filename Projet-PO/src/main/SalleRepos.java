package main;

import java.io.File;
import librairies.StdDraw;
import ressources.Affichage;
import ressources.AssociationTouches;
import ressources.Config;

public class SalleRepos extends Salle {

    public SalleRepos(Deck deck, Heros heros) {
        super(0, 0, deck, heros);
    }

    /**
     * Soigne le hero de 30% de ses PV max.
     */
    public void soin() {
        herosRef.setPv(herosRef.getPv() + (int) Math.round(herosRef.getPvMax() * 0.3));
    }

    @Override
    public void afficher() {
        StdDraw.clear();
        super.afficher();
        herosRef.afficher();
        StdDraw.show();
    }

    @Override
    public boolean jouerSalle() {
        soin();
        setTexteExplicatif(
                "Ouf ! Vous entrez dans une salle de repos et regagnez 30% de vos PV. Vous avez maintenant "
                        + herosRef.getPv() + " PV. ENTRER pour continuer");

        while (!AssociationTouches.trouveProchaineEntree().equals("Entree"));

        return true;
    }
}
