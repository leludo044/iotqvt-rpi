package net.leludo.pi.component.mock;

public class Led
{
    private boolean on;
    private String name;

    public Led(String name)
    {
        on = false;
        this.name = name;
    }

    public void on()
    {
        on = true;
    }

    public void off()
    {
        on = false;
    }

    public boolean isOn()
    {
        return on == true;
    }

    public String getState()
    {
        return isOn() ? "on" : "off";
    }

    public String getName()
    {
        return this.name;
    }

}
