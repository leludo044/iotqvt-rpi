package net.leludo.pi.timer;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import net.leludo.pi.component.Sensor;
import play.mvc.WebSocket.Out;

public class SensorTimer extends TimerTask {

	String name ;
	String temp;
	long date ;

	Out<String> out ;

	public SensorTimer(play.mvc.WebSocket.Out<String> arg1) {
		out = arg1 ;
		temp = "unknown" ;
		name = "Ludo";
	}

	@Override
	public void run() {
		try {
			date = new Date().getTime() ;
			temp = new Sensor().read();
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
			out.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
