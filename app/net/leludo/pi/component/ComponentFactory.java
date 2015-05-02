package net.leludo.pi.component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class ComponentFactory {

	static ComponentFactory instance;
	final GpioController gpio;

	private ComponentFactory() {
		gpio = GpioFactory.getInstance();
	}

	public static final ComponentFactory getInstance() {
		if (instance == null)
			instance = new ComponentFactory();
		return instance;
	}

	public Component createComponent(final String type, final String name,
			final PiPins pin) {
		Component component = null;
		if ("led".equals(type)) {
			component = new Led(this, name);
			component.connect(pin);
		}
		return component;
	}

	protected GpioController getGpioController() {
		return gpio;
	}

	public void close() {
		//gpio.shutdown();
	}
}
