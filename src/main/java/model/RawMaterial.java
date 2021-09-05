package model;

public class RawMaterial {

    private final double inputConcentration;
    private final double alpha;

    public RawMaterial(double inputConcentration, double alpha) {
        this.inputConcentration = inputConcentration;
        this.alpha = alpha;
    }

    public double getInputConcentration() {return inputConcentration;}

    public double getAlpha() {return alpha;}
}
