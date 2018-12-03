/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.bind.JAXBException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author te0003
 */
public class DescriptionGenerator_old {

    public DescriptionGenerator_old() {
    }

    public String IOT_LITE_PREFIX = "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#";
    public String DEREF_INDV_PREFIX = "http://platform.fiesta-iot.eu/srd/registry/poc/";
    public String INDV_NS_PREFIX = "http://iot.ee.surrey.ac.uk/smartcampus#";

//    protected String ONT_FILE = "web/ontologies/iot-lite.ttl";
    protected String ONT_FILE = "file:///C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/ontologies/iot-lite/iot-lite.ttl";
    protected String SPARQL_QUERY1 = "C:/Users/te0003/Documents/NetBeansProjects/OntologyEvaluator/src/main/webapp/query/sensors_declared.rq";
//    protected String SPARQL_QUERY1 = "C:/Users/te0003/Documents/NetBeansProjects/OntologyEvaluator/src/main/webapp/query/describe_sensors.rq";

    public void createDataset(long datasetSize) {

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.setStrictMode(true);
//        Model iotLiteOnt = FileManager.get().loadModel(BASE_ONT_PREFIX);
        Model iotLiteOnt = FileManager.get().loadModel(ONT_FILE);
        ontModel.add(iotLiteOnt);
        ontModel.setNsPrefix("sc", INDV_NS_PREFIX);
        ontModel.setNsPrefix("fiesta-res", DEREF_INDV_PREFIX);
        String M3_LITE_PREFIX = iotLiteOnt.getNsPrefixURI("m3-lite");
        String SSN_PREFIX = iotLiteOnt.getNsPrefixURI("ssn");
        String GEO_PREFIX = iotLiteOnt.getNsPrefixURI("geo");
        String QU_PREFIX = iotLiteOnt.getNsPrefixURI("qu");

        //classes
        OntClass deviceClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Device");
        OntClass sensingDevClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "SensingDevice");
        OntClass systemClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "System");
        OntClass platformClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Platform");
        OntClass locationClass = (OntClass) ontModel.getOntClass(GEO_PREFIX + "Point");
        OntClass typeClass = (OntClass) ontModel.getOntClass(QU_PREFIX + "QuantityKind");
        OntClass unitClass = (OntClass) ontModel.getOntClass(QU_PREFIX + "Unit");
        OntClass serviceClass = (OntClass) ontModel.getOntClass(IOT_LITE_PREFIX + "Service");
        OntClass coverageClass = (OntClass) ontModel.getOntClass(IOT_LITE_PREFIX + "Rectangle"); //Circle, Polygon
        
        //properties
        Property isSubSystemOf = ontModel.getProperty(IOT_LITE_PREFIX + "isSubSystemOf");
        Property hasSubSystem = ontModel.getProperty(SSN_PREFIX + "hasSubSystem");
        Property geoLocation = ontModel.getProperty(GEO_PREFIX + "location");
        Property geoLat = ontModel.getProperty(GEO_PREFIX + "lat");
        Property geoLong = ontModel.getProperty(GEO_PREFIX + "long");
        Property RelativeLocation = ontModel.getProperty(IOT_LITE_PREFIX + "RelativeLocation");
        Property onPlatform = ontModel.getProperty(SSN_PREFIX + "onPlatform");
        Property hasQuantityKind = ontModel.getProperty(IOT_LITE_PREFIX + "hasQuantityKind");
        Property hasUnit = ontModel.getProperty(IOT_LITE_PREFIX + "hasUnit");
        Property exposedBy = ontModel.getProperty(IOT_LITE_PREFIX + "exposedBy");
        Property endpoint = ontModel.getProperty(IOT_LITE_PREFIX + "endpoint");
        Property hasCoverage = ontModel.getProperty(IOT_LITE_PREFIX + "hasCoverage");
        Property hasPoint = ontModel.getProperty(IOT_LITE_PREFIX + "hasPoint");

        String[] quantityKinds = {"Temperature"};//, "Illuminance", "Sound", "Presence", "Power"};
        String[] units = {"DegreeCelsius", "Lux", "Decibel", "Boolean", "Watt"};
        final int sensorSetSize = quantityKinds.length;
