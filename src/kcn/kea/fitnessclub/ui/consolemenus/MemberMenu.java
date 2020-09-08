package kcn.kea.fitnessclub.ui.consolemenus;

import kcn.kea.fitnessclub.data.ClubDataAccess;
import kcn.kea.fitnessclub.abstracts.MemberType;
import kcn.kea.fitnessclub.models.Member;
import kcn.kea.fitnessclub.models.Month;
import kcn.kea.fitnessclub.ui.Console;
import kcn.kea.fitnessclub.ui.IMenu;
import kcn.kea.util.GET;
import kcn.kea.util.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class MemberMenu extends ConsoleMenu implements IMenu<Console>
{

    public MemberMenu(Console ui, ClubDataAccess dataAccess)
    {
        super(ui, dataAccess);
        name = "Member Menu";
    }


    @Override
    public Console ui()
    {
        return ui;
    }

    @Override
    public void view()
    {
        boolean menuExited = false;
        while(!menuExited)
        {
            ui.show("");
            ui.show("\tMember functionality, pick a number:");
            ui.show("");

            ui.show("\t[1]\tList all members");
            ui.show("\t[2]\tList full members");
            ui.show("\t[3]\tList basic members");
            ui.show("\t[4]\tList members months of membership");

            int choice = GET.getInteger("Please enter the number of desired menu: ");
            switch(choice)
            {
                case 1:
                    listMembers();
                    break;
                case 2:
                    listMembers(MemberType.FULL);
                    break;
                case 3:
                    listMembers(MemberType.BASIC);
                    break;
                case 4:
                    monthsOfMember();
                    break;
                default:
                    menuExited = true;
            }

            menuExited = askIfExitMenu(menuExited);
        }
    }

    private void listMembers()
    {
        ui.show("");
        ui.show("\tListing all members basic info:");
        ui.show("");

        HashMap<Integer, Member> membersMap = dataAccess.getMemberDAO().readAll();

        for(Map.Entry<Integer, Member> e : membersMap.entrySet())
        {
            ui.show(e.getValue().toString() + "   " + e.getValue().getMembershipType().fee());
        }
    }

    private void listMembers(MemberType type)
    {
        ui.show("");
        ui.show("\tListing " + type + " all members basic info:");
        ui.show("");

        HashMap<Integer, Member> membersMap = dataAccess.getMemberDAO().readAll();

        for(Map.Entry<Integer, Member> e : membersMap.entrySet())
        {
            if(e.getValue().getMembershipType() == type)
            {
                ui.show(e.getValue().toString() + "   " + e.getValue().getMembershipType().fee());
            }
        }
    }

    private void monthsOfMember()
    {
        boolean menuExit = false;
        while(!menuExit)
        {
            ui.show("Find months of a member");
            ui.show("");
            int choice = GET.getInteger("Enter id of member personage: ");


            Member member = dataAccess.getMemberDAO().read(choice);
            if(member.getID() != -1)
            {
                ui.show("You found member:");
                ui.show(member.toString());

                var monthsMap = dataAccess.getMonthDAO().readAll();

                var fafMap = new Month().getFieldsAndFormatting();

                String formattedHeadline = String.format("%-10s%-10s%-14s%-14s%-15s",
                                                         "Month ID", "Person ID", "Year", "Month", "Noted hours");
                ui.show(formattedHeadline);

                for(Map.Entry<Triplet<Integer, Integer, Integer>, Month> m : monthsMap.entrySet())
                {
                    if(m.getKey().getValue0() == choice)
                    {
                        ui.show(m.getValue().toString() + "   ");
                    }
                }
            } else
            {
                ui.show("The ID you supplied had no matches in the database.");
            }
            ui.show("");
            menuExit = !GET.getString("Type: ' GO ' to continue searching months: ").equalsIgnoreCase("GO");
        }
    }

    @Override
    public String getName()
    {
        return name;
    }
}



