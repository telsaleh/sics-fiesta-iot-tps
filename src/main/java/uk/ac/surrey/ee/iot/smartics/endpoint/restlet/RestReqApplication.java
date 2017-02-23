package uk.ac.surrey.ee.iot.smartics.endpoint.restlet;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.ProxyHandler;

public class RestReqApplication extends Application {

    public static final String METHOD_PREFIX = "/getLastObservations"; //POST
    
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance of HelloWorldResource.
        Router router = new Router(getContext());
        
        router.attach(METHOD_PREFIX, ProxyHandler.class);        //POST
        
        return router;
    }
}
