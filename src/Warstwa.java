public class Warstwa {
    Neuron [] neurony;


    Warstwa(int wejsc, int neuronow){
        neurony = new Neuron[neuronow];
        for(int i = 0; i < neuronow; i++){
            double [] tab = new double [wejsc];
            for(int j = 0; j < tab.length; j++){
                tab[j] = 0;
            }
            neurony[i] = new Neuron(tab, 0);
        }
    }
    //todo Konstruktor warstwy przyjmujacy zadane wartosci wagi i wyrazow wolnych


    double [] wektorWyjscia(double [] wejscia){
        double [] result = new double [neurony.length];
        for(int i = 0; i < neurony.length; i++){
            result[i] = neurony[i].wyjscie(wejscia);
        }
        return result;
    }


    void obliczPochodneGlowne(double [] wejsciaWarstwy, double [] oczekiwaneWyjscia){
        for(int i = 0; i < neurony.length; i++){
            neurony[i].pochodnaGlowna(wejsciaWarstwy, oczekiwaneWyjscia[i]);
        }
    }

    void obliczPochodneZwykle(double [] wejsciaWarstwy, Warstwa warstwaNaPrawo){
        double [] tabPochodnychPoB = new double [warstwaNaPrawo.neurony.length];
        for(int i = 0; i < tabPochodnychPoB.length; i++){
            tabPochodnychPoB[i] = warstwaNaPrawo.neurony[i].pochodnaPoB;
        }

        for(int n = 0; n < neurony.length; n++){
            double [] wagiLaczace = new double [warstwaNaPrawo.neurony.length];
            for(int i = 0; i < wagiLaczace.length; i++){//Dla kazdego neuronu z warstwy na prawo wybieramy wagi laczace jej neurony z neuronem na ktorym wywolujemy pochodna zwykla
                wagiLaczace[i] = warstwaNaPrawo.neurony[i].wagi[n];
            }

            neurony[n].pochodnaZwykla(wejsciaWarstwy, tabPochodnychPoB, wagiLaczace);
        }
    }


    void dodajPochodneDoSum(){
        for(Neuron n : neurony){
            n.dodajPochodneDoSum();
        }
    }

    void wyzerujPochodneISumy(){
        for(Neuron n : neurony){
            n.wyzerujPochodneISumy();
        }
    }

    void zmienWartosciWagIB(int mianownik){
        for(Neuron n : neurony){
            n.zmienWartosciWagIB(mianownik);
        }
    }
}
