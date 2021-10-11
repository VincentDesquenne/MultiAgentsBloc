import java.util.ArrayList;

public class Main {
    public static int endIteration = 50;

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

        System.out.println(env);


    }
}
