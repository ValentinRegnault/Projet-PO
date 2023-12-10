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
})
public interface Pattern extends Serializable {
    public void jouerAction(Monstre lanceur, ArrayList<Entite> listeCible);

    public abstract void actionSuivante();

    public abstract void afficherIntention();

    public abstract String genererIntention();
}
