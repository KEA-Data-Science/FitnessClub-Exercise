package kcn.kea;

import kcn.kea.fitnessclub.data.ClubDataAccess;
import kcn.kea.fitnessclub.EmployeeSalaryCalculator;
import kcn.kea.fitnessclub.ui.Console;
import kcn.kea.fitnessclub.ui.IMenu;
import kcn.kea.fitnessclub.ui.consolemenus.ConsoleMenu;
import kcn.kea.fitnessclub.ui.consolemenus.EmployeeMenu;
import kcn.kea.fitnessclub.ui.consolemenus.MainMenu;
import kcn.kea.fitnessclub.ui.consolemenus.MemberMenu;
import kcn.kea.util.DBConnectionManager;
import kcn.kea.util.GET;

public class FitnessClubProgram
{

    public static void main(String[] args)
    {
//        // Dummy data generator, sadly, only works with functioning database
//        var genie = new ClubDummyDataGenerator();
//        genie.generateData();
        int screenWidth = 95;

        Console console = new Console();

        GET.getConsole().setScreenWidth(screenWidth);
        console.setScreenWidth(screenWidth);

        console.show(DBConnectionManager.getState());
        DBConnectionManager.getConnection();
        console.show("NB: If you use the supplied Gear Host database, loading times are sometimes high.");
        console.show("Gear Host database not responsive on employee tables; recommending local db.");
        console.show(DBConnectionManager.getState());

        ClubDataAccess dataAccess = new ClubDataAccess("fitnessclub", new EmployeeSalaryCalculator());

        IMenu<Console>[] menus = new ConsoleMenu[]{
                new EmployeeMenu(console, dataAccess),
                new MemberMenu(console, dataAccess)};

        IMenu<Console> mainMenu = new MainMenu(console, dataAccess, menus);

        mainMenu.view();

        DBConnectionManager.closeCurrentConnection();
        console.show(DBConnectionManager.getState());
    }
}
