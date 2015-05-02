package net.leludo.domobypi.controller;

import net.leludo.pi.component.ComponentFactory;
import net.leludo.pi.component.Led;
import net.leludo.pi.component.PiPins;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.node.ObjectNode;



public class JsonController extends Controller
{
    private static ComponentFactory manager = ComponentFactory.getInstance() ;
    private static Led led = (Led)manager.createComponent("led", "Red led", PiPins.TWELVE) ;
    

    public static Result led(String sw)
    {
        if (sw.equals("on"))
        {
            led.on();
        } else
        {
            led.off();
        }
        return ledState() ;
    }

    public static Result ledState()
    {
    	ObjectNode result = Json.newObject() ;
    	result.put("led", led.getState()) ;
    	return ok(result) ;    	
    }

}
