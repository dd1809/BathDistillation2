package model;

public class Column {

    private final double columnHoldUp;
    private final double potHoldUp;
    private final double condenserHoldUp;
    private final int platesNumber;

    public Column(double potHoldUp, double columnHoldUp, double condenserHoldUp, int platesNumber) {
        this.potHoldUp = potHoldUp;
        this.columnHoldUp = columnHoldUp;
        this.condenserHoldUp = condenserHoldUp;
        this.platesNumber = platesNumber;
    }

    public double getPotHoldUp() {return potHoldUp;}

    public double getCondenserHoldUp() {return condenserHoldUp;}

    public int getPlatesNumber() {return platesNumber;}

    public double getPlateHoldUp() {return columnHoldUp / platesNumber;}
}
