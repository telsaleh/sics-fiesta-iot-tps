package uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.iot.smartics.annotator.FiestaObsAnnotator;
import uk.ac.surrey.ee.iot.smartics.annotator.FiestaResAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.fiesta.message.TpsRequest;
import uk.ac.surrey.ee.iot.smartics.model.fiesta.message.TpsRequest_old;
import uk.ac.surrey.ee.iot.smartics.model.proprietary.Observations;

public class TpsHandler extends ServerResource {

    public String sicsURL = "http://131.227.88.96:5000/";
    public String getLastObservations = "getLastObservations";
    public String getObservations = "getObservations";

//    @Get("txt")
//    public String toString() {
//        return "Account of user \"" + "TEST" + "\"";
//    }
    
    @Post("json")
    public Representation handlePost(Representation entity) throws IOException {
        
        String request = getRequest().getResourceRef().getPath();
        System.out.println("HI:"+ request);
        if (request.endsWith(getLastObservations))
                sicsURL = sicsURL + getLastObservations;
        else 
                sicsURL = sicsURL + getObservations;

        String tpsRequest = entity.getText();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String result = getSmartIcsObservation(tpsRequest);
        StringRepresentation response = new StringRepresentation(result);
        response.setMediaType(MediaType.APPLICATION_JSON);

        return response;
    }

    public String getSmartIcsObservation(String tpsRequest) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        TpsRequest tpsReq = new TpsRequest();
        
        try {
            tpsReq = objectMapper.readValue(tpsRequest, TpsRequest.class);
        } catch (IOException ex) {
            Logger.getLogger(TpsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        int tpsSize = tpsReq.getSensorIDs().size();
         for (int i=0; i<tpsSize; i++) {
             
             String [] uriSplit = tpsReq.getSensorIDs().get(i).split(FiestaResAnnotator.INDV_NS_PREFIX+FiestaResAnnotator.sensingDevNamePrefix);
             tpsReq.getSensorIDs().set(i, uriSplit[1]);
         }
         
        try {
            tpsRequest = objectMapper.writeValueAsString(tpsReq);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TpsHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        final Context context = new Context();
        Client client = new Client(new Context(), Protocol.HTTP);
        client.getContext().getParameters().add("maxConnectionsPerHost", "5");
        client.getContext().getParameters().add("maxTotalConnections", "5");
        final ClientResource smartIcsClientResource = new ClientResource(context, sicsURL);
        smartIcsClientResource.setNext(client);
        smartIcsClientResource.accept(MediaType.APPLICATION_JSON);
        System.out.println("TPS request: " + tpsRequest);
        String errorMessage = "";
        try {
            Representation result = smartIcsClientResource
                    .post(new StringRepresentation(tpsRequest, MediaType.APPLICATION_JSON));
            smartIcsClientResource.release();
            try {
                Observations obs = objectMapper.readValue(result.getStream(), Observations.class);
                FiestaObsAnnotator fa = new FiestaObsAnnotator();
                String annotatedObs = fa.annotateObservations(obs);
                return annotatedObs;
            } catch (IOException ioex) {
                Logger.getLogger(TpsHandler.class.getName()).log(Level.SEVERE, null, ioex);
                System.out.println("IO Error....ERROR IS THIS: " + ioex.getLocalizedMessage());
            }
        } catch (ResourceException ex) {
            Logger.getLogger(TpsHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage());
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;

    }

    public static void main(String[] args) throws JAXBException, IOException {

        String test = "{"
                + "\n"
                + "  \"SensorIDs\": [\"http://smart-ics.ee.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-001-power\"]\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        TpsRequest tpsReq = objectMapper.readValue(test, TpsRequest.class);
        TpsHandler tpsH = new TpsHandler();
        Representation rep = new StringRepresentation(test);
        Representation response = tpsH.handlePost(rep);
        System.out.print(response.getText());

//        FiestaObsAnnotator ri = new FiestaObsAnnotator();
//        String result = ri.annotateObservations(obs);
//        System.out.println(result);
    }

}
