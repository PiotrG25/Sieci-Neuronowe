public class Neuron {
    public static double sigmoid(double x){
        return 1 / (1 + Math.pow(2.71828, - x));
    }
    public static double sigmoidPrim(double x){
        double eDoMinusX = Math.pow(2.71828, -x);
        return eDoMinusX / Math.pow(1 + eDoMinusX, 2);
    }


    double [] wagi;
    double b;
    double [] pochodnePoWagach;
    double pochodnaPoB;
    double [] sumyPochodnychPoWagach;
    double sumaPochodnychPoB;


    Neuron(double [] wagi, double b){
        this.wagi = wagi;
        this.b = b;
        pochodnePoWagach = new double[wagi.length];
        sumyPochodnychPoWagach = new double[wagi.length];
    }


    double wyjscie(double [] wejscia){
        double x = b;
        for(int i = 0; i < wejscia.length; i++){
            x += wagi[i] * wejscia[i];
        }
        return sigmoid(x);
    }


    void pochodnaGlowna(double [] wejsciaNeuronu, double y){
        double x = b;
        for(int i = 0; i < wejsciaNeuronu.length; i++){
            x += wagi[i] * wejsciaNeuronu[i];
        }

        pochodnaPoB = 2 * (sigmoid(x) - y) * sigmoidPrim(x);
        for(int i = 0; i < wagi.length; i++){
            pochodnePoWagach[i] = pochodnaPoB * wejsciaNeuronu[i];
        }
    }

    void pochodnaZwykla(double [] wejsciaNeuronu, double [] pochodne_po_b, double [] wagi_laczace){
        double suma = 0;
        for(int i = 0; i < pochodne_po_b.length; i++){
            suma += pochodne_po_b[i] * wagi_laczace[i];
        }

        double x = b;
        for(int i = 0; i < wejsciaNeuronu.length; i++){
            x += wagi[i] * wejsciaNeuronu[i];
        }

        pochodnaPoB = suma * sigmoidPrim(x);
        for(int i = 0; i < wagi.length; i++){
            pochodnePoWagach[i] = pochodnaPoB * wejsciaNeuronu[i];
        }
    }


    void dodajPochodneDoSum(){
        for(int i = 0; i < wagi.length; i++){
            sumyPochodnychPoWagach[i] += pochodnePoWagach[i];
        }
        sumaPochodnychPoB += pochodnaPoB;
    }

    void wyzerujPochodneISumy(){
        for(int i = 0; i < pochodnePoWagach.length; i++){
            pochodnePoWagach[i] = 0;
            sumyPochodnychPoWagach[i] = 0;
        }
        pochodnaPoB = 0;
        sumaPochodnychPoB = 0;
    }

    void zmienWartosciWagIB(int mianownik){
        for(int i = 0; i < wagi.length; i++){
            wagi[i] -= sumyPochodnychPoWagach[i] / mianownik;
        }
        b -= sumaPochodnychPoB / mianownik;
    }
}
