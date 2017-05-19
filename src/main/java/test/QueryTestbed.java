/*
 * Copyright (C) 2017 te0003
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.util.FileManager;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource.TpsHandler;
import uk.ac.surrey.ee.iot.smartics.endpoint.servlet.DefaultServletListener;

/**
 *
 * @author te0003
 */
public class QueryTestbed {
   
    //URLs
    public final String GET_ALL_RESOURCES_URL   = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/registry/getAllResources";
    public final String GET_RESOURCE_URL1       = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-001-power";
    public final String GET_RESOURCE_URL2       = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-002-power";
    public final String GET_OBSERVATIONS_URL    = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/tps/getObservations";

    //Requests
    public final String GET_OBSERVATIONS_REQUEST1 =   "C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/request/getObs.json";

    //Ontology files
    protected final String FIESTA_ONT_FILE_DEPLOY   =   "web/ontologies/fiesta-iot/fiesta-iot.owl";
    protected final String FIESTA_ONT_FILE_RUN      =   "file:///C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/ontologies/fiesta-iot/fiesta-iot.owl";

    //SPARQL Queries
    protected final String SPARQL_QUERY1            = "C:/Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/query/sensors_declared.rq";
    protected final String SPARQL_QUERY2            = "C:/Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/query/sensing_device_list.rq";
    protected final String SPARQL_DESCRIBE_SENSORS  = "C:/Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/query/describe_sensors.rq";
    protected final String SPARQL_GET_SUBCLASSES    = "C:/Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/query/get_subclasses.rq";
    protected final String SPARQL_GET_OBSERVATIONS  = "C:/Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/query/get_observations.rq";
    protected final String SPARQL_GET_RES_OBS       = "C:/Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/query/discover_and_get_obs.rq";
    
    //RDF Models
    public OntModel ontModel;
    
    public final Context context = new Context();
    public Client client = new Client(new Context(), Protocol.HTTP);
    
     public QueryTestbed() {                
        
        client.getContext().getParameters().add("maxConnectionsPerHost", "5");
        client.getContext().getParameters().add("maxTotalConnections", "5");
        
        
        //instantiate models
        String ontFilePath = "";
        try {
            ontFilePath = DefaultServletListener.servletContext.getRealPath(FIESTA_ONT_FILE_DEPLOY);
        } catch (NoClassDefFoundError fe) {
            ontFilePath = FIESTA_ONT_FILE_RUN;
        }
        Model fiestaOnt = FileManager.get().loadModel(ontFilePath);

        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.setStrictMode(true);        
        ontModel.add(fiestaOnt);
        
    }

    public void queryDataset(String datasetString, String queryString) {        

        System.out.println("--------------------------------------------------");
        System.out.println("\nQuery Result");
        System.out.println("--------------------------------------------------");

        long qDuration = 0;
        Query query = QueryFactory.create(queryString);
        Instant preQuery = Instant.now();

        try (QueryExecution qexec = QueryExecutionFactory.create(query, ontModel)) {
            if (query.isSelectType()) {//            
                ResultSet results = qexec.execSelect();
                Instant postQuery = Instant.now();
                qDuration = postQuery.toEpochMilli() - preQuery.toEpochMilli();
                ResultSetFormatter.outputAsCSV(System.out, results);
            } 
//            else if (query.isDescribeType()) {
//                Model m = qexec.execDescribe();
//                m.write(System.out, "TURTLE");
//            }
        }

        System.out.println("sparql query duration: " + qDuration + "ms");
    }

    public String getResources(String url) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        final ClientResource sicsClientResource = new ClientResource(context, url);
        sicsClientResource.setNext(client);
        sicsClientResource.accept(MediaType.APPLICATION_JSON);
        String errorMessage = "";
        try {
            Representation result = sicsClientResource.get(MediaType.APPLICATION_JSON);
            sicsClientResource.release();
            return result.getText();

        } catch (IOException | ResourceException ex) {
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage());
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;

    }

    public String getObservations(String tpsRequest) {

        try {
            tpsRequest = org.apache.commons.io.FileUtils.readFileToString(new File(tpsRequest));
            System.out.println(tpsRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        final ClientResource smartIcsClientResource = new ClientResource(context, GET_OBSERVATIONS_URL);
        smartIcsClientResource.setNext(client);
//        smartIcsClientResource.accept(MediaType.APPLICATION_JSON);
        System.out.println("TPS request: " + tpsRequest);
        String errorMessage = "";
        try {
            Representation result = smartIcsClientResource
                    .post(new StringRepresentation(tpsRequest, MediaType.APPLICATION_JSON));
            smartIcsClientResource.release();
            return result.getText();
        } catch (IOException | ResourceException ex) {
            Logger.getLogger(TpsHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR IS THIS: " + ex.getLocalizedMessage());
            errorMessage = ex.getLocalizedMessage();
        }
        return errorMessage;

    }
    
    public void appendToDataset(String datasetString) {
        
        ontModel.read(new ByteArrayInputStream(datasetString.getBytes()), null, RDFLanguages.strLangJSONLD);
        
    }

    public static void main(String[] args) {

        QueryTestbed qr = new QueryTestbed();
        String result = "";

        result = qr.getResources(qr.GET_RESOURCE_URL1);
        qr.appendToDataset(result);
        result = qr.getResources(qr.GET_RESOURCE_URL2);
        qr.appendToDataset(result);
        result = qr.getObservations(qr.GET_OBSERVATIONS_REQUEST1);
        qr.appendToDataset(result);
        
//        System.out.println(result);

        String queryString = "";
        try {
            queryString = org.apache.commons.io.FileUtils.readFileToString(new File(qr.SPARQL_GET_RES_OBS));
            System.out.println(result);
            qr.queryDataset(result, queryString);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
