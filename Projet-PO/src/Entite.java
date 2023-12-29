import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Optional;

public abstract class Entite {
    enum Status {
        Force,
        Faiblesse,
        Vulnérable,
        Fragile,
        Rituel
    }

    protected String nom;
    protected int pvMax;
    protected int pv;
    protected int pointBlocage = 0;
    protected TreeMap<Status, Integer> status;

    public Entite() {
        this.nom = "";
        this.pvMax = 0;
        this.pv = 0;
        this.pointBlocage = 0;

        this.status = new TreeMap<>();
        this.status.put(Status.Faiblesse, 0);
        this.status.put(Status.Force, 0);
        this.status.put(Status.Vulnérable, 0);
        this.status.put(Status.Fragile, 0);
        this.status.put(Status.Rituel, 0);
    }

    public Entite(String nom, int pvMax, int pointBlocage) {
        this.nom = nom;
        this.pvMax = pvMax;
        this.pv = pvMax;
        this.pointBlocage = pointBlocage;

        this.status = new TreeMap<>();
        this.status.put(Status.Faiblesse, 0);
        this.status.put(Status.Force, 0);
        this.status.put(Status.Vulnérable, 0);
        this.status.put(Status.Fragile, 0);
    }

    @Override
    public String toString() {
        return "nom=" + nom + ", pvMax=" + pvMax + ", pv=" + pv + ", pointBlocage=" + pointBlocage;
    }

    public void setStatusPoint(Status s, Integer point) {
        this.status.replace(s, point);

    }

    public Integer getStatusPoint(Status s) {
        return this.status.get(s);
    }

    public void afficheStatus() {
        for (Entry<Status, Integer> s : this.status.entrySet()) {
            if (s.getValue() > 0) {
                System.out.print(s.getKey() + " : " + s.getValue() + " ");
            }
        }
        System.out.println();
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

    public String getNom() {
        return nom;
    }

    public void setPv(int pv) {
        this.pv = pv;
        if (this.pv < 0) {
            this.pv = 0;
        }
    }

    public int getPointBlocage() {
        return pointBlocage;
    }

    public void setPointBlocage(int pointBlocage) {
        this.pointBlocage = pointBlocage;
        if (this.pointBlocage < 0) {
            this.pointBlocage = 0;
        }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TreeMap<Status, Integer> getStatus() {
        return status;
    }

    public void setStatus(TreeMap<Status, Integer> status) {
        this.status = status;
    }

}