/**
 * 
 */
package test.org.gusdb.wsftoy.wsfplugin.echo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.gusdb.wsf.plugin.WsfPlugin;
import org.gusdb.wsf.plugin.WsfServiceException;
import org.gusdb.wsftoy.wsfplugin.echo.EchoPlugin;

/**
 * @author  Jerric
 * @created Feb 17, 2006
 */
public class EchoPluginTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        EchoPlugin plugin = new EchoPlugin();
        plugin.setLogger(Logger.getLogger(plugin.getClass()));
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("TimeZone", "GMT+8");
        String[] orderedColumns = {"Hour", "Minute", "Second", "Month", "Day", "Year"};
        
        try {
            String[][] result = plugin.invoke(params, orderedColumns);
            // print out columns
            System.out.println(WsfPlugin.printArray(orderedColumns));
            System.out.println(WsfPlugin.printArray(result));
        } catch (WsfServiceException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
            // System.err.println(ex);
        }
        
    }
}
