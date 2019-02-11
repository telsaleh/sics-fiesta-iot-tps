package uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import uk.ac.surrey.ee.iot.smartics.annotator.fiesta.FiestaObsAnnotator;
import uk.ac.surrey.ee.iot.smartics.annotator.fiesta.FiestaResAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.message.fiesta.TpsRequest;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Observations;

public class TpsHandler extends ServerResource {

    protected String brokerHostUrl = "";
    protected Properties prop = new Properties();
    protected String configPath = "config/broker.properties";

    @Post
    public Representation handlePost(Representation entity) throws IOException {

        String request = "";
        try {
            request = getRequest().getResourceRef().getPath();
        } catch (NullPointerException e) {
            request = "/getLastObservations";
        }

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
        String gloSuffixPath = prop.getProperty("url.path.broker.tps.get_last_observations");
        String goSuffixPath = prop.getProperty("url.path.broker.tps.get_observations");
        if (request.endsWith(gloSuffixPath)) {
            brokerHostUrl = brokerHostUrl + gloSuffixPath;
        } else {
            brokerHostUrl = brokerHostUrl + goSuffixPath;
        }

        String tpsRequest = entity.getText();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String result = getSicsObservation(tpsRequest);

        StringRepresentation response = new StringRepresentation(result);
        response.setMediaType(MediaType.valueOf("application/ld+json"));
        response.setCharacterSet(null);

        return response;
    }

    public String getSicsObservation(String tpsRequest) {

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
        for (int i = 0; i < tpsSize; i++) {

            String[] uriSplit = tpsReq.getSensorIDs().get(i).split(FiestaResAnnotator.INDV_NS_PREFIX + FiestaResAnnotator.sensingDevNamePrefix);
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
        client.getContext().getParameters().add("socketTimeout", prop.getProperty("client.socket.timeout"));
        final ClientResource smartIcsClientResource = new ClientResource(context, brokerHostUrl);
        smartIcsClientResource.setNext(client);
        smartIcsClientResource.accept(MediaType.APPLICATION_JSON);
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
            System.out.println("client internal TPS request payload:\n" + tpsRequest);
        }
        return errorMessage;

    }

}
