package net.leludo.pi.component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Sensor {

	BufferedReader br ;
	
	public Sensor() throws IOException {		
	}
	
	
	public String read() throws IOException {
		br = new BufferedReader(new FileReader("/sys/bus/w1/devices/28-0000063735b4/w1_slave")) ;
		br.readLine() ;
		String toto = br.readLine() ;
		String value = toto.split(" ")[9].substring(2) ;
		return value ;
	}
}
