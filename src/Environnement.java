import java.util.*;

public class Environnement {

    private final HashMap<Integer, Stack<Bloc>> hashMap = new HashMap<>();
    private final Stack<Bloc> solutionFinale = new Stack<>();
    private ArrayList<Bloc> mesBlocs = new ArrayList<>();


    public Environnement(ArrayList<Bloc> mesBlocs) {
        ArrayList<String> blocsName = new ArrayList<>();
        Stack<Bloc> pile = new Stack<>();
        solutionFinale.add(new Bloc("A", null));
        solutionFinale.add(new Bloc("B", null));
        solutionFinale.add(new Bloc("C", null));
        solutionFinale.add(new Bloc("D", null));
        do {
            Collections.shuffle(mesBlocs);
        } while (this.estTermine());
        for (int i = 0; i < 4; i++) {
            mesBlocs.get(i).setEnvironnement(this);
            blocsName.add(mesBlocs.get(i).getNom());
            pile.add(mesBlocs.get(i));
        }
        hashMap.put(0, pile);
        hashMap.put(1, new Stack<>());
        hashMap.put(2, new Stack<>());

        this.mesBlocs = mesBlocs;
    }

    public void communicationAgent() {
        Bloc blocAvant = null;
        for (int i = 0; i < 4; i++) {
            if (this.getHashMap().get(0).get(i).getBlocDessous() == null) {
                blocAvant = this.getHashMap().get(0).get(i);
                this.getHashMap().get(0).get(i).setPriorite(4);
            }
        }
        int compteur = 3;
        while (compteur > 0) {
            for (int i = 0; i < 4; i++) {
                if (this.getHashMap().get(0).get(i).getBlocDessous() == blocAvant) {
                    blocAvant = this.getHashMap().get(0).get(i);
                    this.getHashMap().get(0).get(i).setPriorite(compteur);
                    break;
                }
            }
            compteur = compteur - 1;
        }
    }

    public void perception(Bloc bloc) {
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc); //regarde position du bloc (tout en haut ou tout en bas par exemple)
        Random rand = new Random();
        int nbAleatoire = indexHashMap;
        this.needToBeMove(bloc, indexHashMap, indexBloc); // regarde s'il peut bouger
        if (bloc.isPushed()) { // si le bloc est poussée
            if (indexBloc + 1 == this.hashMap.get(indexHashMap).size()) { // si il est tout en haut, il se déplace
                while (nbAleatoire == indexHashMap) {
                    nbAleatoire = rand.nextInt(3); // tire un des deux autres emplacements possibles
                }
                bloc.setPushed(false); // du coup il n'est plus poussé
                bloc.seDeplacer(nbAleatoire); // il se déplace aléatoirement
            } else {
                bloc.pousser(hashMap.get(indexHashMap).get(indexBloc + 1)); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        } else if (!bloc.isSatisfied()) { // si le bloc n'est pas poussé, mais n'est pas satisfait
            if (indexBloc + 1 == this.hashMap.get(indexHashMap).size()) { // il est tout en haut, il peut bouger
                while (nbAleatoire == indexHashMap) {
                    nbAleatoire = rand.nextInt(3); // tire un des deux autres emplacements possibles
                }
                bloc.seDeplacer(nbAleatoire);
            } else { // il ne peut pas bouger, il pousse
                bloc.pousser(hashMap.get(indexHashMap).get(indexBloc + 1));
            }
        }
    }

    public void perception2(Bloc bloc) {
        System.out.println("C'est au tour du bloc " + bloc.getNom());
        int indexHashMap = 0;
        for (int i = 0; i < hashMap.size(); i++) {
            if (hashMap.get(i).contains(bloc)) {
                indexHashMap = i; // regarde sur quel emplacement est le bloc (1, 2 ou 3)
                break;
            }
        }
        int indexBloc = hashMap.get(indexHashMap).indexOf(bloc); //regarde position du bloc (tout en haut ou tout en bas par exemple)
        this.needToBeMove(bloc, indexHashMap, indexBloc); // regarde s'il peut bouger
        int nextPosition = this.calculerBestMove(bloc, indexHashMap, indexBloc);
        if (bloc.isPushed()) { // si le bloc est poussée
            if (indexBloc + 1 == this.hashMap.get(indexHashMap).size()) { // si il est tout en haut, il se déplace
                bloc.setPushed(false); // du coup il n'est plus poussé
                bloc.seDeplacer2(nextPosition);// il se déplace aléatoirement
            } else {
                bloc.pousser2(hashMap.get(indexHashMap).get(indexBloc + 1)); // si il est poussé, mais ne peut pas se déplacer, il pousse
            }
        } else if (!bloc.isSatisfied()) { // si le bloc n'est pas poussé, mais n'est pas satisfait
            if (indexBloc + 1 == this.hashMap.get(indexHashMap).size()) { // il est tout en haut, il peut bouger
                bloc.seDeplacer2(nextPosition);
                this.needToBeMove(bloc, nextPosition, this.getHashMap().get(nextPosition).indexOf(bloc)); // regarde si le bloc est satisfait ou non
                if (bloc.isSatisfied()) { // le bloc vient d'être bien placé
                    bloc.setPriorite(0); // sa priorité devient donc 0, il n'a plus a bougé
                }
            } else { // il ne peut pas bouger, il pousse
                bloc.pousser2(hashMap.get(indexHashMap).get(indexBloc + 1));
            }
        } else { // Le premier bloc est bien placé, il faut pousser ceux du haut du coup, -> QUESTION 2
            bloc.setPriorite(0);
            bloc.pousser2(this.getHashMap().get(0).get(3));
            bloc.pousser2(this.getHashMap().get(0).get(2));
            bloc.pousser2(this.getHashMap().get(0).get(1));

        }

    }

