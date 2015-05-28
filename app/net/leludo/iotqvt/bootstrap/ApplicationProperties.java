package net.leludo.iotqvt.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Gestion des propriétés de l'application
 *
 */
public class ApplicationProperties {

	/** URL de la websocket du master */
	private String masterWebsocketUrl;

	/** Fréquence d'envoi en ms des relevés des capteurs */
	private long senderFrequence;

	/** Les propriétés brutes extraites du fichier */
	Properties props;

	/**
	 * Constructeur
	 */
	public ApplicationProperties() {
		props = new Properties();
	}

	/**
	 * Chargement des propriétés
	 * 
	 * @param is
	 *            Le fichier de propriétés
	 * @throws IOException
	 *             Exception levée en cas d'erreur d'accès au fichier
	 * @throws ApplicationPropertiesException
	 *             Exception levée en cas d'incoherence dans le fichier
	 *             (propriété manquante, mauvais format, etc.)
	 */
	public void load(InputStream is) throws IOException,
			ApplicationPropertiesException {
		props.load(is);

		// Initialisation et analyse des propriétés
		masterWebsocketUrl = props.getProperty("master.websocket.url");
		if (masterWebsocketUrl == null) {
			throw new ApplicationPropertiesException(
					"Property master.websocket.url not found !");
		}

		if (null == props.getProperty("cdec.sender.frequence")) {
			throw new ApplicationPropertiesException(
					"Property cdec.sender.frequence not found !");
		} else {
			try {
				senderFrequence = Long.parseLong(props
						.getProperty("cdec.sender.frequence"));
			} catch (NumberFormatException nfe) {
				throw new ApplicationPropertiesException(
						"Property cdec.sender.frequence is not a decimal value !");
			}
		}
	}

	/**
	 * Retourne un itérateur sur la liste des propriétés formaté clé=valeur
	 * @return Un itérateur sur la liste des propriétés formaté clé=valeur
	 */
	public Iterator<String> elements() {
		List<String> nativeProps = new ArrayList<String>();
		for (Enumeration<Object> e = props.keys(); e.hasMoreElements();) {
			String propKey = (String) e.nextElement();
			nativeProps.add(propKey + "=" + props.getProperty(propKey));
		}

		return nativeProps.iterator();
	}

	/**
	 * Retourne l'url de la websocket du master
	 * @return L'url de la websocket du master
	 */
	public String getMasterWebsocketUrl() {
		return masterWebsocketUrl;
	}

	/**
	 * Retourne la fréquence d'envoi en ms des relevés des capteurs 
	 * @return La fréquence d'envoi en ms des relevés des capteurs
	 */
	public long getSenderFrequence() {
		return senderFrequence;
	}

}
