package kcn.kea.fitnessclub.ui;

import kcn.kea.fitnessclub.ui.IPresent;

public class Console implements IPresent
{

    private String prefix;
    private String postfix;
    private int lineNumber;
    private String formatString;
    private String formatStringNoLineBreak;

    private int screenWidth;
    private String formatNoPostfix;

    public Console(String prefix, String postfix, String formatString, int screenWidth)
    {
        this.prefix = prefix;
        this.postfix = postfix;
        lineNumber = 0;
        this.formatString = formatString;

        this.screenWidth = screenWidth;
    }

    public Console()
    {
        setFactorySetting();
    }

    public void setFactorySetting()
    {
        prefix = "<|>";
        postfix = "<||>";
        lineNumber = 0;
        if(screenWidth == 0)
        {
            screenWidth = 110; // IDE sized factory standard
        }

        formatString = " %-6s %-4d> %-" + screenWidth + "." + screenWidth + "s%-10s %n";
        formatNoPostfix = " %-6s %-4d> %-" + 35 + "s";
        formatStringNoLineBreak = "%-4s%-4d> %s";
        // prefix, linenumber, string to present (cut at screenWidth length), postfix
    }

    public String getPrefix(){ return prefix; }

    public void setPrefix(String prefix){ this.prefix = prefix; }

    public String getPostfix(){ return postfix; }

    public void setPostfix(String postfix){ this.postfix = postfix; }

    public String getFormatString(){ return formatString; }

    public void setFormatString(String formatString){ this.formatString = formatString; }

    public int getScreenWidth(){ return screenWidth; }

    public void setScreenWidth(int screenWidth)
    {
        this.screenWidth = screenWidth;
        setFactorySetting();
    }

    /**
     * Method formats the supplied message string,
     * and prints it to the standard output stream
     */
    @Override
    public void show(String stringToPresent)
    {
        System.out.printf(formatString, prefix, lineNumber++, stringToPresent, postfix);
    }

    /**
     * The message is presented as usual, but without post-fix and carriage return
     */
    public void showNoCR(String stringToPresent)
    {
        System.out.printf(formatNoPostfix,
                          prefix, lineNumber++,
                          stringToPresent);
    }

    public String getFormatStringNoLineBreak()
    {
        return formatStringNoLineBreak;
    }

    public void setFormatStringNoLineBreak(String formatStringNoLineBreak)
    {
        this.formatStringNoLineBreak = formatStringNoLineBreak;
    }
}
