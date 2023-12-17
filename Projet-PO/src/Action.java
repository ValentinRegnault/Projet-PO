import java.util.Arrays;
import java.util.ArrayList;

public class Action implements java.io.Serializable {

    private String nom;
    private ArrayList<Effet> effets;

    public Action() {
        this.nom = "";
        this.effets = new ArrayList<Effet>();
    }

    public Action(String nom, ArrayList<Effet> effets) {
        this.nom = nom;
        this.effets = effets;
    }

    public void jouerAction(Monstre lanceur) {

        for(Effet e : this.effets){
            ArrayList<Entite> cibles = new ArrayList<Entite>();
            
            switch (e.getTypeCible()) {
                case AUCUN:
                    break;
                case HERO:
                    cibles.add(Partie.partie.getHero());
                    break;
                case TOUS_LES_MONSTRES:
                    cibles.addAll(Partie.partie.getEquipeMonstreActuelle());
                    break;
                case MONSTRE_ALEATOIRE:
                    cibles.add(Partie.partie.getEquipeMonstreActuelle().get((int) (Math.random() * Partie.partie.getEquipeMonstreActuelle().size())));
                    break;
                case LANCEUR:
                    cibles.add(lanceur);
                    break;
                case SELECTION_JOUEUR:
                    throw new IllegalStateException("Un monstre contient un pattern qui contient des actions. Ces actions ne peuvent pas cibler un monstre choisi par le joueur, or c'est le cas ici.");
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
