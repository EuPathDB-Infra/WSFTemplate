/**
 * 
 */
package org.gusdb.wsftoy.plugin;

import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_ECHO;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_OS_NAME;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_OS_VERSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.gusdb.wsf.plugin.WsfRequest;
import org.gusdb.wsf.plugin.WsfResponse;
import org.gusdb.wsf.plugin.WsfServiceException;
import org.junit.After;
import org.junit.Test;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoPluginTest {

    private Map<String, String> params;
    private EchoPlugin plugin;

    public EchoPluginTest() {
        plugin = new EchoPlugin();
        params = new HashMap<String, String>();
    }

    @After
    public void createParams() {
        params.clear();
    }

    /**
     * @throws WsfServiceException 
     * 
     */
    @Test
    public void testEcho() throws WsfServiceException {
        String message = "This is a whole new world";
        params.put(EchoPlugin.PARAM_ECHO, message);
        String[] columns = { COLUMN_OS_VERSION, COLUMN_OS_NAME, COLUMN_ECHO };
        WsfRequest request = new WsfRequest();
        request.setParams(params);
        request.setOrderedColumns(columns);
        WsfResponse wsfResult = plugin.execute(request);
        String[][] result = wsfResult.getResult();
        assertEquals("result rows", 1, result.length);
        assertEquals("column-echo", message, result[0][2]);
        assertEquals("result-message", message, wsfResult.getMessage());
        assertEquals("signal", 1, wsfResult.getSignal());
        assertTrue("os-name", result[0][0].trim().length() > 0);
        assertTrue("os-version", result[0][1].trim().length() > 0);
    }
}
