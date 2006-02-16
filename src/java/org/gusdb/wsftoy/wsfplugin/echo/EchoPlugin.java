/**
 * 
 */
package org.gusdb.wsftoy.wsfplugin.echo;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.gusdb.wsf.WsfPlugin;

/**
 * @author Jerric
 * @created Jan 4, 2006
 */
public class EchoPlugin extends WsfPlugin {

    public static final String PARAM_TIME_ZONE = "TimeZone";

    public static final String COLUMN_YEAR = "Year";
    public static final String COLUMN_MONTH = "Month";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_HOUR = "Hour";
    public static final String COLUMN_MINUTE = "Minute";
    public static final String COLUMN_SECOND = "Second";

    private static Logger logger = Logger.getLogger(EchoPlugin.class);

    /**
     * 
     */
    public EchoPlugin() {
        super(logger);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#getRequiredParameters()
     */
    @Override
    protected String[] getRequiredParameters() {
        return new String[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#getColumns()
     */
    @Override
    protected String[] getColumns() {
        return new String[] { COLUMN_YEAR, COLUMN_MONTH, COLUMN_DAY,
                COLUMN_HOUR, COLUMN_MINUTE, COLUMN_SECOND };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#execute(java.util.Map, java.lang.String[])
     */
    @Override
    protected String[][] execute(Map<String, String> params, String[] cols) {
        // get parameters
        String zoneID = "GMT-5"; // default time zone
        if (params.containsKey(PARAM_TIME_ZONE))
            zoneID = params.get(PARAM_TIME_ZONE);

        // create a time zone and a Calendar
        TimeZone timeZone = TimeZone.getTimeZone(zoneID);
        Calendar calendar = Calendar.getInstance(timeZone);
        String[][] result = new String[1][cols.length];
        for (int i = 0; i < cols.length; i++) {
            if (cols[i].equalsIgnoreCase(COLUMN_YEAR)) {
                result[0][i] = Integer.toString(calendar.get(Calendar.YEAR));
            } else if (cols[i].equalsIgnoreCase(COLUMN_MONTH)) {
                result[0][i] = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            } else if (cols[i].equalsIgnoreCase(COLUMN_DAY)) {
                result[0][i] = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            } else if (cols[i].equalsIgnoreCase(COLUMN_HOUR)) {
                result[0][i] = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
            } else if (cols[i].equalsIgnoreCase(COLUMN_MINUTE)) {
                result[0][i] = Integer.toString(calendar.get(Calendar.MINUTE));
            } else if (cols[i].equalsIgnoreCase(COLUMN_SECOND)) {
                result[0][i] = Integer.toString(calendar.get(Calendar.SECOND));
            }
        }
        return result;
    }
}
