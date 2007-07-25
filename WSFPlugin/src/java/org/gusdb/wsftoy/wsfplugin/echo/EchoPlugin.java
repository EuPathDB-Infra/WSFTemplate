/**
 * 
 */
package org.gusdb.wsftoy.wsfplugin.echo;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import org.gusdb.wsf.plugin.WsfPlugin;
import org.gusdb.wsf.plugin.WsfServiceException;

/**
 * @author Jerric
 * @created Jan 4, 2006
 */
public class EchoPlugin extends WsfPlugin {

    public static final String PARAM_ECHO = "Echo";

    public static final String COLUMN_OS_NAME = "OsName";
    public static final String COLUMN_OS_VERSION = "OsVersion";
    public static final String COLUMN_ECHO = "EchoString";

    /**
     * 
     */
    public EchoPlugin() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#getRequiredParameters()
     */
    @Override
    protected String[] getRequiredParameterNames() {
        return new String[] { PARAM_ECHO };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#getColumns()
     */
    @Override
    protected String[] getColumns() {
        return new String[] { COLUMN_OS_NAME, COLUMN_OS_VERSION, COLUMN_ECHO };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#validateParameters(java.util.Map)
     */
    @Override
    protected void validateParameters(Map<String, String> params)
            throws WsfServiceException {
    // nothing in this case
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.gusdb.wsf.WsfPlugin#execute(java.util.Map, java.lang.String[])
     */
    @Override
    protected String[][] execute(String invokeKey, Map<String, String> params,
            String[] orderedColumns) {
        // get parameter
        String echo = params.get(PARAM_ECHO);

        // create a time zone and a Calendar
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");

        // prepare the result
        String[][] result = new String[1][orderedColumns.length];
        for (int i = 0; i < orderedColumns.length; i++) {
            if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_NAME)) {
                result[0][i] = osName;
            } else if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_VERSION)) {
                result[0][i] = osVersion;
            } else if (orderedColumns[i].equalsIgnoreCase(COLUMN_ECHO)) {
                result[0][i] = "You said: " + echo;
            }
        }
        return result;
    }
}
