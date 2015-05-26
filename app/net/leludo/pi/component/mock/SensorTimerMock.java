package net.leludo.pi.component.mock;

import java.util.Date;
import java.util.TimerTask;

import javax.websocket.Session;

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
		StringBuffer sb = new StringBuffer() ;
		temp = new Long(Math.round(20000+(Math.random()*5000))).toString();
		sb.append("{") ;
		sb.append("\"").append("id").append("\"") ;
		sb.append(":").append("\"").append(name).append("\"");
		sb.append(",");
		sb.append("\"").append("temp").append("\"") ;
		sb.append(":").append(temp);
		sb.append(",");
		sb.append("\"").append("date").append("\"") ;
		sb.append(":").append(date);
		sb.append("}");
		
		System.out.println(sb.toString());
		if (out != null) {
			out.write(sb.toString());
		} 
		
		if (wsc != null) {
			wsc.sendMessage(sb.toString());
		}
	}
}
