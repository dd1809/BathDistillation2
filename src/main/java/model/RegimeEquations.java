package model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class RegimeEquations implements FirstOrderDifferentialEquations {

    private final Column column;
    private final FlowSystem flowSystem;
    private final RawMaterial rawMaterial;

    public RegimeEquations(Column column, FlowSystem flowSystem, RawMaterial rawMaterial){
        this.column = column;
        this.flowSystem = flowSystem;
        this.rawMaterial = rawMaterial;
    }

    public double getInputConcentration() {return rawMaterial.getInputConcentration();}
    public double getAlpha(){return rawMaterial.getAlpha();}

    @Override
    public int getDimension() {return column.getPlatesNumber() + 2;}

    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) {

        double BPot = flowSystem.getVaporFlow() * rawMaterial.getAlpha()/ (column.getPotHoldUp() + flowSystem.getPotHoldUpAugment() * t);
        double CPot = flowSystem.getLiquidFlow() / (column.getPotHoldUp() + flowSystem.getPotHoldUpAugment() * t);

        double A = flowSystem.getVaporFlow() * rawMaterial.getAlpha() / column.getPlateHoldUp();
        double C = flowSystem.getLiquidFlow() / column.getPlateHoldUp();
        double B = A + C;

        double ACond = flowSystem.getVaporFlow() * rawMaterial.getAlpha() / column.getCondenserHoldUp();
        double BCond = (flowSystem.getLiquidFlow() + flowSystem.getOutputFlow()) / column.getCondenserHoldUp();
        double DCond = flowSystem.getInputFlow() * rawMaterial.getInputConcentration() / column.getCondenserHoldUp();

        yDot[0] = -BPot * y[0] + CPot * y[1];

        for (int i = 1; i <= column.getPlatesNumber(); i++){
            yDot[i] = A * y[i-1] - B * y[i] + C * y[i + 1];
        }
        
        yDot[column.getPlatesNumber() + 1] = ACond * y[column.getPlatesNumber()] - BCond * y[column.getPlatesNumber() + 1] + DCond;
    }
}
