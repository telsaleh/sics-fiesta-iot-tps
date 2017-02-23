package uk.ac.surrey.ee.iot.smartics.annotator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
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
import uk.ac.surrey.ee.iot.smartics.model.ics.Resource;
import uk.ac.surrey.ee.iot.smartics.model.ics.Resources;

/**
 *
 * @author te0003
 */
public class FiestaRegistrar {

    //reference ontologies
    public String FIESTA_ONT_FILE_RUN = "file:///C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/ontologies/fiesta-iot/fiesta-iot.owl";
    public String FIESTA_ONT_FILE_DEPLOY = "/ontologies/fiesta-iot/fiesta-iot.owl";

    //prefixes
    public String INDV_NS_PREFIX = "http://iot.ee.surrey.ac.uk/smartcampus#";
    public String ENDPOINT_PREFIX = "http://iot.ee.surrey.ac.uk/smart-ics/";

    //location
    public String LOCATION = "ICS";
    public String RELATIVE_LOCATION = "http://sws.geonames.org/6695971/";

    public FiestaRegistrar() {
    }

    public String annotateResources(Resources resources) {

        //instantiate models
        String ontFilePath = "";
        try {
            ontFilePath = DefaultServletListener.servletContext.getRealPath(FIESTA_ONT_FILE_DEPLOY);
        } catch (NoClassDefFoundError fe) {
            ontFilePath = FIESTA_ONT_FILE_RUN;
        }
        Model tpsModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        Model fiestOnt = FileManager.get().loadModel(ontFilePath);

        for (Resource res : resources.getResources()) {
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            ontModel.setStrictMode(true);
            ontModel.add(fiestOnt);
            //ontology prefixes
            ontModel.setNsPrefix("sc", INDV_NS_PREFIX);
            ontModel.setNsPrefix("dul", "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#");
            ontModel.setNsPrefix("time", "http://www.w3.org/2006/time#");
            String IOT_LITE_PREFIX = fiestOnt.getNsPrefixURI("iot-lite");
            String M3_LITE_PREFIX = fiestOnt.getNsPrefixURI("m3-lite");
            String QU_PREFIX = fiestOnt.getNsPrefixURI("qu");
            String SSN_PREFIX = fiestOnt.getNsPrefixURI("ssn");
            String GEO_PREFIX = fiestOnt.getNsPrefixURI("geo");
            String DUL_PREFIX = ontModel.getNsPrefixURI("dul");
            String TIME_PREFIX = ontModel.getNsPrefixURI("time");
            //classes
            OntClass deviceClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Device");
            OntClass sensingDevClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "SensingDevice");
            OntClass systemClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "System");
            OntClass platformClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Platform");
            OntClass deploymentClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Deployment");
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
            Property RelativeLocation = ontModel.getProperty(IOT_LITE_PREFIX + "relativeLocation");
            Property onPlatform = ontModel.getProperty(SSN_PREFIX + "onPlatform");
            Property hasDeployment = ontModel.getProperty(SSN_PREFIX + "hasDeployment");
            Property hasQuantityKind = ontModel.getProperty(IOT_LITE_PREFIX + "hasQuantityKind");
            Property hasUnit = ontModel.getProperty(IOT_LITE_PREFIX + "hasUnit");
            Property exposedBy = ontModel.getProperty(IOT_LITE_PREFIX + "exposedBy");
            Property endpoint = ontModel.getProperty(IOT_LITE_PREFIX + "endpoint");
            Property hasCoverage = ontModel.getProperty(IOT_LITE_PREFIX + "hasCoverage");
            Property hasPoint = ontModel.getProperty(IOT_LITE_PREFIX + "hasPoint"); //used for coverage only
            //individual prefixes (instances)
            String deviceNamePrefix = "urn:x-iot:s-ics:";
            String platformNamePrefix = "urn:x-iot:s-ics:plat:";
            String servNamePrefix = "urn:x-iot:s-ics:serivce:";
            String endpointPrefix = "urn:x-iot:s-ics:endp:";
            String unitPrefix = "urn:x-iot:s-ics:unit:";
            String locNamePrefix = "urn:x-iot:s-ics:loc:";
            Instant preDataGen = Instant.now();
            //system individual (smart-building)
            Individual sBuildingSystemIndiv = ontModel.createIndividual(INDV_NS_PREFIX + "SmartBuilding", systemClass);
            //system individual (smart-campus)
            Individual scSystemIndiv = ontModel.createIndividual(INDV_NS_PREFIX + "SmartCampus", systemClass);
            sBuildingSystemIndiv.setPropertyValue(isSubSystemOf, scSystemIndiv);
            //deployment individual (smart-ICS)
            //device individual
            Individual deploymentIndiv = ontModel.createIndividual(INDV_NS_PREFIX + "Smart-ICS", deploymentClass);
            sBuildingSystemIndiv.setPropertyValue(hasDeployment, deploymentIndiv);
            Individual deviceIndiv = ontModel.createIndividual(INDV_NS_PREFIX + deviceNamePrefix + res.getDeviceId(), deviceClass);
            deviceIndiv.setPropertyValue(isSubSystemOf, sBuildingSystemIndiv);
            //urn:x-iot:smart-ics:iot-node:1.Temperature
            //sensor individual
            Individual sensorDevIndiv = ontModel.createIndividual(INDV_NS_PREFIX + deviceNamePrefix + res.getResourceId(), sensingDevClass);
            sensorDevIndiv.setPropertyValue(isSubSystemOf, deviceIndiv);
            deviceIndiv.addProperty(hasSubSystem, sensorDevIndiv);
            //platform individual
            Individual platformIndiv = ontModel.createIndividual(INDV_NS_PREFIX + res.getPlatform(), platformClass);
            deviceIndiv.setPropertyValue(onPlatform, platformIndiv);
            //qk individual
            Individual qkIndiv = ontModel.createIndividual(M3_LITE_PREFIX + res.getQk(), typeClass);
            sensorDevIndiv.setPropertyValue(hasQuantityKind, qkIndiv);
            //unit individual
            Individual unitIndiv = ontModel.createIndividual(M3_LITE_PREFIX + res.getUnit(), unitClass);
            sensorDevIndiv.setPropertyValue(hasUnit, unitIndiv);
            //smart-ics area
            //51.243503, -0.593393 (NW)         51.243502, -0.592990 (NE)
            //51.243441, -0.593393              51.243439, -0.592990
            //51.243378, -0.593393 (W)          51.243376, -0.592990 (E)
            //51.243
            //51.243253, -0.593393 (SW)         51.243250, -0.592990 (SE)
            //
//            double lat = ThreadLocalRandom.current().nextDouble(51.243223, 51.243512);
//            double lon = ThreadLocalRandom.current().nextDouble(-0.593416, -0.592964);
//location individual
Individual locationIndiv = ontModel.createIndividual(INDV_NS_PREFIX + locNamePrefix + LOCATION, locationClass);
platformIndiv.setPropertyValue(geoLocation, locationIndiv);
locationIndiv.setPropertyValue(RelativeLocation, ontModel.createLiteral(RELATIVE_LOCATION));
locationIndiv.setPropertyValue(geoLat, ontModel.createLiteral(res.getLat()));
locationIndiv.setPropertyValue(geoLong, ontModel.createLiteral(res.getLon()));
//service individual
Individual serviceIndiv = ontModel.createIndividual(INDV_NS_PREFIX + servNamePrefix + res.getResourceId(), serviceClass);
sensorDevIndiv.setPropertyValue(exposedBy, serviceIndiv);
serviceIndiv.setPropertyValue(endpoint, ontModel.createLiteral(endpointPrefix + res.getDeviceId().toLowerCase() + "/" + res.getQk().toLowerCase()));
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
//        }
//            Instant postDataGen = Instant.now();
//            final long dgDuration = postDataGen.toEpochMilli() - preDataGen.toEpochMilli();
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
mIndividuals.remove(fiestOnt);//.write(System.out, "JSON-LD");
tpsModel.add(mIndividuals);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        tpsModel.write(bos, RDFLanguages.strLangJSONLD);
        String sicsAnnotated = bos.toString();

        return sicsAnnotated;
    }

    public static void main(String[] args) throws JAXBException, IOException {

        String test = "{\n"
                + "	\"Resources\": [{\n"
                + "		\"iot-type\": \"unis-smart-campus\",\n"
                + "		\"mobile\": \"False\",\n"
                + "		\"resourceId\": \"sc-sics-sp-001-Power\",\n"
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
        Resources res = objectMapper.readValue(test, Resources.class);

//        Observation ob = new Observation("sc-si-sc-001-temp", "sc-si-sc-001", "smart-ics", "desk-ICS-02-18", "false", "unis-smart-campus", "sensor", "AmbientTemperature", "Celsius", "automatic", "51.1", "-0.1", "50", "2", "2002-05-30T09:30:10.5", "23.1");
//        ArrayList<Observation> obsArray = new ArrayList<>();
//        obsArray.add(ob);
//        Observations obs = new Observations(); 
//        obs.setObservations(obsArray);
        FiestaRegistrar ri = new FiestaRegistrar();
        String result = ri.annotateResources(res);
        System.out.println(result);
    }
}
