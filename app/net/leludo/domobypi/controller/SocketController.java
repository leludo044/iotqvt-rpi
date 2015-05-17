package net.leludo.domobypi.controller;

import net.leludo.pi.component.mock.SensorWebSocketMock;
import play.mvc.Controller;
import play.mvc.WebSocket;



public class SocketController extends Controller
{
	
	public static WebSocket<String> socket() {
//	    return WebSocket.whenReady((in, out) -> {
//	        out.write("Hello!");
//	        out.close();
//	    });
		
		return new SensorWebSocketMock() ;
	}
}
