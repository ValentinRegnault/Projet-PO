import java.util.HashMap;

public abstract class Entite{

    enum Status {
        Force,
        Faiblesse,
        Vulnérable,
        Fragie
    }
    protected String nom;
    protected int pvMax;
    protected int pv;
    protected int pointBlocage;
    protected HashMap<Status,Integer> status;

    public Entite(String nom, int pvMax, int pointBlocage){
        this.nom = nom;
        this.pvMax = pvMax;
        this.pointBlocage = pointBlocage;
    }

    @Override
    public String toString() {
        return "nom=" + nom + ", pvMax=" + pvMax + ", pv=" + pv + ", pointBlocage=" + pointBlocage + ", status="
                + status;
    }

    public int getPvMax() {
        return pvMax;
    }

    public void setPvMax(int pvMax) {
        this.pvMax = pvMax;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getPointBlocage() {
        return pointBlocage;
    }

    public void setPointBlocage(int pointBlocage) {
        this.pointBlocage = pointBlocage;
        if(this.pointBlocage < 0){
            this.pointBlocage = 0;
        }
    }


    

    

    
}   