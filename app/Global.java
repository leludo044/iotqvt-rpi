import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Timer;

import javax.websocket.DeploymentException;

import net.leludo.iotqvt.bootstrap.ApplicationProperties;
import net.leludo.pi.component.mock.SensorTimerMock;
import net.leludo.pi.timer.SensorWebsocketClient;
import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {

	/** Nom du fichier de propriétés */
	private static final String PROPERTY_FILENAME = "instance.properties";

	/** URL de la websocket du master */
	private String masterWebsocketUrl;

	/** Fréquence d'envoi en ms des relevés des capteurs */
	private long senderFrequence;

	/**
	 * Sur événement de démarrage de l'application
	 * 
	 * @param app
	 *            l'application en cours de démarrage
	 */
	@Override
	public void onStart(Application app) {

		Logger.info("Starting application ("
				+ (app.isDev() ? "mode DEV" : "Mode PROD") + ")...");

		try {
			// Recherche du fichier de propriétés
			URL propertyFileurl = app.resource(PROPERTY_FILENAME);
			
			// Chargement des propriétés
			Logger.info("Loading properties from " + propertyFileurl.getFile()
					+ "...");
			Properties props = this
					.loadProperties(propertyFileurl.openStream());

			// Log des propriétés
			for (Enumeration<?> prop = props.propertyNames(); prop
					.hasMoreElements();) {
				String propKey = (String) prop.nextElement();
				Logger.info(propKey + "=" + props.getProperty(propKey));
			}

			// Initialisation et analyse des propriétés
			masterWebsocketUrl = props.getProperty("master.websocket.url");
			if (masterWebsocketUrl == null) {
				Logger.error("Property master.websocket.url not found !");
			}

			if (null == props.getProperty("cdec.sender.frequence")) {
				Logger.error("Property cdec.sender.frequence not found !");
			} else {
				try {
					senderFrequence = Long.parseLong(props
							.getProperty("cdec.sender.frequence"));
				} catch (NumberFormatException nfe) {
					Logger.error("Property cdec.sender.frequence is not a decimal value !");
				}
			}
			Logger.info("Properties loaded.");

			// Connection au master
			Logger.info("Connecting to master " + masterWebsocketUrl);
			SensorWebsocketClient wsc = new SensorWebsocketClient(new URI(
					"ws://iotqvt-master.herokuapp.com/websocket/mesure"));
			// new URI("ws://localhost:8081/websocket/mesure"));

			// Déclenchement des relevés
			final Timer t = new Timer("sensor");
			t.schedule(new SensorTimerMock(wsc), 0, senderFrequence);
			Logger.info("Connected to master.");

			// wsc.sendMessage("{\"id\":\"Ludo\",\"temp\":24687,\"date\":1432054547595}");
			// OK
			Logger.info("Application started.");
		} catch (DeploymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStop(Application arg0) {
		Logger.info("Application has stopped.");
	}

	/**
	 * Chargement des propriétés
	 * 
	 * @param is
	 *            Le fichier des propriétés sous forme de stream
	 * @return Le jeu de propriétés extraites
	 * @throws IOException
	 *             Exception levée en cas d'erreur de lecture des propriétés
	 */
	private Properties loadProperties(InputStream is) throws IOException {
		Properties props = new Properties();
		props.load(is);
		return props;
	}

}
