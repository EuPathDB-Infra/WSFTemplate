package org.gusdb.wsftoy.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.gusdb.fgputil.MapBuilder;
import org.gusdb.wsf.client.ClientModelException;
import org.gusdb.wsf.client.ClientRequest;
import org.gusdb.wsf.client.ClientUserException;
import org.gusdb.wsf.client.WsfClient;
import org.gusdb.wsf.client.WsfClientFactory;
import org.gusdb.wsf.client.WsfClientFactoryImpl;
import org.gusdb.wsftoy.plugin.EchoPlugin;
import org.json.JSONObject;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoClient {

  public static final String ARG_MESSAGE = "message";

  public static final String[] ORDERED_COLUMNS = {
    EchoPlugin.COLUMN_OS_NAME, EchoPlugin.COLUMN_OS_VERSION,
    EchoPlugin.COLUMN_ECHO, EchoPlugin.COLUMN_EXTRA
  };

  private WsfClientFactory _clientFactory = new WsfClientFactoryImpl();

  public EchoResponse callEcho(URI serviceURI, String message) throws ClientModelException, ClientUserException {
    EchoResponse results = new EchoResponse();
    ClientRequest request = new ClientRequest(buildRequestJson(message));
    WsfClient client = (serviceURI == null ?
        _clientFactory.newClient(results) : _clientFactory.newClient(results, serviceURI));
    client.invoke(request);
    return results;
  }

  private String buildRequestJson(String message) {
    JSONObject json = new JSONObject();
    json.put(ClientRequest.PLUGIN_KEY, EchoPlugin.class.getName());
    json.put(ClientRequest.PROJECT_KEY, "EchoClient");
    for (String column : ORDERED_COLUMNS) {
      json.append(ClientRequest.COLUMNS_ARRAY_KEY, column);
    }
    json.put(ClientRequest.PARAMETER_MAP_KEY,
        getJsonFromMap(new MapBuilder<String,String>("message", message).toMap()));
    json.put(ClientRequest.CONTEXT_MAP_KEY, getJsonFromMap(new HashMap<String, String>()));
    return json.toString();
  }

  private JSONObject getJsonFromMap(Map<String, String> map) {
    JSONObject json = new JSONObject();
    for (Entry<String,String> entry : map.entrySet()) {
      json.put(entry.getKey(), entry.getValue());
    }
    return json;
  }
}
