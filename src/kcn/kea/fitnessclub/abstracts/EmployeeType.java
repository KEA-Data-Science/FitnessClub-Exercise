package kcn.kea.fitnessclub.abstracts;

public enum EmployeeType
{
    ADMINISTRATION,INSTRUCTOR,ERROR;

    public static EmployeeType getType(String typeString){
        switch(typeString){
            case "ADMINISTRATION":return EmployeeType.ADMINISTRATION;
            case "INSTRUCTOR": return EmployeeType.INSTRUCTOR;
            default: return EmployeeType.ERROR;
        }
    }
}
