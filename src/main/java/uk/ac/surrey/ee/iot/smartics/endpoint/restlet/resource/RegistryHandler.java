package uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.iot.smartics.annotator.FiestaRegistrar;
import uk.ac.surrey.ee.iot.smartics.model.ics.Resources;

public class RegistryHandler extends ServerResource {

    public String sicsURL = "http://131.227.88.96:5000/getAllResources";

//    @Get("txt")
//    public String toString() {
//        return "Account of user \"" + "TEST" + "\"";
//    }
    @Get ("json")
    public Representation handleGet() throws IOException {
               
        String result = getAllResources();
        StringRepresentation response = new StringRepresentation(result);
        response.setMediaType(MediaType.APPLICATION_JSON);
        
        return response;
    }

    public String getAllResources() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        final Context context = new Context();
        Client client = new Client(new Context(), Protocol.HTTP);
        client.getContext().getParameters().add("maxConnectionsPerHost", "5");
        client.getContext().getParameters().add("maxTotalConnections", "5");
        final ClientResource sicsClientResource = new ClientResource(context, sicsURL);
        sicsClientResource.setNext(client);
        sicsClientResource.accept(MediaType.APPLICATION_JSON);
        String errorMessage="";
        try {
            Representation result = sicsClientResource.get(MediaType.APPLICATION_JSON);
            sicsClientResource.release();
//            try {
//                return result.getText();
                
            try {
                Resources res = objectMapper.readValue(result.getStream(), Resources.class);
                FiestaRegistrar fa = new FiestaRegistrar();
                String annotatedRes = fa.annotateResources(res);               
                return annotatedRes;
            } catch (IOException ex) {
                Logger.getLogger(RegistryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
//            } catch (IOException ex) {
//                Logger.getLogger(RegistryHandler.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (ResourceException ex) {
//            Logger.getLogger(RegistryHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage()); 
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;
        
    }

}
