package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.view.cargoeditor.CargoTradeFrame;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

public class AddCargoRelay extends SelectionCommand {

    public AddCargoRelay(Configurator configurator) {
        this.configurator = configurator;
    }

    @Override
    public void confirmExec() {
        CargoTradeFrame tradeFrame =  configurator.getCargoTradeFrame();
        CargoType type = tradeFrame.getSelectedType();
        int amount = tradeFrame.getAmount();
        configurator.unitAdded(type, amount);
    }
}
