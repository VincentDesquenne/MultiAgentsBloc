import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class Environnement {

    private HashMap<Integer, ArrayList<Bloc>> hashMap = new HashMap<>();
    private ArrayList<Bloc> solutionFinale = new ArrayList<>();
    private int position;


    public Environnement(ArrayList<Bloc> mesBlocs) {
        ArrayList<Integer> mesEntiers = new ArrayList<>();
        Collections.shuffle(mesBlocs);
        hashMap.put(0, mesBlocs);
        hashMap.put(1, new ArrayList<>());
        hashMap.put(2, new ArrayList<>());
        Collections.shuffle(mesBlocs);
        solutionFinale = (ArrayList<Bloc>) mesBlocs.clone();



    }

    @Override
    public String toString() {
        for (Map.Entry<Integer, ArrayList<Bloc>> mapentry : hashMap.entrySet()) {
            System.out.println("POSITION : " + mapentry.getKey());
            for (int i = 0; i < mapentry.getValue().size(); i++) {
                System.out.println("|| " + mapentry.getValue().get(i).getNom() + " ||");
            }
        }

        return "END";
    }
}
