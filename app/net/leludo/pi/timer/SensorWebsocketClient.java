package net.leludo.pi.timer;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import play.Logger;

@ClientEndpoint
public class SensorWebsocketClient {

	Session session = null;

	public SensorWebsocketClient(URI endpointURI) throws DeploymentException,
			IOException {
		WebSocketContainer container = ContainerProvider
				.getWebSocketContainer();
		container.connectToServer(this, endpointURI);
	}

	@OnOpen
	public void onOpen(Session session) {
		Logger.info("Client endpoint open");
		this.session = session;
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		Logger.info("Client endpoint close");
		this.session = null;
	}

	@OnError
	public void onError(Throwable t) {
		Logger.info("Client endpoint error ", t);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		Logger.info("message recu=" + message);
		// JsonObject jsonObject = Json.createReader(new
		// StringReader(message)).readObject();
		// String nom = jsonObject.getString("nom");
		// String valeur = jsonObject.getString("valeur");
		// LOGGER.log(Level.INFO, "nom="+nom+" valeur="+valeur);
	}

	public void sendMessage(String message) {
		if (session != null) {
			this.session.getAsyncRemote().sendText(message);
		}
	}
}