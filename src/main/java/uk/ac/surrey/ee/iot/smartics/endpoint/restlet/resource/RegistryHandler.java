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
import uk.ac.surrey.ee.iot.smartics.annotator.fiesta.FiestaResAnnotator;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Resources;

public class RegistryHandler extends ServerResource {

    public String brokerHostUrl = "";
    protected Properties prop = new Properties();
    protected String configPath = "config/broker.properties";

    @Get("json")
    public Representation handleGet() throws IOException {

        String result = getAllResources();
        StringRepresentation response = new StringRepresentation(result);
        response.setMediaType(MediaType.valueOf("application/ld+json"));
        response.setCharacterSet(null);

        return response;
    }

    public String getAllResources() {

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
            brokerHostUrl = prop.getProperty("url.host.registry");
        } catch (IOException e) {
            e.getMessage();
        }

        String iotRegistryUrl = prop.getProperty("url.path.registry.get_all_resources");

        final Context context = new Context();
        Client client = new Client(new Context(), Protocol.HTTP);
        client.getContext().getParameters().add("maxConnectionsPerHost", "5");
        client.getContext().getParameters().add("maxTotalConnections", "5");
        final ClientResource sicsClientResource = new ClientResource(context, brokerHostUrl + iotRegistryUrl);
        sicsClientResource.setNext(client);
        sicsClientResource.accept(MediaType.APPLICATION_JSON);
        String errorMessage = "";
        try {
            Representation result = sicsClientResource.get(MediaType.APPLICATION_JSON);
            sicsClientResource.release();

            try {
                Resources res = objectMapper.readValue(result.getStream(), Resources.class);
                FiestaResAnnotator fa = new FiestaResAnnotator();
                String annotatedRes = fa.annotateResources(res);
                return annotatedRes;
            } catch (IOException ex) {
                Logger.getLogger(RegistryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ResourceException ex) {
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage());
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;

    }

}
