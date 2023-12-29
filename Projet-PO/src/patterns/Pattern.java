package patterns;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import main.Action;
import main.Monstre;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cyclique.class, name = "cyclique"),
        @JsonSubTypes.Type(value = Aleatoire.class, name = "aleatoire"),
        @JsonSubTypes.Type(value = Preparation.class, name = "preparation"),
})
public interface Pattern extends Serializable {
    /**
     * Joue l'action préparée par le monstre
     * 
     * @param lanceur le monstre qui joue l'action
     */
    public void jouerAction(Monstre lanceur);

    /**
     * Prépare une nouvelle action
     */
    public abstract void actionSuivante();

    /**
     * Retourne l'action préparée par le monstre sans la jouer.
     */
    public abstract Action intention();
}
