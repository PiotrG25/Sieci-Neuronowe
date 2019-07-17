
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ZestawTreningowy [] zestawy = new ZestawTreningowy [1];
        int [] _1 = {1};
        Siec siec = new Siec(2, _1);
/*
        //Zestawy treningowe dla sieci symulujacej bramę logiczna AND
        double [] _00 = {0, 0}, _01 = {0, 1}, _10 = {1, 0}, _11 = {1, 1}, _0 = {0}, _1 = {1};
        zestawy = {new ZestawTreningowy(_11, _1), new ZestawTreningowy(_10, _0), new ZestawTreningowy(_01, _0), new ZestawTreningowy(_00, _0)}; // Dla sieci 2-1
        zestawy = {new ZestawTreningowy(_11, _10), new ZestawTreningowy(_10, _01), new ZestawTreningowy(_01, _01), new ZestawTreningowy(_00, _01)}; // Dla siecie 2-4-2

        int [] neuronyWWarstwie = {2};
        siec = new Siec(2, neuronyWWarstwie);//siec 2-2
        int [] neuronyWWarstwie = {4, 2};
        siec = new Siec(2, neuronyWWarstwie);//siec 2-4-2

        for(int i = 0; i < 10; i++){
            siec.trenuj(zestawy, 100);
        }
*/



        String menu = "" +
                "1 - menu\n" +
                "2 - stworz siec\n" +
//                "3 - podaj wagi sieci\n" +
                "4 - podaj zestawy treningowe\n" +
//                "5 - zmien zestawy treningowe\n" +
//                "6 - dodaj zestaw treningowy\n" +
                "7 - trenuj\n" +
                "8 - wyjscie dla podanego wejscia\n" +
                "9 - drukuj siec\n" +
                "10 - zapisz do pliku siec i zestawy treningowe\n" +
//                "11 - wygeneruj z pliku siec i zestawy treningowe\n" +
                "" +
                "" +
                "0 - koniec\n" +
                "";
        byte b;
        boolean kontynuuj = true;
        System.out.println(menu);
        do{

            System.out.println("Twoj wybor(1 - menu): ");
            b = sc.nextByte();

            switch (b){
                case 1:
                    System.out.println(menu);
                    break;
                case 2:
                    System.out.print("Podaj ilosc wejsc: ");
                    int wejsc = sc.nextInt();
                    System.out.print("Podaj ilosc warstw: ");
                    int [] neuronowWWarstwie = new int [sc.nextInt()];
                    for(int i = 0; i < neuronowWWarstwie.length; i++){
                        System.out.print("Podaj ilosc neuronow w " + i + " warstwie: ");
                        neuronowWWarstwie[i] = sc.nextInt();
                    }
                    siec = new Siec(wejsc, neuronowWWarstwie);
                    break;
                case 3:
                    //todo podaj wagi sieci
                    break;
                case 4:
                    System.out.println("Podaj ilosc zestawow treningowych: ");
                    zestawy = new ZestawTreningowy [sc.nextInt()];
                    for(int i = 0; i < zestawy.length; i++){
                        System.out.println("Podaj kolejne " + siec.wejsc + " wejscia sieci: ");
                        double [] wejscia = new double [siec.wejsc];
                        for(int j = 0; j < wejscia.length; j++){
                            wejscia[j] = sc.nextDouble();
                        }
                        System.out.println("Podaj kolejne " + siec.wyjsc + " oczekiwane wyjscia sieci: ");
                        double [] wyjscia = new double [siec.wyjsc];
                        for(int j = 0; j < wyjscia.length; j++){
                            wyjscia[j] = sc.nextDouble();
                        }
                        zestawy[i] = new ZestawTreningowy(wejscia, wyjscia);
                    }
                    break;
                case 5:
                    // todo zmien zestawy treningowe
                    break;
                case 6:
                    //todo dodaj zestaw treningowy
                    break;
                case 7:
                    if(zestawy[0].wejscia.length != siec.wejsc){
                        System.err.println("Nie mozna wykonac treningu");
                        System.err.println("ilosc wejsc zestawu treningowego: " + zestawy[0].wejscia.length);
                        System.err.println("ilosc wejsc sieci: " + siec.wejsc);
                        break;
                    }
                    if(zestawy[0].oczekiwaneWyjscia.length != siec.wyjsc){
                        System.err.println("Nie mozna wykonac treningu");
                        System.err.println("ilosc wyjsc zestawu treningowego: " + zestawy[0].oczekiwaneWyjscia.length);
                        System.err.println("ilosc wyjsc sieci: " + siec.wyjsc);
                        break;
                    }
                    System.out.print("Podaj ile razy wykonać trening: ");
                    int ile = sc.nextInt();
                    System.out.println("Kost przed: " + siec.kosztDlaZestawuZestawow(zestawy));
                    siec.trenuj(zestawy, ile);
                    System.out.println("Koszt po: " + siec.kosztDlaZestawuZestawow(zestawy));
                    break;
                case 8:
                    System.out.println("Podaj kolejne " + siec.wejsc + " wejscia sieci: ");
                    double [] wejscia = new double [siec.wejsc];
                    for(int i = 0; i < wejscia.length; i++){
                        wejscia[i] = sc.nextDouble();
                    }
                    double [] wektorWyjscia = siec.wyjscieSieci(wejscia);
                    System.out.println("Wyjscie sieci: ");
                    for(int i = 0; i < wektorWyjscia.length; i++){
                        System.out.println(wektorWyjscia[i]);
                    }
                    break;
                case 9:
                    siec.wydrukujSiec();
                    break;
                case 10:
                    System.out.println("Podaj nazwe pliku do ktorego zapisac");
                    try {
                        siec.zapiszDoPliku(sc.next(), zestawy);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 11:
                    //todo wygeneruj z pliku
                    break;
                case 0:
                    kontynuuj = false;
                    break;
            }

        }while(kontynuuj);
    }
}
