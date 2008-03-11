/**
 * 
 */
package org.gusdb.wsftoy.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.cli.ParseException;
import org.gusdb.wsf.client.WsfService;
import org.gusdb.wsf.client.WsfServiceServiceLocator;
import org.gusdb.wsf.plugin.WsfResult;
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
     * @throws ParseException 
     * @throws MalformedURLException 
     */
    public static void main(String[] args) throws MalformedURLException {
        String command = System.getProperty("command", "echoClient");
        EchoClient client = new EchoClient(command);

        try {
            client.parseCommandLine(args);
        } catch (ParseException ex) {
            client.printUsage();
            System.exit(-1);
        }
        client.invoke();
    }

    private URL url;
    private String message;

    public EchoClient(String command) throws MalformedURLException {
        super(command, "Replay the given message on the remote service");

        url = new URL((String) getOptionValue(ARG_URL));
        message = (String) getOptionValue(ARG_MESSAGE);
    }

    public void invoke() {
        String[] columns = { "OsName", "OsVersion", "EchoString" };
        WsfServiceServiceLocator locator = new WsfServiceServiceLocator();
        try {
            WsfService service = locator.getWsfService(url);
            WsfResult wsfResult = service.invokeEx(
                    "org.gusdb.wsftoy.plugin.EchoPlugin", "EchoClient",
                    new String[] { "message=" + message }, columns);
            String[][] result = wsfResult.getResult();
            String message = wsfResult.getMessage();
            int signal = wsfResult.getSignal();

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
        addSingleValueOption(ARG_URL, true, null,
                "The url of the remote service");
        addSingleValueOption(ARG_MESSAGE, true, "no messages",
                "The message to be echoed by the remote service");
    }
}
