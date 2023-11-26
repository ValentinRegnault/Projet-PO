public class Inflige extends Effet {
    
    public Inflige(int pointEffet){
        super(pointEffet);
    }
    
    public void appliquerEffet(Entite cible){

        // Inflige prends en compte les points de blocage

        int pointBlocageCible = cible.getPointBlocage();

        cible.setPointBlocage(pointBlocageCible - this.pointEffet);

        int degatInflige = Math.abs(pointBlocageCible - this.pointEffet);

        cible.setPv(cible.getPv() - degatInflige);
    }

    @Override
    public String toString() {
        return "Inflige " + this.pointEffet;
    }

    
}

