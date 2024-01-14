package main;


import java.io.File;
import ressources.Affichage;

/**
 * Sprite est une classe Affichable qui affiche une image, avec certaines dimensions.
 */
public class Sprite extends Affichable {
    private String imageFilePath;
    private double width;
    private double height;

    public Sprite(String imageFilePath, double x, double y, double width, double height) {
        super(x, y);
        setImageFilePath(imageFilePath);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void afficher() {
        Affichage.image(getX(), getX() + getWidth(), getY(), getY() + getHeight(), imageFilePath);
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath =
                imageFilePath.replace("/", File.separator).replace("\\", File.separator);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width < 0) {
            throw new IllegalArgumentException("width ne peut pas être inférieur à 0");
        }

        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height < 0) {
            throw new IllegalArgumentException("height ne peut pas être inférieur à 0");
        }

        this.height = height;
    }


}
