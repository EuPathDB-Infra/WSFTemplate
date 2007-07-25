/**
 * 
 */
package test.org.gusdb.wsftoy.client.echo;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.gusdb.wsf.client.WsfResponse;
import org.gusdb.wsf.client.WsfService;
import org.gusdb.wsf.client.WsfServiceServiceLocator;

/**
 * @author Jerric
 * @created Feb 17, 2006
 */
public class EchoClientTest {
    
    private static final String NEW_LINE = System.getProperty( "line.separator" );
    private static final String COMMAND = "wsftoyClient";
    private static final String PLUGIN_NAME = "org.gusdb.wsftoy.wsfplugin.echo.EchoPlugin";
    
    /**
     * <param_name, required> tuples
     */
    private static Map< String, Boolean > knownParams;
    
    static {
        knownParams = new HashMap< String, Boolean >();
        knownParams.put( "url", true );
        knownParams.put( "echo", true );
    }
    
    /**
     * @param args
     */
    public static void main( String[ ] args ) {
        // validate parameters
        Map< String, String > inParams = parseParameter( args );
        if ( !validateParams( inParams, knownParams ) )
            printUsage( COMMAND, knownParams );
        
        String echo = inParams.get( "echo" );
        String url = inParams.get( "url" );
        
        String[ ] params = { "Echo=" + echo };
        String[ ] orderedColumns = { "EchoString", "OsName", "OsVersion" };
        
        WsfServiceServiceLocator locator = new WsfServiceServiceLocator();
        
        try {
            WsfService service = locator.getWsfService( new URL( url ) );
            WsfResponse response = service.invoke( PLUGIN_NAME, "TestClient",
                    params, orderedColumns );
            String[ ][ ] result = response.getResults();
            String message = response.getMessage();
            
            // print out result message
            System.out.println( "Result message: \"" + message + "\"" );
            
            // print out columns
            System.out.println( printArray( orderedColumns ) );
            System.out.println( printArray( result ) );
        } catch ( MalformedURLException ex ) {
            ex.printStackTrace();
        } catch ( ServiceException ex ) {
            ex.printStackTrace();
        } catch ( RemoteException ex ) {
            ex.printStackTrace();
        }
    }
    
    private static Map< String, String > parseParameter( String[ ] args ) {
        Map< String, String > params = new HashMap< String, String >();
        for ( int i = 0; i < args.length; i++ ) {
            if ( args[ i ].charAt( 0 ) == '-' ) {
                String param = args[ i ].substring( 1 ).trim().toLowerCase();
                // check if the next arg is the value of this param
                String value = "";
                if ( i < args.length - 1 ) {
                    if ( args[ i + 1 ].charAt( 0 ) != '-' )
                        value = args[ i + 1 ].trim();
                }
                params.put( param, value );
            }
        }
        return params;
    }
    
    private static boolean validateParams( Map< String, String > inParams,
            Map< String, Boolean > knownParams ) {
        // check if all input parameters are known
        for ( String param : inParams.keySet() ) {
            if ( !knownParams.containsKey( param ) ) return false;
        }
        // check if all required parameters are present
        for ( String param : knownParams.keySet() ) {
            if ( knownParams.get( param ) ) {
                if ( !inParams.containsKey( param ) ) return false;
            }
        }
        return true;
    }
    
    private static void printUsage( String command,
            Map< String, Boolean > knownParams ) {
        System.err.print( command );
        for ( String param : knownParams.keySet() ) {
            boolean required = knownParams.get( param );
            System.err.print( " " );
            if ( !required ) System.err.print( '[' );
            System.err.print( "-" + param );
            System.err.print( " <" + param + "_value>" );
            if ( !required ) System.err.print( ']' );
        }
        System.err.println();
        System.exit( -1 );
    }
    
    private static String printArray( String[ ] array ) {
        StringBuffer sb = new StringBuffer();
        sb.append( "{" );
        for ( String s : array ) {
            if (sb.length()>1) sb.append( ", " );
            sb.append( "\"" + s +"\"");
        }
        sb.append( "}" );
        return sb.toString();
    }
    
    private static String printArray( String[ ][ ] array ) {
        StringBuffer sb = new StringBuffer();
        for ( String[ ] parts : array ) {
            sb.append( printArray( parts ) );
            sb.append( NEW_LINE );
        }
        return sb.toString();
    }
    
}
