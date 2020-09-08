package kcn.kea.fitnessclub.ui.consolemenus;

import kcn.kea.fitnessclub.data.ClubDataAccess;
import kcn.kea.fitnessclub.ui.Console;
import kcn.kea.fitnessclub.ui.IMenu;
import kcn.kea.util.GET;

public class ConsoleMenu implements IMenu<Console>
{
    protected ClubDataAccess dataAccess;
    protected Console ui;
    protected String name;

    public ConsoleMenu(Console ui, ClubDataAccess dataAccess)
    {
        this.ui = ui;
        this.dataAccess = dataAccess;
        name = "Base Console Menu (No content)";
    }

    @Override
    public Console ui()
    {
        return ui;
    }

    @Override
    public void view()
    {
        ui.show("ConsoleMenu :: Message from base ConsoleMenu. This should have been overridden.");
    }

    protected boolean askIfExitMenu(boolean menuExitSignalSent)
    {
        if(menuExitSignalSent)
        {
            ui.show("");
            ui.show("Would you like to exit the " + getName() + "?");
            int choice = GET.getInteger("Type ' 0 ' to exit, any other number to stay: ");
            ui.show("");
            switch(choice)
            {
                case 0:
                    break;
                default:
                    menuExitSignalSent = false;
                    break;
            }
        }
        return menuExitSignalSent;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
