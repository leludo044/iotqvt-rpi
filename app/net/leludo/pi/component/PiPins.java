package net.leludo.pi.component;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public enum PiPins {
	TWELVE(RaspiPin.GPIO_01);

	private Pin pin;

	PiPins(Pin pin) {
		this.pin = pin;
	}
	
	Pin getPin()  {
		return this.pin;
	}
}
