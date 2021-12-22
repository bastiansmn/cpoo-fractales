package utils;

public final class Complex {
    private double Realpart;
    private double ImaginaryPart;

    //--------------------------------------------------------------
    //                   Constructor
    //--------------------------------------------------------------

    private Complex(double Re, double Im){
        Realpart = Re;
        ImaginaryPart = Im;
    }

    //--------------------------------------------------------------
    //                  Static fabric
    //--------------------------------------------------------------
    public static Complex of(double Re, double Im) {
        return new Complex(Re, Im);
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
        double re = this.Realpart*m.Realpart - this.ImaginaryPart*m.ImaginaryPart;
        double im = this.Realpart*m.ImaginaryPart + m.Realpart*this.ImaginaryPart;
        return new Complex(re,im);
    }

    public Complex divide(Complex n){
        Complex temp = this.multiply(n);
        return new Complex(
                temp.getRealpart() / (n.Realpart*n.Realpart - n.ImaginaryPart*n.ImaginaryPart),
                temp.getImaginayPart() / (n.Realpart*n.Realpart - n.ImaginaryPart*n.ImaginaryPart)
        );
    }

    public Complex pow(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot raise to a negative power");
        }
        if (n == 0) {
            return new Complex(1, 0);
        }
        if (n == 1) {
            return this;
        }
        return this.multiply(this.pow(n-1));
    }

    public double mod() {
        return Math.sqrt((Realpart * Realpart) + (ImaginaryPart * ImaginaryPart));
    }

    public Complex conjugue() {
        return new Complex(Realpart, -ImaginaryPart);
    }

    public boolean equals (Complex n){
        return n == this;
    }
}
