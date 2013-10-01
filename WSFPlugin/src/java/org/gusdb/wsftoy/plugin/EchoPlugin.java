/**
 * 
 */
package org.gusdb.wsftoy.plugin;

import java.util.Map;

import org.gusdb.wsf.plugin.AbstractPlugin;
import org.gusdb.wsf.plugin.WsfRequest;
import org.gusdb.wsf.plugin.WsfResponse;
import org.gusdb.wsf.plugin.WsfServiceException;

/**
 * @author Jerric
 * @created Jan 4, 2006
 */
public class EchoPlugin extends AbstractPlugin {

  public static final String PARAM_ECHO = "message";

  public static final String COLUMN_OS_NAME = "OsName";
  public static final String COLUMN_OS_VERSION = "OsVersion";
  public static final String COLUMN_ECHO = "EchoString";

  /**
     * 
     */
  public EchoPlugin() {
    super();
  }

  @Override
  protected String[] defineContextKeys() {
    return new String[0];
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.WsfPlugin#getRequiredParameters()
   */
  @Override
  public String[] getRequiredParameterNames() {
    return new String[] { PARAM_ECHO };
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.WsfPlugin#getColumns()
   */
  @Override
  public String[] getColumns() {
    return new String[] { COLUMN_OS_NAME, COLUMN_OS_VERSION, COLUMN_ECHO };
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.WsfPlugin#validateParameters(java.util.Map)
   */
  @Override
  public void validateParameters(WsfRequest request) throws WsfServiceException {}

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.WsfPlugin#execute(java.util.Map, java.lang.String[])
   */
  @Override
  public WsfResponse execute(WsfRequest request) {

    // get parameter
    Map<String, String> params = request.getParams();
    String echo = params.get(PARAM_ECHO);

    // create a time zone and a Calendar
    String osName = System.getProperty("os.name");
    String osVersion = System.getProperty("os.version");

    // prepare the result
    String[] orderedColumns = request.getOrderedColumns();
    String[][] result = new String[1][orderedColumns.length];
    for (int i = 0; i < orderedColumns.length; i++) {
      if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_NAME)) {
        result[0][i] = osName;
      } else if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_VERSION)) {
        result[0][i] = osVersion;
      } else if (orderedColumns[i].equalsIgnoreCase(COLUMN_ECHO)) {
        result[0][i] = echo;
      }
    }
    WsfResponse response = new WsfResponse();
    response.setMessage(echo);
    response.setSignal(1);
    response.setResult(result);
    return response;
  }
}
