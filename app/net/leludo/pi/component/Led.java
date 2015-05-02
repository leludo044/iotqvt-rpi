package net.leludo.pi.component;

public class Led extends Component {

	protected Led(ComponentFactory manager, String name) {
		super(manager, name);
	}

	public void on() {
		pin.high();
	}

	public void off() {
		pin.low();
	}
	
	public void blink(final int nb, final int seconds) throws InterruptedException {
		for (int i = 0; i<nb; i++) {
			this.on() ;
			Thread.sleep(seconds*1000);
			this.off() ;
			Thread.sleep(seconds*1000);
		}
	}

	public boolean isOn() {
		return pin.isHigh() ;
	}

	public String getState() {
		return isOn() ? "on" : "off";
	}

}
