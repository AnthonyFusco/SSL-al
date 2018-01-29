package kernel.generator;

import kernel.Application;
import kernel.structural.Sensor;

public class CodeGenerator extends Visitor<StringBuilder> {

	public CodeGenerator() {
		this.result = new StringBuilder();
	}

	private void w(String s) {
		result.append(String.format("%s\n",s));
	}

	@Override
	public void visit(Sensor sensor) {

	}

	@Override
	public void visit(Application app) {
		w("// Wiring code generated from an ArduinoML model");
		w(String.format("// Application name: %s\n", app.getName()));

		app.getLaws().forEach(law -> w("law " + law.getName() + " declared"));
		app.getSensorsLots().forEach(sensorsLot -> w("sensor " + sensorsLot.getName() + " declared"));
	}

}
