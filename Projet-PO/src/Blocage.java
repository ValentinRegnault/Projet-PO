public class Blocage extends Effet {


    public Blocage(int pointEffet){
        super(pointEffet);
    }

    public void appliquerEffet(Entite cible){

        cible.setPointBlocage(cible.getPointBlocage()+ pointEffet);



    }

    @Override
    public String toString() {
        return "Blocage " + this.pointEffet;
    }

    
    
}
