package controllers;

import net.leludo.domobypi.components.Led;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.test;



public class Application extends Controller
{
    private static Led led = new Led("red");

    public static Result index()
    {
        return ok(index.render("Your new application is ready."));
    }

    public static Result led(String sw)
    {
        if (sw.equals("on"))
        {
            led.on();
        } else
        {
            led.off();
        }
        return ok(test.render(led.getName() + " led switched to "
                + led.getState()));
    }

    public static Result ledState()
    {
        return ok(test.render(led.getName() + " led is " + led.getState()));
    }

}
