public class Bloc {
    private final String nom;
    private Environnement environnement;
    private int priorite;
    private Bloc blocDessous;
    private boolean isSatisfied;
    private boolean isPushed;

    public Bloc(String nom, Bloc blocDessous) {
        this.nom = nom;
        this.priorite = 0;
        this.blocDessous = blocDessous;
        this.isSatisfied = false;
        this.isPushed = false;
    }

    public void seDeplacer(int x) {
        int index = 0;
        for (int i = 0; i < environnement.getHashMap().size(); i++) {
            if (environnement.getHashMap().get(i).contains(this)) {
                index = environnement.getHashMap().get(i).indexOf(this);
                environnement.getHashMap().get(i).remove(index);
                environnement.getHashMap().get(x).add(this);
                break;
            }
        }
    }

    public void perception() {
        System.out.println("C'est au tour de " + this.getNom());
        this.environnement.needToBeMove(this);
        if (this.isPushed()) { // si le bloc est poussée
            if (this.environnement.canMove(this)) {
                int nbAleatoire = this.environnement.getAleatoirePosition(this); // si il est tout en haut, il se déplace
                this.setPushed(false); // du coup il n'est plus poussé
                this.seDeplacer(nbAleatoire); // il se déplace aléatoirement
            } else {
                Bloc blocAuDessus = this.environnement.getBlocAuDessus(this);
                this.pousser(blocAuDessus); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        } else if (!this.isSatisfied()) { // si le bloc n'est pas poussé, mais n'est pas satisfait
            if (this.environnement.canMove(this)) { // il est tout en haut, il peut bouger
                int nbAleatoire = this.environnement.getAleatoirePosition(this); // si il est tout en haut, il se déplace
                this.seDeplacer(nbAleatoire);
            } else { // il ne peut pas bouger, il pousse
                Bloc blocAuDessus = this.environnement.getBlocAuDessus(this);
                this.pousser(blocAuDessus); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        }
    }

    public void perception2() {
        System.out.println("C'est au tour de " + this.getNom());
        this.environnement.needToBeMove(this);
        if (this.isPushed()) { // si le bloc est poussée
            if (this.environnement.canMove(this)) {
                int nextPosition = this.environnement.calculerBestMove(this); // si il est tout en haut, il se déplace
                this.setPushed(false); // du coup il n'est plus poussé
                this.seDeplacer(nextPosition); // il se déplace aléatoirement
            } else {
                Bloc blocAuDessus = this.environnement.getBlocAuDessus(this);
                this.pousser2(blocAuDessus); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        } else if (!this.isSatisfied()) { // si le bloc n'est pas poussé, mais n'est pas satisfait
            if (this.environnement.canMove(this)) { // il est tout en haut, il peut bouger
                int nextPosition = this.environnement.calculerBestMove(this); // si il est tout en haut, il se déplace
                this.seDeplacer(nextPosition);
                this.environnement.needToBeMove(this);
                if (this.isSatisfied) {
                    this.setPriorite(0);
                }
            } else { // il ne peut pas bouger, il pousse
                Bloc blocAuDessus = this.environnement.getBlocAuDessus(this);
                this.pousser2(blocAuDessus); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        } else {
            this.setPriorite(0);
            Bloc blocAuDessus = this.environnement.getBlocAuDessus(this);
            Bloc blocAuDessus2 = this.environnement.getBlocAuDessus(blocAuDessus);
            Bloc blocAuDessus3 = this.environnement.getBlocAuDessus(blocAuDessus2);
            this.pousser2(blocAuDessus3);
            this.pousser2(blocAuDessus2);
            this.pousser2(blocAuDessus);
        }

    }

    public void pousser(Bloc bloc) {
        bloc.setPushed(true);
        bloc.perception();
    }

    public void pousser2(Bloc bloc) {
        bloc.setPushed(true);
        bloc.perception2();
    }

    public boolean isPushed() {
        return this.isPushed;
    }

    public void setPushed(boolean pushed) {
        this.isPushed = pushed;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public void setSatisfied(boolean satisfied) {
        isSatisfied = satisfied;
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

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public Bloc getBlocDessous() {
        return blocDessous;
    }

    public void setBlocDessous(Bloc blocDessous) {
        this.blocDessous = blocDessous;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                ", nom='" + nom + '\'' +
                ", Priorite ='" + priorite + '\'' +
                '}';
    }
}
