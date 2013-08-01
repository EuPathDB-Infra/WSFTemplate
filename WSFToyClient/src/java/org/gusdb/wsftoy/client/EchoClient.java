/**
 * 
 */
package org.gusdb.wsftoy.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.gusdb.wsf.client.WsfResponse;
import org.gusdb.wsf.client.WsfService;
import org.gusdb.wsf.client.WsfServiceServiceLocator;
import org.gusdb.wsf.service.WsfRequest;
import org.gusdb.wsf.util.BaseCLI;
import org.gusdb.wsf.util.Formatter;

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

  private URL url;
  private String message;

  public EchoClient(String command) throws MalformedURLException {
    super(command, "Replay the given message on the remote service");

    url = new URL((String) getOptionValue(ARG_URL));
    message = (String) getOptionValue(ARG_MESSAGE);
  }

  public void execute() {
    Map<String, String> params = new LinkedHashMap<>();
    params.put("message", message);
    String[] columns = { "OsName", "OsVersion", "EchoString" };
    WsfRequest request = new WsfRequest();
    request.setParams(params);
    request.setOrderedColumns(columns);
    request.setPluginClass("org.gusdb.wsftoy.plugin.EchoPlugin");
    request.setProjectId("EchoClient");
    WsfServiceServiceLocator locator = new WsfServiceServiceLocator();
    try {
      WsfService service = locator.getWsfService(url);
      WsfResponse response = service.invoke(request.toString());
      String[][] result = response.getResult();
      String message = response.getMessage();
      int signal = response.getSignal();

      // print out result message
      System.out.println("Result message: \"" + message + "\"");
      System.out.println("Signal: \"" + signal + "\"");

      // print out columns
      System.out.println(Formatter.printArray(columns));
      System.out.println(Formatter.printArray(result));
    } catch (ServiceException ex) {
      ex.printStackTrace();
    } catch (RemoteException ex) {
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
    addSingleValueOption(ARG_MESSAGE, true, "no messages",
        "The message to be echoed by the remote service");
  }

}
