/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.iot.smartics.endpoint.restlet;


import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.ProxyHandler;

public class RestReqApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    
    public static final String nodePrefix = "/iot-node"; //POST
    public static final String mapPrefix = "/node-map"; //POST
    
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());
        
        router.attach(mapPrefix, ProxyHandler.class);        
        router.attach(nodePrefix+"/{node_id}/{quantity_kind}", ProxyHandler.class); //GET
        
        return router;
    }

}
