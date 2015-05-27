package net.leludo.iotqvt.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ApplicationProperties {

	/** URL de la websocket du master */
	private String masterWebsocketUrl;

	/** Fréquence d'envoi en ms des relevés des capteurs */
	private long senderFrequence;

	Properties props;

	public ApplicationProperties() {
		props = new Properties();
	}

	public void load(InputStream is) throws IOException {
		props.load(is);
	}

	public Enumeration<Object> elements() {

		return props.elements();
	}

	public String getMasterWebsocketUrl() {
		return masterWebsocketUrl;
	}

	public long getSenderFrequence() {
		return senderFrequence;
	}

}
