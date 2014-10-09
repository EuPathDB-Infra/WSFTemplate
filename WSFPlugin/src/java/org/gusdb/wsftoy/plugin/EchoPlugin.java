package org.gusdb.wsftoy.plugin;

import java.util.Map;
import java.util.Random;

import org.gusdb.wsf.plugin.AbstractPlugin;
import org.gusdb.wsf.plugin.PluginModelException;
import org.gusdb.wsf.plugin.PluginRequest;
import org.gusdb.wsf.plugin.PluginResponse;
import org.gusdb.wsf.plugin.PluginUserException;

/**
 * @author Jerric
 * @created Jan 4, 2006
 */
public class EchoPlugin extends AbstractPlugin {

  public static final String PARAM_ECHO = "message";

  public static final String COLUMN_OS_NAME = "OsName";
  public static final String COLUMN_OS_VERSION = "OsVersion";
  public static final String COLUMN_ECHO = "EchoString";
  public static final String COLUMN_EXTRA = "Extra";

  private final Random random;

  public EchoPlugin() {
    super();
    random = new Random();
  }

  @Override
  public String[] getRequiredParameterNames() {
    return new String[] { PARAM_ECHO };
  }

  @Override
  public void validateParameters(PluginRequest request) throws PluginModelException, PluginUserException {
    // do nothing
  }

  @Override
  public String[] getColumns() {
    return new String[] { COLUMN_OS_NAME, COLUMN_OS_VERSION, COLUMN_ECHO, COLUMN_EXTRA };
  }

  @Override
  protected int execute(PluginRequest request, PluginResponse response)
      throws PluginModelException, PluginUserException {
    Map<String, String> params = request.getParams();
    String[] orderedColumns = request.getOrderedColumns();

    // get parameter
    String echo = params.get(PARAM_ECHO);

    // create a time zone and a Calendar
    String osName = System.getProperty("os.name");
    String osVersion = System.getProperty("os.version");

    // prepare the result
    int count = random.nextInt(100);// determine # of rows
    for (int r = 0; r < count; r++) {
      String[] row = new String[orderedColumns.length];
      for (int i = 0; i < orderedColumns.length; i++) {
        if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_NAME)) {
          row[i] = osName;
        }
        else if (orderedColumns[i].equalsIgnoreCase(COLUMN_OS_VERSION)) {
          row[i] = osVersion;
        }
        else if (orderedColumns[i].equalsIgnoreCase(COLUMN_ECHO)) {
          row[i] = echo;
        }
        else if (orderedColumns[i].equalsIgnoreCase(COLUMN_EXTRA)) {
          row[i] = Integer.toString(random.nextInt());
        }
      }
      response.addRow(row);
    }
    response.setMessage(echo);
    return 1;
  }
}
