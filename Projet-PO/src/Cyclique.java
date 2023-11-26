import java.util.ArrayList;

public class Cyclique implements Pattern {
    
    private ArrayList<Action> listeAction;
    private int indice;

    public Cyclique(ArrayList<Action> listAction){
        this.listeAction = listAction;
        this.indice = 0;
    }
    
    public void jouerAction(Monstre lanceur, Entite cible){
        this.actionActuelle().jouerAction(lanceur, cible);
        this.actionSuivante();
    }

    public Action actionActuelle(){

        return this.listeAction.get(this.indice);
    }

    public void actionSuivante(){

        this.indice = ((this.indice+1)%listeAction.size());
    }

    public void afficherIntention(){
        Action actionActuelle = this.actionActuelle();
        System.out.println(actionActuelle);

    }

    @Override
    public String toString() {
        return "Cyclique [listeAction=" + listeAction + ", indice=" + indice + "]";
    }


    
}
