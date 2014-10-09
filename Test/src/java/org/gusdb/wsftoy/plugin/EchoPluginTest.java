package org.gusdb.wsftoy.plugin;

import static org.gusdb.fgputil.FormatUtil.NL;

import java.net.URI;

import org.gusdb.fgputil.BaseCLI;
import org.gusdb.wsftoy.client.EchoClient;
import org.gusdb.wsftoy.client.EchoResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoPluginTest extends BaseCLI {

  public static final String ARG_URL = "url";

  private URI _serviceUri;
  private String _message;
  
  public static void main(String[] args) throws Exception {
    EchoPluginTest tester = new EchoPluginTest();
    tester.invoke(args);
  }

  public EchoPluginTest() {
    super(System.getProperty("command", "echoClient"), "Replay the given message on the remote service");
  }

  @Override
  protected void declareOptions() {
    addSingleValueOption(ARG_URL, true, null, "The url of the remote service");
    addSingleValueOption(EchoClient.ARG_MESSAGE, true, "no messages", "The message to be echoed by the remote service");
  }

  @Override
  public void execute() throws Exception {
    _serviceUri = new URI((String)getOptionValue(ARG_URL));
    _message = (String) getOptionValue(EchoClient.ARG_MESSAGE);
    testEcho();
  }

  @Before
  public void setUpTest() {
    _serviceUri = null; // use local during unit testing
    _message = "This is a whole new world";
  }

  @Test
  public void testEcho() throws Exception {
    EchoClient client = new EchoClient();
    EchoResponse response = client.callEcho(_serviceUri, _message);
    System.out.println("Ran EchoClient and received:" + NL + response.getOutputString());
  }
}
