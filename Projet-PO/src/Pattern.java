import java.util.ArrayList;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Cyclique.class, name = "cyclique"),
    @JsonSubTypes.Type(value = Aleatoire.class, name = "aleatoire"),
    @JsonSubTypes.Type(value = Preparation.class, name = "preparation"),
})
public interface Pattern extends Serializable {
    /**
     * Joue l'action actuellement préparé par le monstre
     */
    public void jouerAction(Monstre lanceur);

    /**
     * Passe à l'action suivante
     */
    public abstract void actionSuivante();

    /**
     * Retourne l'action actuellement préparé par le monstre
     */
    public abstract Action intention();
}
