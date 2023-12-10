import java.util.ArrayList;

public class Cyclique implements Pattern {
    
    private ArrayList<Action> listeAction;
    private int indice;

    public Cyclique(){
        this.listeAction = new ArrayList<Action>();
        this.indice = 0;
    }

    public Cyclique(ArrayList<Action> listAction){
        this.listeAction = listAction;
        this.indice = 0;
    }
    
    public void jouerAction(Monstre lanceur, ArrayList<Entite> listeCible){
        this.actionActuelle().jouerAction(lanceur, listeCible);
        this.actionSuivante();
    }

    public Action actionActuelle(){

        return this.listeAction.get(this.indice);
    }

    public void actionSuivante(){

        this.indice = ((this.indice+1)%listeAction.size());
    }

    public void afficherIntention(){
        System.out.println(this.actionActuelle());

    }

    @Override
    public String toString() {
        return "Cyclique [listeAction=" + listeAction + ", indice=" + indice + "]";
    }

    public String genererIntention(){
        return this.actionActuelle().toString();
    }


    public ArrayList<Action> getListeAction() {
        return listeAction;
    }

    public void setListeAction(ArrayList<Action> listeAction) {
        this.listeAction = listeAction;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
