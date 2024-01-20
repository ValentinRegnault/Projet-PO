package main;

import java.util.ArrayList;

import effets.Effet;

/**
 * Représente une action effectuée par un monstre, qui peut contenir plusieurs effets.
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
     * @param nom Nom de l'action
     * @param effets Effets de l'action
     */
    public Action(String nom, ArrayList<Effet> effets) {
        this.nom = nom;
        this.effets = effets;
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
