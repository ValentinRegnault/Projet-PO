package main;

import java.io.File;
import ressources.Config;

/**
 * Représente le héro du jeu.
 */
public class Heros extends Entite {

    private int pointEnergieMax;
    private int pointEnergie;

    public Heros(String nom) {
        // Par défaut, PV = 70
        super(nom, 70, 0, "assets" + File.separator + "pictures" + File.separator + "Ironclad.png",
                0.0, Config.Y_MAX/2.0 - 259.0/2, 356.0, 259.0);
        this.pointEnergie = 3;
        this.pointEnergieMax = 3;
    }

    /**
     * Restaure les points d'énergie du héros à leur valeur maximale.
     */
    public void restaureEnergie() {
        this.pointEnergie = this.pointEnergieMax;
    }

    @Override
    public String toString() {
        return "Hero: [" + super.toString() + " pointEnergieMax=" + pointEnergieMax
                + ", pointEnergie=" + pointEnergie + "]";
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
        if (this.pointEnergie > this.pointEnergieMax) {
            this.pointEnergie = this.pointEnergieMax;
        }
    }
}
