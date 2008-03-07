/**
 * 
 */
package org.gusdb.wsftoy.plugin;

import java.util.HashMap;
import java.util.Map;

import org.gusdb.wsf.plugin.WsfResult;
import org.gusdb.wsf.plugin.WsfServiceException;
import org.junit.After;
import org.junit.Test;

import static org.gusdb.wsftoy.plugin.EchoPlugin.*;
import static org.junit.Assert.*;

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
        WsfResult wsfResult = plugin.invoke(message, params, columns);
        String[][] result = wsfResult.getResult();
        assertEquals("result rows", 1, result.length);
        assertEquals("column-echo", message, result[0][2]);
        assertEquals("result-message", message, wsfResult.getMessage());
        assertEquals("signal", 1, wsfResult.getSignal());
        assertTrue("os-name", result[0][0].trim().length() > 0);
        assertTrue("os-version", result[0][1].trim().length() > 0);
    }
}
