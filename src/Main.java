
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        //Zestawy treningowe dla sieci symulujacej bramę logiczna AND
        double [] _00 = {0, 0}, _01 = {0, 1}, _10 = {1, 0}, _11 = {1, 1}, _0 = {0}, _1 = {1};
//        ZestawTreningowy [] zestawy = {new ZestawTreningowy(_11, _1), new ZestawTreningowy(_10, _0), new ZestawTreningowy(_01, _0), new ZestawTreningowy(_00, _0)}; // Dla sieci 2-1
        ZestawTreningowy [] zestawy = {new ZestawTreningowy(_11, _10), new ZestawTreningowy(_10, _01), new ZestawTreningowy(_01, _01), new ZestawTreningowy(_00, _01)}; // Dla siecie 2-4-2

        int [] neuronyWWarstwie = {2};
        Siec siec = new Siec(2, neuronyWWarstwie);//siec 2-2
//        int [] neuronyWWarstwie = {4, 2};
//        Siec siec = new Siec(2, neuronyWWarstwie);//siec 2-4-2

        for(int i = 0; i < 10; i++){
            siec.trenuj(zestawy, 100);
        }

        /*
        boolean kontynuuj = true;
        char c;

        do{
            System.out.print("Kontynuować?(t/n): ");
            c = sc.next().charAt(0);
            switch (c){
                case 't':
                    siec.trenuj(zestawy, 100);
                break;
                case 'n':
                    kontynuuj = false;
                break;
            }
        }while(kontynuuj);
        */

        /*
        int i = 0;
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_00)[i]);
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_01)[i]);
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_10)[i]);
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_11)[i]);

        i = 1;
        System.out.println();
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_00)[i]);
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_01)[i]);
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_10)[i]);
        System.out.println("wyjscie dla zestawow wejsciowych: " + siec.wyjscieSieci(_11)[i]);
        */

//        siec.wydrukujSiec();
    }
}
