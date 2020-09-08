package kcn.kea.fitnessclub.abstracts;

public enum MemberType
{
    FULL(229), BASIC(199), ERROR(0);

    private final int fee;

    MemberType(int fee){this.fee = fee;}

    public static MemberType getType(String typeString)
    {
        switch(typeString){
            case "FULL" : return MemberType.FULL;
            case "BASIC" : return MemberType.BASIC;
            default: return MemberType.ERROR;
        }
    }

    public int fee(){return fee;}
}
//// Expanded
/// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html