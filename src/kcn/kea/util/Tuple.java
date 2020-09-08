package kcn.kea.util;

public class Tuple<A, B>
{
    A itemA;
    B itemB;

    public Tuple(A itemA, B itemB)
    {
        this.itemA = itemA;
        this.itemB = itemB;
    }

    public A getItemA()
    {
        return itemA;
    }

    public void setItemA(A itemA)
    {
        this.itemA = itemA;
    }

    public B getItemB()
    {
        return itemB;
    }

    public void setItemB(B itemB)
    {
        this.itemB = itemB;
    }
}
