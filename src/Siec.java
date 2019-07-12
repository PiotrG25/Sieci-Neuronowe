import java.util.Arrays;

public class Siec {
    Warstwa [] warstwy;
    int wykonanychTreningow = 0;


    Siec(int wejsc, int [] neuronyWWarstwie){
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



    void trenuj(ZestawTreningowy [] zestawy, int ile){
        System.out.println("Koszt przed: " + kosztDlaZestawuZestawow(zestawy));
        wykonanychTreningow += ile;

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
            zmienWartosciWagIB(zestawy.length);


        }

        System.out.println("Koszt clakowity po " + wykonanychTreningow + " treningach: " + kosztDlaZestawuZestawow(zestawy) + "\n");
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

    void zmienWartosciWagIB(int mianownik){
        for(Warstwa w : warstwy){
            w.zmienWartosciWagIB(mianownik);
        }
    }



    void wydrukujSiec(){
        for(int i = 0; i < warstwy.length; i++){
            System.out.println("Warstwa " + i);
            for(int j = 0; j < warstwy[i].neurony.length; j++){
                System.out.println("\tNeuron " + j + " wagi i b");
                for(int k = 0; k < warstwy[i].neurony[j].wagi.length; k++){
                    System.out.print("\t" + warstwy[i].neurony[j].wagi[k] + ", ");
                }
                System.out.println(warstwy[i].neurony[j].b);
            }
        }
    }
}
