/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.iot.smartics.annotator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import uk.ac.surrey.ee.iot.smartics.endpoint.servlet.DefaultServletListener;
import uk.ac.surrey.ee.iot.smartics.model.proprietary.Observation;
import uk.ac.surrey.ee.iot.smartics.model.proprietary.Observations;

/**
 *
 * @author te0003
 */
public class FiestaObsAnnotator {

    public FiestaObsAnnotator() {
    }

    //reference ontologies
    public String FIESTA_ONT_FILE_RUN = "file:///C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/ontologies/fiesta-iot/fiesta-iot.owl";
    public String FIESTA_ONT_FILE_DEPLOY = "/ontologies/fiesta-iot/fiesta-iot.owl";

    //prefixes
    public String INDV_NAMESPACE = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/";
    
    //location
    public String LOCATION = "ICS";
    public String RELATIVE_LOCATION = "http://sws.geonames.org/6695971/";

    public String annotateObservations(Observations obs) {

        //instantiate models
        String ontFilePath = "";
        try {
            ontFilePath = DefaultServletListener.servletContext.getRealPath(FIESTA_ONT_FILE_DEPLOY);
        } catch (NoClassDefFoundError fe) {
            ontFilePath = FIESTA_ONT_FILE_RUN;
        }
        Model tpsModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        Model fiestOnt = FileManager.get().loadModel(ontFilePath);

        try{
        //iterate over observations
        for (Observation ob : obs.getObservations()) {

            try{
//            if (ob == null) {
//                continue; // or break, whatever is better in your case
//            }            

            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            ontModel.setStrictMode(true);
            ontModel.add(fiestOnt);

            //ontology prefixes
            ontModel.setNsPrefix("sics", INDV_NAMESPACE);
            ontModel.setNsPrefix("dul", "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#");
            ontModel.setNsPrefix("time", "http://www.w3.org/2006/time#");
            String IOT_LITE_PREFIX = fiestOnt.getNsPrefixURI("iot-lite");
            String M3_LITE_PREFIX = fiestOnt.getNsPrefixURI("mthreelite");
            String SSN_PREFIX = fiestOnt.getNsPrefixURI("ssn");
            String GEO_PREFIX = fiestOnt.getNsPrefixURI("geo");
            String DUL_PREFIX = ontModel.getNsPrefixURI("dul");
            String TIME_PREFIX = ontModel.getNsPrefixURI("time");

            //classes
            OntClass observationClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Observation"); //ok
            OntClass sensingDevClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + ob.getIotType()); //ok
            OntClass sensorOutputClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "SensorOutput"); //ok
            OntClass observationValueClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "ObservationValue"); //ok
            OntClass unitClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + ob.getUnit()); //ok
            OntClass qkClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + ob.getQk()); //not used, why?
            
            OntClass geoPointClass = (OntClass) ontModel.getOntClass(GEO_PREFIX + "Point"); //ok
            OntClass timeIntervalClass = (OntClass) ontModel.getOntClass(TIME_PREFIX + "Instant");

            //object properties
            Property observedBy = ontModel.getProperty(SSN_PREFIX + "observedBy"); //ok
            Property observedProperty = ontModel.getProperty(SSN_PREFIX + "observedProperty"); //ok
            Property geoLocation = ontModel.getProperty(GEO_PREFIX + "location"); //ok
            Property hasUnit = ontModel.getProperty(IOT_LITE_PREFIX + "hasUnit"); //ok
            Property observationResult = ontModel.getProperty(SSN_PREFIX + "observationResult"); //ok
            Property obserationSamplingTime = ontModel.getProperty(SSN_PREFIX + "observationSamplingTime"); //ok
            Property hasValue = ontModel.getProperty(SSN_PREFIX + "hasValue");

            //datatype properties
            Property geoLat = ontModel.getProperty(GEO_PREFIX + "lat"); //ok
            Property geoLong = ontModel.getProperty(GEO_PREFIX + "long"); //ok
            Property geoAlt = ontModel.getProperty(GEO_PREFIX + "alt"); //ok
            Property geoAltRel = ontModel.getProperty(IOT_LITE_PREFIX + "altRelative"); //ok
            Property geoRelLoc = ontModel.getProperty(IOT_LITE_PREFIX + "relativeLocation"); //ok
            Property inXSDDateTime = ontModel.getProperty(TIME_PREFIX + "inXSDDateTime"); //ok
            Property hasDataValue = ontModel.getProperty(DUL_PREFIX + "hasDataValue"); //ok

            //individual prefixes (instances)
            String sensingDevNamePrefix = "resource/";
            String observationNamePrefix = "observationName#";
            String obsValueNamePrefix = "observationValue#";
            String sensorOutputNamePrefix = "sensorOutput#";
            String obsPropNamePrefix = "observationProperty#:";
            String timeIntNamePrefix = "timeInterval#";
            String unitPrefix = "unit#";
            String locNamePrefix = "loc#";

            //sensing device
            String sensingDevUri = INDV_NAMESPACE + sensingDevNamePrefix + ob.getResourceId();
            Individual sensorDevIndiv = ontModel.createIndividual(sensingDevUri, sensingDevClass);

            //observation 
            String observationUri = INDV_NAMESPACE + observationNamePrefix + ob.getResourceId();
            Individual observationIndiv = ontModel.createIndividual(observationUri, observationClass);
            observationIndiv.setPropertyValue(observedBy, sensorDevIndiv);

            //sensor output
            String sensorOutputUri = INDV_NAMESPACE + sensorOutputNamePrefix + ob.getResourceId();
            Individual sensorOutputIndiv = ontModel.createIndividual(sensorOutputUri, sensorOutputClass);
            observationIndiv.setPropertyValue(observationResult, sensorOutputIndiv);

            //observation Value
            String obsValueUri = INDV_NAMESPACE + obsValueNamePrefix + ob.getResourceId();
            Individual obsValueIndiv = ontModel.createIndividual(obsValueUri, observationValueClass);
            sensorOutputIndiv.setPropertyValue(hasValue, obsValueIndiv);
            obsValueIndiv.setPropertyValue(hasDataValue, ontModel.createLiteral(ob.getDataValue()));

            //observed property
            String obsPropUri = INDV_NAMESPACE + obsPropNamePrefix + ob.getQk();
            Individual obsPropIndiv = ontModel.createIndividual(obsPropUri, qkClass);
            observationIndiv.setPropertyValue(observedProperty, obsPropIndiv);

            //time interval
            String timeIntUri = INDV_NAMESPACE + timeIntNamePrefix + ob.getTimestamp();
            Individual timeIntervalIndiv = ontModel.createIndividual(timeIntUri, timeIntervalClass);
            observationIndiv.setPropertyValue(obserationSamplingTime, timeIntervalIndiv);
            timeIntervalIndiv.setPropertyValue(inXSDDateTime, ontModel.createLiteral(ob.getTimestamp()));

            //unit individual
            String unitUri = INDV_NAMESPACE + unitPrefix + ob.getUnit();
            Individual unitIndiv = ontModel.createIndividual(unitUri, unitClass);
            obsValueIndiv.setPropertyValue(hasUnit, unitIndiv);

            //location individual
            String locationUri = INDV_NAMESPACE + locNamePrefix + LOCATION;
            Individual locationIndiv = ontModel.createIndividual(locationUri, geoPointClass);
            locationIndiv.setPropertyValue(geoLat, ontModel.createLiteral(ob.getLat()));
            locationIndiv.setPropertyValue(geoLong, ontModel.createLiteral(ob.getLon()));
            locationIndiv.setPropertyValue(geoAlt, ontModel.createLiteral(ob.getAlt()));
            locationIndiv.setPropertyValue(geoAltRel, ontModel.createLiteral(ob.getRelativeAlt()));
            locationIndiv.setPropertyValue(geoRelLoc, ontModel.createLiteral(RELATIVE_LOCATION));
            observationIndiv.setPropertyValue(geoLocation, locationIndiv);

            //extract all individuals
            Model mIndividuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            mIndividuals.setNsPrefixes(ontModel.getNsPrefixMap());
            ExtendedIterator<Individual> iterIndv = ontModel.listIndividuals();
            while (iterIndv.hasNext()) {
                Individual indv = (Individual) iterIndv.next();
                mIndividuals.add(indv.getOntModel());
            }
            mIndividuals.remove(fiestOnt);
            tpsModel.add(mIndividuals);
            
            }catch(NullPointerException npe){
                System.out.println("null returned from smart-ics...");
            }
        }
        }catch(NullPointerException npe){
        System.out.println("null returned from smart-ics1...");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        tpsModel.write(bos, RDFLanguages.strLangJSONLD);
        String sicsAnnotated = bos.toString();

        return sicsAnnotated;
    }

    public static void main(String[] args) throws JAXBException, IOException {

        String test = "{\n"
                + "	\"Observations\": [{\n"
                + "		\"iot-type\": \"unis-smart-campus\",\n"
                + "		\"timestamp\": \"2016-12-23T18:56:00.001\",\n"
                + "		\"mobile\": \"False\",\n"
                + "		\"resourceId\": \"sc-sics-sp-001-Power\",\n"
                + "		\"dataValue\": \"0.297018\",\n"
                + "		\"measurement\": \"Watts\",\n"
                + "		\"lon\": \"-0.1\",\n"
                + "		\"system\": \"smart-ics\",\n"
                + "		\"relativeAlt\": \"2\",\n"
                + "		\"platform\": \"desk-ICS-02-18\",\n"
                + "		\"deviceId\": \"sc-sics-sp-001\",\n"
                + "		\"deployment\": \"unis-smart-campus\",\n"
                + "		\"lat\": \"51.1\",\n"
                + "		\"qk\": \"Power\",\n"
                + "		\"alt\": \"53\",\n"
                + "		\"unit\": \"Watts\"\n"
                + "	}]\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Observations obs = objectMapper.readValue(test, Observations.class);

//        Observation ob = new Observation("sc-si-sc-001-temp", "sc-si-sc-001", "smart-ics", "desk-ICS-02-18", "false", "unis-smart-campus", "sensor", "AmbientTemperature", "Celsius", "automatic", "51.1", "-0.1", "50", "2", "2002-05-30T09:30:10.5", "23.1");
//        ArrayList<Observation> obsArray = new ArrayList<>();
//        obsArray.add(ob);
//        Observations obs = new Observations(); 
//        obs.setObservations(obsArray);
        FiestaObsAnnotator ri = new FiestaObsAnnotator();
        String result = ri.annotateObservations(obs);
        System.out.println(result);
    }
}
