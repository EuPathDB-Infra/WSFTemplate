/**
 * 
 */
package org.gusdb.wsftoy.plugin;

import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_ECHO;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_EXTRA;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_OS_NAME;
import static org.gusdb.wsftoy.plugin.EchoPlugin.COLUMN_OS_VERSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.gusdb.wsf.service.WsfRequest;
import org.gusdb.wsf.service.WsfResponse;
import org.gusdb.wsf.service.WsfService;
import org.gusdb.wsf.service.WsfServiceException;
import org.junit.Test;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoServiceTest {

  public static void main(String[] args) throws WsfServiceException {
    EchoServiceTest tester = new EchoServiceTest();
    while (true) {
      tester.testEcho();
      // break;
    }
  }

  private final WsfService service;
  private final Random random;

  public EchoServiceTest() {
    service = new WsfService();
    random = new Random();
  }

  /**
   * @throws WsfServiceException
   * 
   */
  @Test
  public void testEcho() throws WsfServiceException {
    String message = "This is a whole new world - " + random.nextInt(Integer.MAX_VALUE);
    Map<String, String> params = new HashMap<>();
    params.put(EchoPlugin.PARAM_ECHO, message);
    String[] columns = { COLUMN_OS_VERSION, COLUMN_OS_NAME, COLUMN_ECHO, COLUMN_EXTRA };
    WsfRequest request = new WsfRequest();
    request.setParams(params);
    request.setOrderedColumns(columns);
    request.setPluginClass(EchoPlugin.class.getName());
    request.setProjectId("EchoService");
    request.setContext(new HashMap<String, String>());

    WsfResponse response = service.invoke(request.toString());

    int pages = response.getPageCount();
    assertTrue("page count", pages >= 1);
    for (int i = 0; i < pages; i++) {
      response.setCurrentPage(i);
      String[][] result = response.getResult();
      assertTrue("result rows", result.length >= 100);
      assertEquals("result-message", message, response.getMessage());
      assertEquals("signal", 1, response.getSignal());
      for (int r = 0; r < result.length; r++) {
        assertEquals("column-echo", message, result[r][2]);
        assertTrue("os-name", result[r][0].trim().length() > 0);
        assertTrue("os-version", result[r][1].trim().length() > 0);
      }
    }
  }
}
