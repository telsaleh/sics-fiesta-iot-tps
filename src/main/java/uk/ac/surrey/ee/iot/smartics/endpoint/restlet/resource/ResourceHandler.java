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
import uk.ac.surrey.ee.iot.smartics.annotator.FiestaResAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.proprietary.Resources;

public class ResourceHandler extends ServerResource {

    public String sicsURL = "http://131.227.88.96:5000/getResource/";

//    @Get("txt")
//    public String toString() {
//        return "Account of user \"" + "TEST" + "\"";
//    }
    @Get("json")
    public Representation handleGet() throws IOException {

        String result = getResource();
        StringRepresentation response = new StringRepresentation(result);
        response.setMediaType(MediaType.valueOf("application/ld+json"));

        return response;
    }

    public String getResource() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        String resLocalName = (String) getRequest().getAttributes().get("resourceUri");
        System.out.println("the resourceId is: " + resLocalName);
        sicsURL = sicsURL + resLocalName;
        System.out.println("the URL is: " + sicsURL);

        final Context context = new Context();
        Client client = new Client(new Context(), Protocol.HTTP);
        client.getContext().getParameters().add("maxConnectionsPerHost", "5");
        client.getContext().getParameters().add("maxTotalConnections", "5");
        final ClientResource sicsClientResource = new ClientResource(context, sicsURL);
        sicsClientResource.setNext(client);
        sicsClientResource.accept(MediaType.APPLICATION_JSON);
        String errorMessage = "";
        try {
            Representation result = sicsClientResource.get(MediaType.APPLICATION_JSON);
            sicsClientResource.release();
//            try {
//                return result.getText();

            try {
//                String resultString = result.getText();
                Resources res = objectMapper.readValue(result.getStream(), Resources.class);
//                Resources res = objectMapper.readValue(resultString, Resources.class);
                FiestaResAnnotator fa = new FiestaResAnnotator();
                String annotatedRes = fa.annotateResources(res);
                return annotatedRes;
            } catch (IOException ex) {
                Logger.getLogger(ResourceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ResourceException ex) {
//            Logger.getLogger(RegistryHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage());
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;

    }

}
