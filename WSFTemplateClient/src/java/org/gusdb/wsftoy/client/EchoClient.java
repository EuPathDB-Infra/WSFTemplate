/**
 * 
 */
package org.gusdb.wsftoy.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.rpc.ServiceException;

import org.gusdb.wsf.client.WsfResponse;
import org.gusdb.wsf.client.WsfService;
import org.gusdb.wsf.client.WsfServiceServiceLocator;
import org.gusdb.wsf.service.WsfRequest;
import org.gusdb.wsf.util.BaseCLI;
import org.gusdb.wsf.util.Formatter;
import org.gusdb.wsftoy.plugin.EchoPlugin;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoClient extends BaseCLI {

  public static final String ARG_URL = "url";
  public static final String ARG_MESSAGE = "message";

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    String command = System.getProperty("command", "echoClient");
    EchoClient client = new EchoClient(command);
    client.invoke(args);
  }

  public EchoClient(String command) throws MalformedURLException {
    super((command != null) ? command : "echoClientTest", "Replay the given message on the remote service");
  }

  public void execute() throws MalformedURLException {
    Random random = new Random();
    URL url = new URL((String) getOptionValue(ARG_URL));
    String message = (String) getOptionValue(ARG_MESSAGE);
    while (true) {
      callEcho(url, message + "-" + random.nextInt(Integer.MAX_VALUE));
      break;
    }
  }

  public void callEcho(URL url, String message) {
    Map<String, String> params = new LinkedHashMap<>();
    params.put("message", message);
    String[] columns = { "OsName", "OsVersion", "EchoString", "Extra" };
    WsfRequest request = new WsfRequest();
    request.setParams(params);
    request.setOrderedColumns(columns);
    request.setPluginClass(EchoPlugin.class.getName());
    request.setProjectId("EchoClient");
    request.setContext(new HashMap<String, String>());
    WsfServiceServiceLocator locator = new WsfServiceServiceLocator();
    try {
      WsfService service = locator.getWsfService(url);
      WsfResponse response = service.invoke(request.toString());
      String[][] result = response.getResult();
      String resultMessage = response.getMessage();
      int signal = response.getSignal();

      // print out result message
      System.out.println("Result message: \"" + resultMessage + "\"");
      System.out.println("Signal: \"" + signal + "\"");

      // print out columns
      System.out.println(Formatter.printArray(columns));
      System.out.println(Formatter.printArray(result));
    }
    catch (ServiceException ex) {
      ex.printStackTrace();
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.gusdb.wsf.plugin.BaseCLI#declareOptions()
   */
  @Override
  protected void declareOptions() {
    addSingleValueOption(ARG_URL, true, null, "The url of the remote service");
    addSingleValueOption(ARG_MESSAGE, true, "no messages", "The message to be echoed by the remote service");
  }

}
