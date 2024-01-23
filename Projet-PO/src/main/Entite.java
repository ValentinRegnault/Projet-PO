package main;

import java.util.Map.Entry;
import ressources.Affichage;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;


/**
 * Classe abstraite représentant une entité du jeu. Les monstres et le héro sont des entités.
 */
public abstract class Entite extends Sprite {
    public enum Status {
        FORCE, FAIBLESSE, VULNERABLE, FRAGILE, RITUEL
    }

    protected String nom;
    protected int pvMax;
    protected int pv;
    protected int pointBlocage = 0;
    protected Map<Status, Integer> status;

    protected Entite() {
        super("", 0.0, 0.0, 0.0, 0.0);
        this.nom = "";
        this.pvMax = 0;
        this.pv = 0;
        this.pointBlocage = 0;

        this.status = new TreeMap<>();
        for (Status s : Status.values()) {
            this.status.put(s, 0);
        }

    }

    protected Entite(String nom, int pvMax, int pointBlocage, String imageFilePath, double x,
            double y, double width, double height) {
        super(imageFilePath, x, y, width, height);
        this.nom = nom;
        this.pvMax = pvMax;
        this.pv = pvMax;
        this.pointBlocage = pointBlocage;

        this.status = new TreeMap<>();
        for (Status s : Status.values()) {
            this.status.put(s, 0);
        }
    }

    @Override
    public void afficher() {
        super.afficher();
        Affichage.texteCentre(getX() + getWidth() / 2, getY(),
                pv + "/" + pvMax + " PV" + " Blocage : " + pointBlocage);

        ArrayList<Status> statusAffiches = new ArrayList<>();
        for (Entry<Status, Integer> s : this.status.entrySet()) {
            if (s.getValue() > 0) {
                statusAffiches.add(s.getKey());
            }
        }

        for (int i = 0; i < statusAffiches.size(); i++) {
            Affichage.texteGauche(getX() + getWidth() / 2 + 20, getY() - 20 * (i + 1),
                    this.status.get(statusAffiches.get(i)).toString());
            Affichage.image(getX() + getWidth() / 2 - 20, getX() + getWidth() / 2 + 20,
                    getY() - 40 * (i + 1), getY() - 40 * i,
                    statusToImagePath(statusAffiches.get(i)));
        }
    }

    @Override
    public String toString() {
        return "nom=" + nom + ", pvMax=" + pvMax + ", pv=" + pv + ", pointBlocage=" + pointBlocage;
    }

    /**
     * Retourne le chemin vers l'image associé au statut.
     * 
     * @param status le statut
     * @return le chemin vers l'image associé au statut
     */
    private static String statusToImagePath(Status status) {
        String debutChemin = "assets" + File.separator + "pictures" + File.separator + "statuts";

        switch (status) {
            case FORCE:
                return debutChemin + File.separator + "Force.png";
            case FAIBLESSE:
                return debutChemin + File.separator + "Faiblesse.png";
            case VULNERABLE:
                return debutChemin + File.separator + "Vulnérable.png";
            case FRAGILE:
                return debutChemin + File.separator + "Fragile.png";
            case RITUEL:
                return debutChemin + File.separator + "Rituel.png";
            default:
                throw new IllegalArgumentException(
                        "Un statuts n'a pas d'image associé : " + status);
        }
    }

    /**
     * Affiche les status de l'entité.
     * 
     * @category Affichage
     */
    public void afficheStatus() {
        for (Entry<Status, Integer> s : this.status.entrySet()) {
            if (s.getValue() > 0) {
                System.out.print(s.getKey() + " : " + s.getValue() + " ");
            }
        }
        System.out.println();
    }

    public void setStatusPoint(Status s, Integer point) {
        this.status.replace(s, point);

    }

    public Integer getStatusPoint(Status s) {
        return this.status.get(s);
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
        if (this.pv > this.pvMax) {
            this.pv = this.pvMax;
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

    public Map<Status, Integer> getStatus() {
        return status;
    }

    public void setStatus(Map<Status, Integer> status) {
        this.status = status;
    }

}
