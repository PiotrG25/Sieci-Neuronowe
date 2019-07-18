import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Siec {
    int wejsc;
    int wyjsc;
    Warstwa [] warstwy;


    Siec(int wejsc, int [] neuronowWWarstwie){
        this.wejsc = wejsc;
        this.wyjsc = neuronowWWarstwie[neuronowWWarstwie.length - 1];

        ustawWarstwy(neuronowWWarstwie);
    }

    void ustawWarstwy(int [] neuronyWWarstwie){
        warstwy = new Warstwa[neuronyWWarstwie.length];
        warstwy[0] = new Warstwa(wejsc, neuronyWWarstwie[0]);
        for(int i = 1; i < warstwy.length; i++){
            warstwy[i] = new Warstwa(neuronyWWarstwie[i - 1], neuronyWWarstwie[i]);
        }
    }

    double [] wyjscieSieci(double [] wejscia){
        double [] wyjscie = warstwy[0].wektorWyjscia(wejscia);
        for(int i = 1; i < warstwy.length; i++){
            wyjscie = warstwy[i].wektorWyjscia(wyjscie);
        }
        return wyjscie;
    }


    double kosztDlaZestawu(ZestawTreningowy zestaw){
        double [] wyjscie = wyjscieSieci(zestaw.wejscia);
        double koszt = 0;
        for(int i = 0; i < wyjscie.length;i++){
            koszt += Math.pow(wyjscie[i] - zestaw.oczekiwaneWyjscia[i], 2);
        }
        return koszt;
    }
    double kosztDlaZestawuZestawow(ZestawTreningowy [] zestawy){
        double koszt = 0;
        for(int i = 0; i < zestawy.length; i++){
            koszt += kosztDlaZestawu(zestawy[i]);
        }
        return koszt;
    }



    void trenuj(ZestawTreningowy [] zestawy, int ile, double dlKroku){

        for(; ile > 0; ile--){

            wyzerujPochodneISumy();

            //obliczanie kazdej pochodnej kazdego neuronu dla kazdego zestawy testowego
            for(int t = 0; t < zestawy.length; t++){

                double [][] warstwy_wyjscia = new double [warstwy.length][];
                znajdzWyjsciaWarstw(zestawy[t].wejscia, warstwy_wyjscia);

                int warstw = warstwy.length;
                if(warstw == 1){
                    warstwy[0].obliczPochodneGlowne(zestawy[t].wejscia, zestawy[t].oczekiwaneWyjscia);
                }else{
                    warstwy[warstw - 1].obliczPochodneGlowne(warstwy_wyjscia[warstw - 2], zestawy[t].oczekiwaneWyjscia);//przedostatnie wyjscie jest wejsciem ostatniej warstwy
                    for(int w = warstw - 2; w > 0; w--){//Iterujemy od przedostatniego do zapierwszego
                        warstwy[w].obliczPochodneZwykle(warstwy_wyjscia[w - 1], warstwy[w + 1]);//przekazujemy wyjscia warstyw poprzedniej i warstwe nastepna
                    }
                    warstwy[0].obliczPochodneZwykle(zestawy[t].wejscia, warstwy[1]);
                }

                //sumowanie pochodnych wszystkich testow
                dodajPochodneDoTablicSumPochodnychWagIB();
            }

            //zmiana zmiennej
            zmienWartosciWagIB(dlKroku, zestawy.length);
        }
    }


    void znajdzWyjsciaWarstw(double [] wejscia, double [][] warstwy_wyjscia){
        warstwy_wyjscia[0] = warstwy[0].wektorWyjscia(wejscia);
        for(int i = 1; i < warstwy.length; i++){
            warstwy_wyjscia[i] = warstwy[i].wektorWyjscia(warstwy_wyjscia[i - 1]);
        }
    }

    void dodajPochodneDoTablicSumPochodnychWagIB(){
        for(Warstwa w : warstwy){
            w.dodajPochodneDoSum();
        }
    }

    void wyzerujPochodneISumy(){
        for(Warstwa w : warstwy){
            w.wyzerujPochodneISumy();
        }
    }

    void zmienWartosciWagIB(double dlKroku, int mianownik){
        for(Warstwa w : warstwy){
            w.zmienWartosciWagIB(dlKroku, mianownik);
        }
    }


    void zapiszDoPliku(String nazwa, ZestawTreningowy [] zestawy)throws IOException {
        FileWriter fw = new FileWriter("sieci/" + nazwa);
        String newLine = System.lineSeparator();

        //struktura sieci
        fw.write("" + warstwy.length);
        fw.write(newLine);
        fw.write("" + wejsc);
        for(int i = 0; i < warstwy.length; i++){
            fw.write(" " +warstwy[i].neurony.length);
        }
        fw.write(newLine);
        fw.write(newLine);
        fw.write(newLine);

        //Warstwy Wagi b
        for(int i = 0; i < warstwy.length; i++){
            for(int j = 0; j < warstwy[i].neurony.length; j++){
                for(int k = 0; k < warstwy[i].neurony[j].wagi.length; k++){
                    fw.write(warstwy[i].neurony[j].wagi[k] + newLine);
                }
                fw.write(warstwy[i].neurony[j].b + newLine);
                fw.write(newLine);
            }
        }
        fw.write(newLine);

        //Zestawy treningowe
        fw.write("" + zestawy.length);
        fw.write(newLine);
        for(int i = 0; i < zestawy.length; i++){
            for(int j = 0; j < zestawy[i].wejscia.length; j++){
                fw.write(zestawy[i].wejscia[j] + " ");
            }
            for(int j = 0; j < zestawy[i].oczekiwaneWyjscia.length; j++){
                fw.write(zestawy[i].oczekiwaneWyjscia[j] + " ");
            }
            fw.write(newLine);
        }

        fw.close();
    }
    ZestawTreningowy [] generujZPliku(String nazwa)throws IOException{
        File plik = new File("sieci/" + nazwa);
        Scanner fr = new Scanner(plik);

        //Struktura sieci
        int [] neuronowWWarstwie = new int [Integer.parseInt(fr.next())];


        int wejsc = Integer.parseInt(fr.next());
        for(int i = 0; i < neuronowWWarstwie.length; i++){
            neuronowWWarstwie[i] = Integer.parseInt(fr.next());
        }

        //konstruktor
        this.wejsc = wejsc;
        this.wyjsc = neuronowWWarstwie[neuronowWWarstwie.length - 1];
        ustawWarstwy(neuronowWWarstwie);

        //wagi i b-y
        for(int i = 0; i < warstwy.length; i++){
            for(int j = 0; j < warstwy[i].neurony.length; j++){
                for(int k = 0; k < warstwy[i].neurony[j].wagi.length; k++){
                    warstwy[i].neurony[j].wagi[k] = Double.parseDouble(fr.next());
                }
                warstwy[i].neurony[j].b = Double.parseDouble(fr.next());
            }
        }

        //Zestawy treningowe
        ZestawTreningowy [] zestawy = new ZestawTreningowy[Integer.parseInt(fr.next())];
        for(int i = 0; i < zestawy.length; i++){
            zestawy[i] = new ZestawTreningowy(new double[this.wejsc], new double[this.wyjsc]);
        }
        for(int i = 0; i < zestawy.length; i++){
            for(int j = 0; j < this.wejsc; j++){
                zestawy[i].wejscia[j] = Double.parseDouble(fr.next());
            }
            for(int j = 0; j < this.wyjsc; j++){
                zestawy[i].oczekiwaneWyjscia[j] = Double.parseDouble(fr.next());
            }
        }

        return zestawy;
    }

    void wydrukujSiec(){
        for(int i = 0; i < warstwy.length; i++){
            System.out.println("Warstwa " + i);
            for(int j = 0; j < warstwy[i].neurony.length; j++){
                System.out.println("\tNeuron " + j + ": ");
                System.out.println("\t\twagi: ");
                for(int k = 0; k < warstwy[i].neurony[j].wagi.length; k++){
                    System.out.println(String.format("\t\t\t%10.5f", warstwy[i].neurony[j].wagi[k]));
                }
                System.out.println("\t\tb: ");
                System.out.println(String.format("\t\t\t%10.5f",warstwy[i].neurony[j].b));
            }
        }
    }
}
