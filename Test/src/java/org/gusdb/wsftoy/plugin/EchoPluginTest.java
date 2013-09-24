/**
 * 
 */
package org.gusdb.wsftoy.plugin;

import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_ECHO;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_OS_NAME;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_OS_VERSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.gusdb.wsf.plugin.PluginResponse;
import org.gusdb.wsf.plugin.PluginRequest;
import org.gusdb.wsf.plugin.WsfPluginException;
import org.junit.After;
import org.junit.Test;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoPluginTest {

    private final Map<String, String> params;
    private final EchoPlugin plugin;
    private final File storageDir;

    public EchoPluginTest() {
        plugin = new EchoPlugin();
        params = new HashMap<String, String>();
        String temp = System.getProperty("java.io.tmpdir", "/tmp");
        storageDir = new File(temp + "wsf-test/");
        if (!storageDir.exists() || !storageDir.isDirectory()) storageDir.mkdirs();
    }

    @After
    public void createParams() {
        params.clear();
    }

    /**
     * @throws WsfPluginException 
     * 
     */
    @Test
    public void testEcho() throws WsfPluginException {
        String message = "This is a whole new world";
        params.put(EchoPlugin.PARAM_ECHO, message);
        String[] columns = { COLUMN_OS_VERSION, COLUMN_OS_NAME, COLUMN_ECHO };
        PluginRequest request = new PluginRequest();
        request.setParams(params);
        request.setOrderedColumns(columns);
        
        PluginResponse response = new PluginResponse(storageDir, 1);
        plugin.execute(request, response);
        String[][] result = response.getPage(0);
        assertEquals("result rows", 1, result.length);
        assertEquals("column-echo", message, result[0][2]);
        assertEquals("result-message", message, response.getMessage());
        assertEquals("signal", 1, response.getSignal());
        assertTrue("os-name", result[0][0].trim().length() > 0);
        assertTrue("os-version", result[0][1].trim().length() > 0);
    }
}
