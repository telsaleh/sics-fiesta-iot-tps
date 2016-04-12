/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.iot.smartics.annotator;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.text.WordUtils;
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

/**
 *
 * @author te0003
 */
public class IotNodeDataAnnotator {

    public IotNodeDataAnnotator() {
    }

    public String BASE_ONT_PREFIX = "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#";
//    public String BASE_ONT_FILE = "file:///C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/ontologies/iot-lite/iot-lite.ttl";
    public String BASE_ONT_FILE = "/ontologies/iot-lite/iot-lite.ttl";
    public String SSN_FILE = "/ontologies/ssn/ssn.ttl";
    public String DEREF_INDV_PREFIX = "http://platform.fiesta-iot.eu/srd/registry/poc/";
    public String INDV_NS_PREFIX = "http://iot.ee.surrey.ac.uk/smartcampus#";

    public String annotateObservation(String nodeNumber, String quantityKind, String value, String timestamp) {

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.setStrictMode(true);
//        Model iotLiteOnt = FileManager.get().loadModel(BASE_ONT_PREFIX); 
        
        Model iotLiteOnt = FileManager.get().loadModel(DefaultServletListener.servletContext.getRealPath(BASE_ONT_FILE));
        ontModel.add(iotLiteOnt);
        ontModel.setNsPrefix("sc", INDV_NS_PREFIX);
        ontModel.setNsPrefix("fiesta-res", DEREF_INDV_PREFIX);
        ontModel.setNsPrefix("dul", "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#");
        String M3_LITE_PREFIX = iotLiteOnt.getNsPrefixURI("m3-lite");
        String SSN_PREFIX = iotLiteOnt.getNsPrefixURI("ssn");
        String GEO_PREFIX = iotLiteOnt.getNsPrefixURI("geo");
        String QU_PREFIX = iotLiteOnt.getNsPrefixURI("qu");
        String DUL_PREFIX = ontModel.getNsPrefixURI("dul");
        
        Model ssnOnt = FileManager.get().loadModel(DefaultServletListener.servletContext.getRealPath(SSN_FILE));

        //classes
        OntClass observationClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Observation");
        OntClass sensingDevClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "SensingDevice");
        OntClass sensorOutputClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "SensorOutput");
        OntClass observationValueClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "ObservationValue");
        OntClass quantityClass = (OntClass) ontModel.getOntClass(QU_PREFIX + "QuantityKind");      
        OntClass unitClass = (OntClass) ontModel.getOntClass(QU_PREFIX + "Unit");
        OntClass geoPointClass = (OntClass) ontModel.getOntClass(GEO_PREFIX + "Point");
        OntClass timeIntervalClass = (OntClass) ontModel.getOntClass(DUL_PREFIX + "TimeInterval");

        //properties
        Property observedBy = ontModel.getProperty(SSN_PREFIX + "observedBy");
        Property observedProperty = ontModel.getProperty(SSN_PREFIX + "observedProperty");
        Property geoLocation = ontModel.getProperty(GEO_PREFIX + "location");
        Property geoLat = ontModel.getProperty(GEO_PREFIX + "lat");
        Property geoLong = ontModel.getProperty(GEO_PREFIX + "long");
        Property hasUnit = ontModel.getProperty(BASE_ONT_PREFIX + "hasUnit");
        Property observationResult = ontModel.getProperty(SSN_PREFIX + "observationResult");
        Property obserationSamplingTime = ontModel.getProperty(SSN_PREFIX + "obserationSamplingTime");
        Property hasIntervalDate = ontModel.getProperty(DUL_PREFIX + "hasIntervalDate");
        Property hasValue = ontModel.getProperty(SSN_PREFIX + "hasValue");
        Property hasDataValue = ontModel.getProperty(DUL_PREFIX + "hasDataValue");

        String[] quantityKinds = {"Temperature", "Illuminance", "Sound", "Presence", "Power"};
        String[] units = {"DegreeCelsius", "Lux", "Decibel", "Boolean", "Watt"};
        
        String deviceNamePrefix = "urn:x-iot:smartcampus:smart-ics:n";//"IoT-Node";
        String observationNamePrefix = "urn:x-iot:smartcampus:smart-ics:obs";
        String obsValueNamePrefix = "urn:x-iot:smartcampus:smart-ics:obsval";
        String sensorOutputNamePrefix = "urn:x-iot:smartcampus:smart-ics:so";
        String obsPropNamePrefix = "urn:x-iot:smartcampus:smart-ics:op";
        String timeIntNamePrefix = "urn:x-iot:smartcampus:smart-ics:ti";
        String locNamePrefix = "UK-GU-UNIS-ICS-Desk";
        String separator = ".";

        String unit ="unit";        
               
                //iterate the String array
                for(int i=0; i < quantityKinds.length; i++){
                       
                        //check if string array contains the string
                        if(quantityKinds[i].equalsIgnoreCase(quantityKind)){
                                unit = units[i];
                                //string found
                                break;                               
                        }
                }        

        String deviceNumber = String.valueOf(nodeNumber); //String.format("%03d", 1); e.g. "001"
        String devNumFormatted = String.format("%03d", Integer.parseInt(nodeNumber));

        //urn:x-iot:smart-ics:iot-node:1.Temperature
        
        //sensing device
        String sensingDeviceUri = DEREF_INDV_PREFIX + deviceNamePrefix + devNumFormatted + separator + quantityKind;
        Individual sensorDevIndiv = ontModel.createIndividual(sensingDeviceUri, sensingDevClass);
        
        //observation 
        String observationUri = INDV_NS_PREFIX + observationNamePrefix + devNumFormatted + separator + quantityKind;
        Individual observationIndiv = ontModel.createIndividual(observationUri, observationClass);
        observationIndiv.setPropertyValue(observedBy, sensorDevIndiv);

        //sensor output
        String sensorOutputUri = INDV_NS_PREFIX + sensorOutputNamePrefix + devNumFormatted + separator + quantityKind;
        Individual sensorOutputIndiv = ontModel.createIndividual(sensorOutputUri, sensorOutputClass);
        observationIndiv.setPropertyValue(observationResult, sensorOutputIndiv);

        //observation Value
        String obsValueUri = INDV_NS_PREFIX + obsValueNamePrefix + devNumFormatted + separator + quantityKind;
        Individual obsValueIndiv = ontModel.createIndividual(obsValueUri, observationValueClass);
        sensorOutputIndiv.setPropertyValue(hasValue, obsValueIndiv);
        obsValueIndiv.setPropertyValue(hasDataValue, ontModel.createLiteral(String.valueOf(value)));
        
        //observed property
        String obsPropUri = M3_LITE_PREFIX + WordUtils.capitalize(quantityKind);
        Individual obsPropIndiv = ontModel.createIndividual(obsPropUri, observationValueClass);
        observationIndiv.setPropertyValue(observedProperty, obsPropIndiv);

        //time interval
        String timeIntUri = INDV_NS_PREFIX + timeIntNamePrefix + devNumFormatted + separator + "timestamp";
        Individual timeIntervalIndiv = ontModel.createIndividual(timeIntUri, timeIntervalClass);
        observationIndiv.setPropertyValue(obserationSamplingTime, timeIntervalIndiv);
        timeIntervalIndiv.setPropertyValue(hasIntervalDate, ontModel.createLiteral(String.valueOf(timestamp)));

        //unit individual
        String unitUri = M3_LITE_PREFIX + unit;
        Individual unitIndiv = ontModel.createIndividual(unitUri, unitClass);
        obsValueIndiv.setPropertyValue(hasUnit, unitIndiv);

        double lat = ThreadLocalRandom.current().nextDouble(51.243223, 51.243512);
        double lon = ThreadLocalRandom.current().nextDouble(-0.593416, -0.592964);

        //location individual
        Individual locationIndiv = ontModel.createIndividual(INDV_NS_PREFIX + locNamePrefix + deviceNumber, geoPointClass);
        locationIndiv.setPropertyValue(geoLat, ontModel.createLiteral(String.valueOf(lat)));
        locationIndiv.setPropertyValue(geoLong, ontModel.createLiteral(String.valueOf(lon)));
        observationIndiv.setPropertyValue(geoLocation, locationIndiv);

        Model mIndividuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        mIndividuals.setNsPrefixes(ontModel.getNsPrefixMap());
        ExtendedIterator<Individual> iterIndv = ontModel.listIndividuals();

        while (iterIndv.hasNext()) {
            Individual indv = (Individual) iterIndv.next();
            mIndividuals.add(indv.getOntModel());

        }
//        mIndividuals.remove(iotLiteOnt).write(System.out, "TURTLE");
//  mIndividuals.remove(iotLiteOnt).write(System.out, "JSON-LD");  
//  mIndividuals.remove(iotLiteOnt).write(System.out, "RDF/XML");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mIndividuals.remove(ssnOnt);
        mIndividuals.remove(iotLiteOnt);        
        mIndividuals.write(bos, RDFLanguages.strLangJSONLD);
        String sicsAnnotated = bos.toString();
        
        return sicsAnnotated;
    }

    public static void main(String[] args) throws JAXBException {

        IotNodeDataAnnotator ri = new IotNodeDataAnnotator();
        ri.annotateObservation("1000", "temperature", "10.01", "2014-07-14 07:35:15.0");
    }

}
