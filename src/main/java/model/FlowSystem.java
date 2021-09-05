package model;

public class FlowSystem {

    private final double liquidFlow;
    private final double inputFlow;
    private final double outputFlow;

    public FlowSystem(double liquidFlow, double inputFlow, double outputFlow) {
        this.liquidFlow = liquidFlow;
        this.inputFlow = inputFlow;
        this.outputFlow = outputFlow;
    }

    public double getInputFlow() {return inputFlow;}

    public double getOutputFlow() {return outputFlow;}

    public double getLiquidFlow() {return liquidFlow;}

    public double getInputFlowFraction() {return inputFlow / liquidFlow;}

    public double getOutputFlowFraction() {return outputFlow / liquidFlow;}

    public double getVaporFlow() {return liquidFlow + outputFlow - inputFlow;}

    public double getPotHoldUpAugment() {return inputFlow - outputFlow;}
}

