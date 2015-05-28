import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Timer;

import javax.websocket.DeploymentException;

import net.leludo.iotqvt.bootstrap.ApplicationProperties;
import net.leludo.iotqvt.bootstrap.ApplicationPropertiesException;
import net.leludo.pi.component.mock.SensorTimerMock;
import net.leludo.pi.timer.SensorWebsocketClient;
import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {

	/** Nom du fichier de propriétés */
	private static final String PROPERTY_FILENAME = "instance.properties";

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

		boolean propertiesOk = false;
		ApplicationProperties ap = new ApplicationProperties();
		try {
			// Recherche du fichier de propriétés
			URL propertyFileurl = app.resource(PROPERTY_FILENAME);
			ap.load(propertyFileurl.openStream());
			propertiesOk = true;
		} catch (ApplicationPropertiesException e) {
			Logger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			for (Iterator<String> i = ap.elements(); i.hasNext();) {
				Logger.info(i.next());
			}
		}

		if (propertiesOk) {
			try {
				// Connection au master
				Logger.info("Connecting to master "
						+ ap.getMasterWebsocketUrl());
				SensorWebsocketClient wsc = new SensorWebsocketClient(new URI(
						ap.getMasterWebsocketUrl()));

				// Déclenchement des relevés
				final Timer t = new Timer("sensor");
				t.schedule(new SensorTimerMock(wsc), 0, ap.getSenderFrequence());
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
	}

	@Override
	public void onStop(Application arg0) {
		Logger.info("Application has stopped.");
	}

}
