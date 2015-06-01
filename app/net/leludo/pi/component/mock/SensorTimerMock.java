package net.leludo.pi.component.mock;

import java.util.Date;
import java.util.TimerTask;

import javax.websocket.Session;

import net.leludo.pi.timer.MasterValueFormatter;
import net.leludo.pi.timer.SensorWebsocketClient;
import play.mvc.WebSocket.Out;

public class SensorTimerMock extends TimerTask {

	String name ;
	String temp;
	long date ;
	
	Out<String> out ;
	SensorWebsocketClient wsc ;

	public SensorTimerMock(play.mvc.WebSocket.Out<String> arg1) {
		out = arg1 ;
		temp = "unknown" ;
		name = "Ludo";
		wsc = null ;
	}
	
	public SensorTimerMock(SensorWebsocketClient swsc) {
		this.wsc = swsc ;
		temp="unknown";
		name = "Ludo" ;
		out = null ;
	}

	@Override
	public void run() {
		date = new Date().getTime() ;
		temp = new Long(Math.round(20000+(Math.random()*5000))).toString();
		
		String json = MasterValueFormatter.toJson(name,  date,  temp);
				
		System.out.println(json);
		if (out != null) {
			out.write(json);
		} 
		
		if (wsc != null) {
			wsc.sendMessage(json);
		}
	}
}
