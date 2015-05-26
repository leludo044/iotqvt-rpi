package net.leludo.pi.timer;

import java.util.Timer;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

public class SensorWebSocket extends WebSocket<String> {

	@Override
	public void onReady(WebSocket.In<String> arg0,
			WebSocket.Out<String> arg1) {
		
		final Timer t = new Timer("sensor");
		t.schedule(new SensorTimer(arg1), 0, 5000);
		
		// For each event received on the socket,
        	arg0.onMessage(new Callback<String>() {
            public void invoke(String event) {

                // Log events to the console
                System.out.println(event);

            }
        });
        	
            // When the socket is closed.
            arg0.onClose(new Callback0() {
                public void invoke() {

                	t.cancel();
                    System.out.println("Disconnected");

                }
            });
        
	}

}
