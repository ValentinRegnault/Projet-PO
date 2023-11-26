public class Hero extends Entite {
    
    private int pointEnergieMax;
    private int pointEnergie;

    public Hero(String nom){

        // Par défaut, PV = 70
        super(nom, 70,  0);
        this.pointEnergie = 3;
        this.pointEnergieMax = 3;
    }

    @Override
    public String toString() {
        return "Hero: [" + super.toString() +  " pointEnergieMax=" + pointEnergieMax + ", pointEnergie=" + pointEnergie + "]";
    }

    public int getPointEnergieMax() {
        return pointEnergieMax;
    }

    public void setPointEnergieMax(int pointEnergieMax) {
        this.pointEnergieMax = pointEnergieMax;
    }

    public int getPointEnergie() {
        return pointEnergie;
    }

    public void setPointEnergie(int pointEnergie) {
        this.pointEnergie = pointEnergie;
    }
    
    public void restaureEnergie(){
        this.pointEnergie = this.pointEnergieMax;
    }
    
}
