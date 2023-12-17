public class SalleRepos extends Salle {
    
    public void soin(){
        hero.setPv((int)Math.round(hero.getPvMax()*0.3));
    }

    public boolean jouerSalle(){
        System.out.println("Vous entrez dans une salle de repos...");
        System.out.println("Ouf, vous avez eu chaud, vous vous reposez un peu et regagnez 30% de vos PV");
        soin();
        System.out.println("Vous avez maintenant " + hero.getPv() + " PV");
        return true;
    }
}
