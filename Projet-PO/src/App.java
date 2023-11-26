import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        
        Hero Bob = new Hero("Bob");
        System.out.println(Bob);

        Inflige inflige = new Inflige(6);
        Blocage blocage = new Blocage(5);
        ArrayList<Effet> effetAttaque = new ArrayList<>();
        effetAttaque.add(inflige);

        ArrayList<Effet> effetBlocage = new ArrayList<>();
        effetBlocage.add(blocage);
        

        Carte attaque = new Carte("Frappe", Carte.Rarete.Commun, 1, new ArrayList<Effet>(), effetAttaque, false );
        Carte defense = new Carte("Défense", Carte.Rarete.Commun, 1, effetBlocage, new ArrayList<Effet>(), false);





    }
}