//        long deskCount = 10; //200 //real number is 184
        long nodeCount = datasetSize / sensorSetSize;
        String deviceNamePrefix = "urn:x-iot:smartcampus:smart-ics:n";//"IoT-Node";
        String platformNamePrefix = "Desk";
        String locNamePrefix = "UK-GU-UNIS-ICS-" + platformNamePrefix;
//        String covNamePrefix = "Coverage";
        String servNamePrefix = "Service";
//        String endpointPrefix = "http://iot.ee.surrey.ac.uk/smart-ics/iot-node/";
        String endpointPrefix = "http://mu.tlmat.unican.es:8080/smart-ics/iot-node/";
        String separator = ".";

        Instant preDataGen = Instant.now();

        //system individual (smart-ics)
        Individual sicsSystemIndiv = ontModel.createIndividual(INDV_NS_PREFIX + "smart-ics", systemClass);

        //system individual (smart-campus)               
        Individual scSystemIndiv = ontModel.createIndividual(INDV_NS_PREFIX + "SmartCampus", systemClass);
        sicsSystemIndiv.setPropertyValue(isSubSystemOf, scSystemIndiv);

        for (long j = 1; j <= nodeCount; j++) {

            String deviceNumber = String.valueOf(j); //String.format("%03d", 1); e.g. "001"
            String devNumFormatted = String.format("%03d", j);

            //device individual                
            Individual deviceIndiv = ontModel.createIndividual(DEREF_INDV_PREFIX + deviceNamePrefix + devNumFormatted /*+ sensorNumber*/, deviceClass);
            deviceIndiv.setPropertyValue(isSubSystemOf, sicsSystemIndiv);

            for (int i = 0; i < sensorSetSize; i++) {

//                String sensorNumber = String.valueOf(i);
                String quantityKind = quantityKinds[i];//];
                String unit = units[i];//];

                //urn:x-iot:smart-ics:iot-node:1.Temperature
                //sensor individual
                Individual sensorDevIndiv = ontModel.createIndividual(DEREF_INDV_PREFIX + deviceNamePrefix + devNumFormatted + separator + quantityKind, sensingDevClass);
                sensorDevIndiv.setPropertyValue(isSubSystemOf, deviceIndiv);
                deviceIndiv.addProperty(hasSubSystem, sensorDevIndiv);

                //platform individual                
                Individual platformIndiv = ontModel.createIndividual(INDV_NS_PREFIX + platformNamePrefix + deviceNumber, platformClass);
                deviceIndiv.setPropertyValue(onPlatform, platformIndiv);

                //type individual
                Individual typeIndiv = ontModel.createIndividual(M3_LITE_PREFIX + quantityKind, typeClass);
                sensorDevIndiv.setPropertyValue(hasQuantityKind, typeIndiv);
                
                //type individual
                Individual unitIndiv = ontModel.createIndividual(M3_LITE_PREFIX + unit, unitClass);
                sensorDevIndiv.setPropertyValue(hasUnit, unitIndiv);

                //smart-ics area
                //51.243512, -0.592964 (NE)               
                //51.243223, -0.593416 (SW)
                double lat = ThreadLocalRandom.current().nextDouble(51.243223, 51.243512);
                double lon = ThreadLocalRandom.current().nextDouble(-0.593416, -0.592964);

                //location individual
                Individual locationIndiv = ontModel.createIndividual(INDV_NS_PREFIX + locNamePrefix + deviceNumber, locationClass);
                platformIndiv.setPropertyValue(geoLocation, locationIndiv);
                locationIndiv.setPropertyValue(RelativeLocation, ontModel.createLiteral(platformNamePrefix + deviceNumber));
                locationIndiv.setPropertyValue(geoLat, ontModel.createLiteral(String.valueOf(lat)));
                locationIndiv.setPropertyValue(geoLong, ontModel.createLiteral(String.valueOf(lon)));

                //service individual
                Individual serviceIndiv = ontModel.createIndividual(INDV_NS_PREFIX + deviceNamePrefix + devNumFormatted + servNamePrefix + quantityKind, serviceClass);
                sensorDevIndiv.setPropertyValue(exposedBy, serviceIndiv);
                serviceIndiv.setPropertyValue(endpoint, ontModel.createLiteral(endpointPrefix + deviceNumber + "/" + quantityKind.toLowerCase()));

                //coverage points individuals
//                Individual coverageLocIndiv1 = ontModel.createIndividual(INDV_NS + covNamePrefix +"1"+  locNamePrefix + deskNumber, locationClass);
//                coverageLocIndiv1.setPropertyValue(ontModel.getProperty(iotLiteOnt.getNsPrefixURI("geo") + "lat"), ontModel.createLiteral("51.401"));
//                coverageLocIndiv1.setPropertyValue(ontModel.getProperty(iotLiteOnt.getNsPrefixURI("geo") + "long"), ontModel.createLiteral("-0.511"));
//            
//                Individual coverageLocIndiv2 = ontModel.createIndividual(INDV_NS + covNamePrefix +"2"+ locNamePrefix + deskNumber, locationClass);
//                coverageLocIndiv2.setPropertyValue(ontModel.getProperty(iotLiteOnt.getNsPrefixURI("geo") + "lat"), ontModel.createLiteral("51.402"));
//                coverageLocIndiv2.setPropertyValue(ontModel.getProperty(iotLiteOnt.getNsPrefixURI("geo") + "long"), ontModel.createLiteral("-0.512"));
//                
//                //coverage individual
//                Individual coverageIndiv = ontModel.createIndividual(INDV_NS + covNamePrefix + deskNumber, coverageClass);
//                sensorIndiv.setPropertyValue(ontModel.getProperty(BASE_ONT_NS + "hasCoverage"), coverageIndiv); 
//                coverageIndiv.setPropertyValue(ontModel.getProperty(BASE_ONT_NS + "hasPoint"), coverageLocIndiv1);
//                coverageIndiv.addProperty(ontModel.getProperty(BASE_ONT_NS + "hasPoint"), coverageLocIndiv2);
            }

        }

