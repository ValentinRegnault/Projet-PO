package main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import main.Carte.EtatCarte;

public class Deck extends Affichable {
    LinkedList<Carte> toutesLesCartes;

    public Deck() {
        super(0, 0);
        toutesLesCartes = new LinkedList<>();
    }

    /**
     * Ajoute une carte dans le deck, avec l'état deffaussée.
     * 
     * @param carte
     */
    public void ajouterCarteDansDefausse(Carte carte) {
        toutesLesCartes.add(carte);
        carte.setEtat(EtatCarte.DEFAUSSEE);
    }

    /**
     * Fait piocher une carte au joueur dans la pioche. Si la pioche est vide, les cartes de la
     * défausse sont remises dans la pioche, et la pioche est mélangée. Puis, la "carte du dessus"
     * de la pioche est ajoutée à la main du joueur.
     */
    public void piocherCarte() {
        if (getPioche().isEmpty()) {
            toutesLesCartes.stream().filter(c -> c.getEtat() == EtatCarte.DEFAUSSEE)
                    .forEach(c -> c.setEtat(EtatCarte.DANS_PIOCHE));
            Collections.shuffle(toutesLesCartes);
        }
        Carte carte = getPioche().get(0);
        carte.setEtat(EtatCarte.DANS_MAIN);
    }

    /**
     * Défausse une carte de la main du joueur. La carte est ajoutée à la défausse
     * 
     * @param indice L'indice de la carte dans la main du joueur
     */
    public void defausseCarte(int indice) {
        List<Carte> main = getMain();
        Carte carteUtilise = main.get(indice);
        carteUtilise.setEtat(carteUtilise.isaExiler() ? EtatCarte.EXILEE : EtatCarte.DEFAUSSEE);
    }

    /**
     * Change l'état de toutes les cartes pour les mettre dans la pioche, puis mélange la pioche.
     */
    public void genererPioche() {
        getDefausse().forEach(c -> c.setEtat(EtatCarte.DANS_PIOCHE));
        Collections.shuffle(toutesLesCartes);
    }

    /**
     * Defausse toutes les cartes de la main du joueur.
     */
    public void defausserTouteLesCartes() {
        getMain().stream().forEach(carte -> carte.setEtat(EtatCarte.DEFAUSSEE));
    }

    /**
     * Retire les cartes Brûlure de la main du joueur, et les défausse.
     */
    public void defausseCarteBruluresDeLaMain() {
        List<Carte> main = getMain();
        main.stream().filter(carte -> carte.getNom().equals("Brûlure"))
                .forEach(carte -> carte.setEtat(EtatCarte.DEFAUSSEE));
    }

    public List<Carte> getDeck() {
        // En java, il n'existe pas de liste immutable, donc on retourne une liste
        // non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
        // C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
        // nous avons trouvé en java.
        return Collections.unmodifiableList(toutesLesCartes.stream()
                .filter(carte -> carte.getEtat() != EtatCarte.EXILEE).collect(Collectors.toList()));
    }

    public List<Carte> getDefausse() {
        // En java, il n'existe pas de liste immutable, donc on retourne une liste
        // non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
        // C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
        // nous avons trouvé en java.
        return Collections.unmodifiableList(
                toutesLesCartes.stream().filter(carte -> carte.getEtat() == EtatCarte.DEFAUSSEE)
                        .collect(Collectors.toList()));
    }

    public List<Carte> getMain() {
        // En java, il n'existe pas de liste immutable, donc on retourne une liste
        // non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
        // C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
        // nous avons trouvé en java.
        return Collections.unmodifiableList(
                toutesLesCartes.stream().filter(carte -> carte.getEtat() == EtatCarte.DANS_MAIN)
                        .collect(Collectors.toList()));
    }

    public List<Carte> getPioche() {
        // En java, il n'existe pas de liste immutable, donc on retourne une liste
        // non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
        // C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
        // nous avons trouvé en java.
        return Collections.unmodifiableList(
                toutesLesCartes.stream().filter(carte -> carte.getEtat() == EtatCarte.DANS_PIOCHE)
                        .collect(Collectors.toList()));
    }

    public List<Carte> getExilees() {
        // En java, il n'existe pas de liste immutable, donc on retourne une liste
        // non modifiable (qui lève une exception si on essaye de la modifier, au runtime)
        // C'est une solution que nous trouvons plutôt mauvaise, mais qui est la seule que
        // nous avons trouvé en java.
        return Collections.unmodifiableList(toutesLesCartes.stream()
                .filter(carte -> carte.getEtat() == EtatCarte.EXILEE).collect(Collectors.toList()));
    }


    @Override
    public void afficher() {
        List<Carte> main = getMain();
        for (int i = 0; i < main.size(); i++) {
            Carte carte = main.get(i);
            carte.setX(250 + i * (Carte.LARGEUR_CARTE + 40));
            carte.setY(20);
            carte.afficher();
        }
    }
}
