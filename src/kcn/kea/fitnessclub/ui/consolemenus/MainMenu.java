package kcn.kea.fitnessclub.ui.consolemenus;

import kcn.kea.fitnessclub.data.ClubDataAccess;
import kcn.kea.fitnessclub.ui.Console;
import kcn.kea.fitnessclub.ui.IMenu;
import kcn.kea.util.GET;

public class MainMenu extends ConsoleMenu implements IMenu<Console>
{
    private IMenu<Console>[] menus;

    public MainMenu(Console ui, ClubDataAccess dataAccess, IMenu<Console>[] menus)
    {
        super(ui, dataAccess);
        this.name = "Main Menu";
        this.menus = menus;
    }

    @Override
    public Console ui()
    {
        return ui;
    }

    @Override
    public void view()
    {
        boolean exitSignal = false;
        while(!exitSignal)
        {
            ui.show("");
            ui.show("Welcome to the Fitness Club :: " + getName());
            ui.show("");
            ui.show("Select a menu by typing number: 0 - " + (menus.length - 1));
            ui.show("");

            for(int i = 0; i < menus.length; i++)
            {
                ui.show("\t[" + i + "]\t" + menus[i].getName());
            }
            ui.show("");
            ui.show("Notice: Exit a menu by typing a number not specified.");
            ui.show("");

            int choice = GET.getInteger("Please enter the number of desired menu: ");
            if(choice > -1 && choice <= menus.length - 1){ menus[choice].view(); } else{ exitSignal = true; }

            exitSignal = askIfExitMenu(exitSignal);
        }
    }

    @Override
    public String getName(){ return name; }
}
