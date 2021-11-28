public class Complex {
    private double Realpart;
    private double ImaginaryPart;

    //--------------------------------------------------------------
    //                   Constructor
    //--------------------------------------------------------------

    Complex(double pR, double pM){
        Realpart = pR;
        ImaginaryPart = pM;
    }

    //--------------------------------------------------------------
    //                    Getters
    //--------------------------------------------------------------

    public double getRealpart() {
        return Realpart;
    }

    public double getImaginayPart() {
        return ImaginaryPart;
    }

    public Complex getZero(){
        return new Complex(0.0,0.0);
    }

    public Complex getI(){
        return new Complex(0.0, 1.0);
    }

    //--------------------------------------------------------------
    //                    Display
    //--------------------------------------------------------------

    @Override
    public String toString(){
        return Realpart + " + " + ImaginaryPart + "i";
    }

    //--------------------------------------------------------------
    //                    Functions
    //--------------------------------------------------------------

    public Complex add(Complex m ){
        double re = this.Realpart + m.Realpart;
        double im = this.ImaginaryPart + m.ImaginaryPart;
        return new Complex(re,im);
    }

    public Complex subtract(Complex m ){
        double re = this.Realpart - m.Realpart;
        double im = this.ImaginaryPart - m.ImaginaryPart;
        return new Complex(re,im);
    }

    public Complex multiply(Complex m){
        double re = this.ImaginaryPart*m.Realpart + m.ImaginaryPart*this.Realpart;
        double im = this.ImaginaryPart*this.Realpart - m.ImaginaryPart*m.Realpart;
        return new Complex(re,im);
    }

    public Complex divide(Complex n){
        double re = (n.Realpart *this.Realpart + n.ImaginaryPart*this.ImaginaryPart)/(this.Realpart *this.Realpart + this.ImaginaryPart*this.ImaginaryPart);
        double im = (n.Realpart *this.Realpart - n.ImaginaryPart*this.ImaginaryPart)/(this.Realpart *this.Realpart + this.ImaginaryPart*this.ImaginaryPart);
        return new Complex(re,im);
    }

    public double modulus() {
        return Math.sqrt((Realpart * Realpart) + (ImaginaryPart * ImaginaryPart));
    }

    public Complex conjugue() {
        return new Complex(Realpart, -ImaginaryPart);
    }

    public boolean equals (Complex n){
        return n == this;
    }
}
