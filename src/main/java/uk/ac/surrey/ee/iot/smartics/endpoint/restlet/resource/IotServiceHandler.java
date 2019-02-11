package uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
import uk.ac.surrey.ee.iot.smartics.annotator.fiesta.FiestaObsAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Observations;

public class IotServiceHandler extends ServerResource {

    public String brokerHostUrl = "";
    protected Properties prop = new Properties();
    protected String configPath = "config/broker.properties";

    @Get("json")
    public Representation handleGet() throws IOException {

        String result = getObservation();
        StringRepresentation response = new StringRepresentation(result);
        response.setMediaType(MediaType.valueOf("application/ld+json"));
        response.setCharacterSet(null);

        return response;
    }

    public String getObservation() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        try {
            InputStream inputStream;
            inputStream = getClass().getClassLoader().getResourceAsStream(configPath);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + configPath + "' not found in the classpath");
            }
            brokerHostUrl = prop.getProperty("url.host.broker");            
        } catch (IOException e) {
            e.getMessage();
        }

        String iotServiceUrl = prop.getProperty("url.path.broker.service.get_last_observation");
        String resLocalName = (String) getRequest().getAttributes().get("resourceUri");
        System.out.println("the resourceId is: " + resLocalName);
        brokerHostUrl = brokerHostUrl + iotServiceUrl + resLocalName;
        System.out.println("the URL is: " + brokerHostUrl);

        final Context context = new Context();
        Client client = new Client(new Context(), Protocol.HTTP);
        client.getContext().getParameters().add("maxConnectionsPerHost", "5");
        client.getContext().getParameters().add("maxTotalConnections", "5");
        final ClientResource sicsClientResource = new ClientResource(context, brokerHostUrl);
        sicsClientResource.setNext(client);
        sicsClientResource.accept(MediaType.APPLICATION_JSON);
        String errorMessage = "";
        try {
            Representation result = sicsClientResource.get(MediaType.APPLICATION_JSON);
            sicsClientResource.release();
            try {
                Observations obs = objectMapper.readValue(result.getStream(), Observations.class);
                FiestaObsAnnotator fa = new FiestaObsAnnotator();
                String annotatedObs = fa.annotateObservations(obs);
                return annotatedObs;
            } catch (IOException ex) {
                Logger.getLogger(IotServiceHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ResourceException ex) {
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage());
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;

    }

}