//        }
        Instant postDataGen = Instant.now();
        final long dgDuration = postDataGen.toEpochMilli() - preDataGen.toEpochMilli();

//        System.out.println("\nModel Individuals");
//        System.out.println("--------------------------------------------------");
//        ExtendedIterator<Individual> iter3 = ontModel.listIndividuals();
//        while (iter3.hasNext()) {
//            Individual ontIndiv = iter3.next();
//            System.out.println(ontIndiv.getURI());
//        }
//        System.out.println("Model Instances");
//        System.out.println("--------------------------------------------------");
//        ontModel.write(System.out, "RDF/XML-ABBREV");
//        ontModel.write(System.out, "RDF/XML");
//        ontModel.write(System.out, "TURTLE");
//        ontModel.write(System.out, "JSON-LD");
        Model mIndividuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        mIndividuals.setNsPrefixes(ontModel.getNsPrefixMap());
        ExtendedIterator<Individual> iterIndv = ontModel.listIndividuals();

        while (iterIndv.hasNext()) {
            Individual indv = (Individual) iterIndv.next();
            mIndividuals.add(indv.getOntModel());

        }
//        mIndividuals.remove(iotLiteOnt).write(System.out, "TURTLE");
  mIndividuals.remove(iotLiteOnt).write(System.out, "JSON-LD");  
//  mIndividuals.remove(iotLiteOnt).write(System.out, "RDF/XML");

    }

    public static void main(String[] args) throws JAXBException {

        DescriptionGenerator_old ri = new DescriptionGenerator_old();
        long datasetSize = 1;
        ri.createDataset(datasetSize);
    }

}

//        ValidityReport validity = ontModel.validate();
//        if (validity.isValid()) {
//            System.out.println("OK");
//        } else {
//            System.out.println("Conflicts");
//            for (Iterator i = validity.getReports(); i.hasNext();) {
//                System.out.println(" - " + i.next());
//            }
//        }
