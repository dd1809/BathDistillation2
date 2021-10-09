package controller;

import model.*;
import org.example.View;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {

    private final View view;

    public Controller(View view){
        this.view = view;
    }

    private RegimeModel createModel(){
        List<Double> input = view.getUserInputValues();

        RawMaterial rawMaterial = new RawMaterial(input.get(0), input.get(1));

        Column column = new Column(input.get(2), input.get(3), input.get(4), view.getPlatesNumber());

        FlowSystem flowSystem = new FlowSystem(input.get(5),input.get(6), input.get(7));

        RegimeEquations regimeEquations = new RegimeEquations(column, flowSystem, rawMaterial);

        return new RegimeModel(regimeEquations,50);
    }

    public List<Double []> getData(){
     RegimeModel regimeModel = this.createModel();

        return IntStream.range(0, regimeModel.getTime().size())
                .mapToObj(i -> new Double[]{regimeModel.getTime().get(i), regimeModel.getFactor().get(i)})
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
