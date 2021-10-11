import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static int endIteration = 5;

    public static void main(String[] args) {
        Bloc blocA = new Bloc("A");
        Bloc blocB = new Bloc("B");
        Bloc blocC = new Bloc("C");
        Bloc blocD = new Bloc("D");

        ArrayList<Bloc> mesBlocs = new ArrayList<>();
        mesBlocs.add(blocA);
        mesBlocs.add(blocB);
        mesBlocs.add(blocC);
        mesBlocs.add(blocD);
        Environnement env = new Environnement(mesBlocs);
        boolean end = false;

        Random rand = new Random();
        int nbAleatoire = 0;
        Bloc blocSelect;
        while(!end && endIteration != 0){
            System.out.println(env);
            nbAleatoire = rand.nextInt(4);
            blocSelect = mesBlocs.get(nbAleatoire);
            env.perception(blocSelect);
            end = env.estTermine();
            endIteration--;
        }

    }
}