    public int calculerBestMove(Bloc bloc, int indexHashMap, int indexStack) {
        Random rand = new Random();
        int bestPosition = indexHashMap;

        if (bloc.getBlocDessous() == null) {
            for (int i = 0; i < 3; i++) { // si doit être le premier bloc, va chercher une place seul d'abord
                if (i != indexHashMap) {
                    if (this.hashMap.get(i).size() == 0) return i;
                }
            }
        } else { // n'est pas le premier bloc
            for (int i = 0; i < 3; i++) { // va chercher à se mettre au dessus du bon bloc
                if (i != indexHashMap) {
                    if (this.hashMap.get(i).size() != 0 && this.hashMap.get(i).peek() == bloc.getBlocDessous())
                        return i;
                }
            }
        }

        if (mesBlocs.stream().max(Comparator.comparingInt(Bloc::getPriorite)).get().getPriorite() == 4) { // cad que le premier n'a pas été placé, il lui faut une place de libre, -> QUESTION 2
            for (int i = 0; i < 3; i++) { // si n'est pas arrivé, va chercher à se mettre sur un bloc pas seul pour éviter de gêner le premier
                if (i != indexHashMap) {
                    if (this.hashMap.get(i).size() != 0) return i;
                }
            }
        }

        for (int i = 0; i < 3; i++) { // si n'est pas arrivé, va chercher à se mettre sur un bloc pas seul pour éviter de gêner le premier
            if (i != indexHashMap) {
                if (this.hashMap.get(i).size() == 0) return i;
            }
        }

        for (int i = 0; i < 3; i++) { // aucun place qui correspond, aucune place de libre, va se mettre pour ne pas embêter premier bloc, -> QUESTION 2
            if (i != indexHashMap) {
                if (this.hashMap.get(i).size() != 0 && this.hashMap.get(i).get(0).getPriorite() != 0) return i;
            }
        }

        while (bestPosition == indexHashMap) { // sinon tire au hasard car les deux positions sont mauvaises, donc l'une ou l'autre fera l'affaire
            bestPosition = rand.nextInt(3); // tire un des deux autres emplacements possibles, car aucun n'est un move meilleur que l'autre
        }
        return bestPosition;
    }

    public boolean estTermine() {
        for (Map.Entry<Integer, Stack<Bloc>> mapentry : hashMap.entrySet()) {
            if (mapentry.getValue().size() == 4) {
                for (int i = 0; i < mapentry.getValue().size(); i++) {
                    if (mapentry.getValue().get(i).getNom() != solutionFinale.get(i).getNom()) return false;
                }
                return true;
            }
        }
        return false;
    }

    public void needToBeMove(Bloc bloc, int indexHashMap, int indexStack) { // met à jour si le bloc est bien placé ou non
        if (indexStack == 0) {
            bloc.setSatisfied(bloc.getBlocDessous() == null);
        } else bloc.setSatisfied(bloc.getBlocDessous() == this.hashMap.get(indexHashMap).get(indexStack - 1));
    }


    @Override
    public String toString() {
        for (Map.Entry<Integer, Stack<Bloc>> mapentry : hashMap.entrySet()) {
            System.out.println("POSITION : " + mapentry.getKey());
            for (int i = 0; i < mapentry.getValue().size(); i++) {
                System.out.println("|| " + mapentry.getValue().get(mapentry.getValue().size() - i - 1) + " ||");
            }
        }

        return "END \n -------------------------------------------------------";
    }

    public HashMap<Integer, Stack<Bloc>> getHashMap() {
        return hashMap;
    }
}
