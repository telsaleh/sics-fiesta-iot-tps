package uk.ac.surrey.ee.iot.smartics.endpoint.restlet;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.IotServiceHandler;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.RegistryHandler;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.ResourceHandler;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.TpsHandler;

public class RestReqApplication extends Application {

    public static final String TPS_PREFIX = "/tps";
    public static final String REGISTRY_PREFIX = "/registry";
    public static final String GET_ALL_RES_PATH = "/getAllResources"; //POST
    public static final String RESOURCE_PREFIX = "/resource/";
    public static final String SERVICE_PREFIX = "/service/";
    public static final String GET_LAST_OBS_PATH = "/getLastObservations"; //POST
    public static final String GET_OBS_PATH = "/getObservations"; //POST

    @Override
    public synchronized Restlet createInboundRoot() {

        Router router = new Router(getContext());

        router.attach(REGISTRY_PREFIX + GET_ALL_RES_PATH, RegistryHandler.class);        //GET
        router.attach(TPS_PREFIX + GET_OBS_PATH, TpsHandler.class);                //POST   
        router.attach(TPS_PREFIX + GET_LAST_OBS_PATH, TpsHandler.class);                //POST   
        
        router.attach(RESOURCE_PREFIX + "{resourceUri}", ResourceHandler.class);        //GET
        router.attach(SERVICE_PREFIX + "{resourceUri}", IotServiceHandler.class);        //GET

        return router;
    }
}
