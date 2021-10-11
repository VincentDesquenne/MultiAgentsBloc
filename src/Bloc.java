public class Bloc {
    private Environnement environnement;
    private String nom;
    private Bloc blocDessus;
    private Bloc blocDessous;

    public Bloc(String nom) {
        this.nom = nom;
    }

    public void seDeplacer(int x){
        int index = 0;
        for(int i=0; i<environnement.getHashMap().size(); i++){
            if(environnement.getHashMap().get(i).contains(this.getNom())){
                index = environnement.getHashMap().get(i).indexOf(this.getNom());
                //environnement.getHashMap().get(i).set(index, null);
                if(this.getBlocDessous() != null){
                    Bloc auDessous = this.getBlocDessous();
                    auDessous.setBlocDessus(null);
                }
                environnement.getHashMap().get(i).remove(index);
                //System.out.println(this);
            }
        }
        environnement.getHashMap().get(x).add(this.getNom());
        index = environnement.getHashMap().get(x).indexOf(this.getNom());
        if (index != 0) {
            Bloc auDessous = environnement.findBlocByName(environnement.getHashMap().get(x).get(index-1));
            auDessous.setBlocDessus(this);
            this.setBlocDessous(auDessous);
        }

    }

    public void pousser() {
        if(blocDessus != null){
            environnement.perception(blocDessus);
        }
    }

    public String getNom() {
        return nom;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                ", nom='" + nom + '\'' +
                '}';
    }

    public Bloc getBlocDessus() {
        return blocDessus;
    }

    public void setBlocDessus(Bloc blocDessus) {
        this.blocDessus = blocDessus;
    }

    public Bloc getBlocDessous() {
        return blocDessous;
    }

    public void setBlocDessous(Bloc blocDessous) {
        this.blocDessous = blocDessous;
    }
}
