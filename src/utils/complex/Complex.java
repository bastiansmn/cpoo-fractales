package utils.complex;

import java.util.Arrays;

public final class Complex {

    public static final Complex I = new Complex(0, 1);
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);

    private final double re;
    private final double im;

    //--------------------------------------------------------------
    //                   Constructor
    //--------------------------------------------------------------

    private Complex(double Re, double Im){
        re = Re;
        im = Im;
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

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }


    //--------------------------------------------------------------
    //                    Display
    //--------------------------------------------------------------

    @Override
    public String toString(){
        return re + " + " + im + "i";
    }

    //--------------------------------------------------------------
    //                    Functions
    //--------------------------------------------------------------

    public Complex add(Complex m ){
        double re = this.re + m.re;
        double im = this.im + m.im;
        return new Complex(re,im);
    }

    public Complex sub(Complex m ){
        double re = this.re - m.re;
        double im = this.im - m.im;
        return new Complex(re,im);
    }

    public Complex mult(Complex m){
        double re = this.re *m.re - this.im *m.im;
        double im = this.re *m.im + m.re *this.im;
        return new Complex(re,im);
    }

    public Complex div(Complex n){
        Complex temp = this.mult(n);
        double v = n.re * n.re - n.im * n.im;
        return new Complex(
                temp.getRe() / v,
                temp.getIm() / v
        );
    }

    public Complex pow(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Cannot raise to a negative power");
        if (n == 0)
            return ONE;
        if (n == 1)
            return this;
        return this.mult(this.pow(n-1));
    }

    public double mod() {
        return Math.sqrt((re * re) + (im * im));
    }

    public Complex conj() {
        return new Complex(re, -im);
    }

    public boolean equals (Complex n){
        return n.getIm() == this.getIm() && n.getRe() == this.getRe();
    }

    public static Complex parse(String s) {
        String[] parts = s.split("\\+");
        if (parts.length == 1)
            parts = s.split("-");
        if (parts[0].length() == 0)
            parts = new String[]{("-" + parts[1]), ("-" + parts[2])};
        double re = Double.parseDouble(parts[0]);
        if (!parts[1].contains("i"))
            throw new IllegalArgumentException(String.format("Number %s is not a valid Complex number.", s));
        double im = Double.parseDouble(parts[1].replace("i", ""));
        return Complex.of(re, im);
    }
}
