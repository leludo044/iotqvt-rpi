
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;

import play.Application;
import play.GlobalSettings;

import javax.websocket.*;

import net.leludo.pi.component.mock.SensorTimerMock;
import net.leludo.pi.timer.SensorWebsocketClient;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application arg0) {
		System.out.println("Application has started");
		try {
			SensorWebsocketClient wsc = new SensorWebsocketClient(new URI(
					"ws://localhost:8081/websocket/mesure"));
			final Timer t = new Timer("sensor");
			t.schedule(new SensorTimerMock(wsc), 0, 5000);
//			wsc.sendMessage("{\"id\":\"Ludo\",\"temp\":24687,\"date\":1432054547595}");
		} catch (DeploymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStop(Application arg0) {
		System.out.println("Application has stopped");
	}

}
