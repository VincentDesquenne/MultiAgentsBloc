import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.*;

public class Environnement {

    private HashMap<Integer, Stack<Bloc>> hashMap = new HashMap<>();
    private Stack<Bloc> solutionFinale = new Stack<>();
    private int position;


    public Environnement(ArrayList<Bloc> mesBlocs) {
        ArrayList<String> blocsName = new ArrayList<>();
        Stack<Bloc> pile = new Stack<>();
        ArrayList<Integer> mesEntiers = new ArrayList<>();
        Collections.shuffle(mesBlocs);
        for (int i = 0; i< 4; i ++) {
            mesBlocs.get(i).setEnvironnement(this);
            /*if (i == 0) {
                mesBlocs.get(i).setBlocDessous(null);
                mesBlocs.get(i).setBlocDessus(mesBlocs.get(i+1));
            } else if (i == 3) {
                mesBlocs.get(i).setBlocDessous(mesBlocs.get(i-1));
                mesBlocs.get(i).setBlocDessus(null);
            }
            else {
                mesBlocs.get(i).setBlocDessous(mesBlocs.get(i-1));
                mesBlocs.get(i).setBlocDessus(mesBlocs.get(i+1));
            }*/
            blocsName.add(mesBlocs.get(i).getNom());
            pile.add(mesBlocs.get(i));
        }
        hashMap.put(0, pile);
        hashMap.put(1, new Stack<>());
        hashMap.put(2, new Stack<>());
        solutionFinale.add(new Bloc("A"));
        solutionFinale.add(new Bloc("B"));
        solutionFinale.add(new Bloc("C"));
        solutionFinale.add(new Bloc("D"));
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
        if (indexBloc == hashMap.get(indexHashMap).size()-1) { // si le bloc est tout en haut d'une pile de 4, on le déplace
            while (nbAleatoire == indexHashMap) {
                nbAleatoire = rand.nextInt(3); // tire un des deux autres emplacements possibles
            }
            bloc.seDeplacer(nbAleatoire);
        } else if (hashMap.get(indexHashMap).get(indexBloc + 1) != null && this.needToBeMove(bloc, indexHashMap, indexBloc)) { // si le bloc n'est pas tout en haut, et l'environnement lui dit qu'il doit bouger
            this.perception(hashMap.get(indexHashMap).get(indexBloc + 1)); // correspond à notre pousser
        } else {//if (this.needToBeMove(bloc, indexHashMap, indexBloc)){
            while (nbAleatoire == indexHashMap) {
                nbAleatoire = rand.nextInt(3);
            }
            bloc.seDeplacer(nbAleatoire); // le bloc est en haut d'une pile d'une taille inférieure à 4
        }
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

    public boolean needToBeMove(Bloc bloc, int indexHashMap, int indexStack) {
        Bloc blocAuDessus = null;
        Bloc blocAuDessous = null;
        for (int i=0; i < 4; i ++) {
            if (this.solutionFinale.get(i).getNom() == bloc.getNom()) {
                if (i == 0) {
                    blocAuDessus = solutionFinale.get(i+1);
                    blocAuDessous = null;
                } else if (i == 3) {
                    blocAuDessus = null;
                    blocAuDessous = solutionFinale.get(i - 1);
                } else {
                    blocAuDessus = solutionFinale.get(i + 1);
                    blocAuDessous = solutionFinale.get(i - 1);
                }
            }
        }
        if (indexStack == 0 && blocAuDessous == null
                && this.hashMap.get(indexHashMap).get(indexStack + 1) == null) { // cas pour la lettre tout en bas
            return false;
        }
        if (blocAuDessus == this.hashMap.get(indexHashMap).get(indexStack + 1)
                && blocAuDessous == this.hashMap.get(indexHashMap).get(indexStack - 1)) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        for (Map.Entry<Integer, Stack<Bloc>> mapentry : hashMap.entrySet()) {
            System.out.println("POSITION : " + mapentry.getKey());
            for (int i = 0; i < mapentry.getValue().size(); i++) {
                System.out.println("|| " + mapentry.getValue().get(mapentry.getValue().size()-i-1) + " ||");
            }
        }

        return "END \n -------------------------------------------------------";
    }

    public HashMap<Integer, Stack<Bloc>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<Integer, Stack<Bloc>> hashMap) {
        this.hashMap = hashMap;
    }

    public Stack<Bloc> getSolutionFinale() {
        return solutionFinale;
    }

    public void setSolutionFinale(Stack<Bloc> solutionFinale) {
        this.solutionFinale = solutionFinale;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
