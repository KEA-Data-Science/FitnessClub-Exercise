package kcn.kea.fitnessclub.ui;

public interface IMenu<T extends IPresent>
{
    /** Promises to retrieve ui object*/
    public T ui();
    /** Promises printable name of menu. */
    public String getName();

    public void view();
}
