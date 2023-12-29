package main;

import java.util.ArrayList;

import effets.Effet;

/**
 * Représente une action effectuée par un monstre, qui peut contenir plusieurs
 * effets.
 * 
 * @see Effet
 */
public class Action implements java.io.Serializable {
    private String nom;
    private ArrayList<Effet> effets;

    public Action() {
        this.nom = "";
        this.effets = new ArrayList<Effet>();
    }

    /**
     * @param nom    Nom de l'action
     * @param effets Effets de l'action
     */
    public Action(String nom, ArrayList<Effet> effets) {
        this.nom = nom;
        this.effets = effets;
    }

    /**
     * Effectue l'action. Applique chacun des effets de l'action.
     * 
     * @param lanceur Monstre qui effectue l'action
     * @apiNote Cette méthode est appelée par
     *          {@link patterns.Pattern#jouerAction(Monstre)}
     */
    public void jouerAction(Monstre lanceur) {
        for (Effet e : this.effets) {
            ArrayList<Entite> cibles = new ArrayList<Entite>();

            switch (e.getTypeCible()) {
                case AUCUN:
                    break;
                case HERO:
                    cibles.add(Partie.getPartie().getHero());
                    break;
                case TOUS_LES_MONSTRES:
                    cibles.addAll(Partie.getPartie().getEquipeMonstreActuelle());
                    break;
                case MONSTRE_ALEATOIRE:
                    cibles.add(Partie.getPartie().getEquipeMonstreActuelle()
                            .get((int) (Math.random() * Partie.getPartie().getEquipeMonstreActuelle().size())));
                    break;
                case LANCEUR:
                    cibles.add(lanceur);
                    break;
                case SELECTION_JOUEUR:
                    throw new IllegalStateException(
                            "Un monstre contient un pattern qui contient des actions. Ces actions ne peuvent pas cibler un monstre choisi par le joueur, or c'est le cas ici.");
                default:
                    break;
            }

            e.appliquerEffet(lanceur, cibles);
        }
    }

    @Override
    public String toString() {
        return "Action [effets=" + effets + ", nom=" + nom + "]";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Effet> getEffets() {
        return effets;
    }

    public void setEffets(ArrayList<Effet> effets) {
        this.effets = effets;
    }

}
