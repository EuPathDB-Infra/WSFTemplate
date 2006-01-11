/**
 * 
 */
package org.gusdb.wsftoy.wsfplugin.echo;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.gusdb.wsf.IWsfPlugin;
import org.gusdb.wsf.WsfServiceException;

/**
 * @author Jerric
 * @created Jan 4, 2006
 */
public class EchoPlugin implements IWsfPlugin {

    private static final String[] COLUMNS = { "Year", "Month", "Day", "Hour",
            "Minute", "Second" };

    /**
     * 
     */
    public EchoPlugin() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.IWSFPlugin#invoke(java.lang.String[],
     *      java.lang.String[], java.lang.String[])
     */
    public String[][] invoke(String[] params, String[] values, String[] cols)
            throws WsfServiceException {
        // check the column
        if (cols.length != COLUMNS.length)
            throw new WsfServiceException("Imcompatible column(s):"
                    + printArray(cols) + ", where template: "
                    + printArray(COLUMNS));

        Set<String> colMap = new HashSet<String>();
        for (String col : cols) {
            colMap.add(col.toLowerCase());
        }
        for (String col : COLUMNS) {
            if (!colMap.contains(col.toLowerCase()))
                throw new WsfServiceException("Imcompatible column(s): "
                        + printArray(cols) + ", where template: "
                        + printArray(COLUMNS));
        }

        // get the date pattern
        String zoneID = "GMT-5"; // default time zone
        for (int i = 0; i < params.length; i++) {
            if (params[i].equalsIgnoreCase("TimeZone")) {
                zoneID = values[i];
            } else {
                System.err.println("Unknown parameter: " + params[i]);
            }
        }
        // create a time zone and a Calendar
        TimeZone timeZone = TimeZone.getTimeZone(zoneID);
        Calendar calendar = Calendar.getInstance(timeZone);
        String[][] result = new String[1][cols.length];
        for (int i = 0; i < cols.length; i++) {
            if (cols[i].equalsIgnoreCase("year")) {
                result[0][i] = Integer.toString(calendar.get(Calendar.YEAR));
            } else if (cols[i].equalsIgnoreCase("month")) {
                result[0][i] = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            } else if (cols[i].equalsIgnoreCase("day")) {
                result[0][i] = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            } else if (cols[i].equalsIgnoreCase("hour")) {
                result[0][i] = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
            } else if (cols[i].equalsIgnoreCase("minute")) {
                result[0][i] = Integer.toString(calendar.get(Calendar.MINUTE));
            } else if (cols[i].equalsIgnoreCase("second")) {
                result[0][i] = Integer.toString(calendar.get(Calendar.SECOND));
            }
        }
        return result;
    }

    private String printArray(String[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (String s : array) {
            sb.append(s);
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }
}
