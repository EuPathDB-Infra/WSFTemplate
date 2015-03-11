package org.gusdb.wsftoy.client;

import static org.gusdb.fgputil.FormatUtil.NL;

import java.util.ArrayList;
import java.util.List;

import org.gusdb.fgputil.FormatUtil;
import org.gusdb.wsf.client.ClientModelException;
import org.gusdb.wsf.client.ClientUserException;
import org.gusdb.wsf.client.WsfResponseListener;

public class EchoResponse implements WsfResponseListener {

  private String _message;
  private List<String[]> _rows = new ArrayList<>();

  public String getMessage() { return _message; }
  public List<String[]> getRows() { return _rows; }
  
  @Override
  public void onRowReceived(String[] row)
      throws ClientModelException, ClientUserException {
    _rows.add(row);
  }

  @Override
  public void onAttachmentReceived(String key, String content)
      throws ClientModelException, ClientUserException {
    // echo plugin does not include attachments
  }

  @Override
  public void onMessageReceived(String message)
      throws ClientModelException, ClientUserException {
    _message = message;
  }

  public String getOutputString() {
    StringBuilder sb = new StringBuilder()
      .append("EchoPluginResponse {").append(NL)
      .append("  message: ").append(_message).append(NL)
      .append("  rows: {").append(NL);
    for (String[] row : _rows) {
      sb.append("    ").append(FormatUtil.arrayToString(row)).append(NL);
    }
    return sb.append("  }").append(NL).append("}").append(NL).toString();
  }
}
