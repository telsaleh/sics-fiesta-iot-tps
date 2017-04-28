package uk.ac.surrey.ee.iot.smartics.annotator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import javax.xml.bind.JAXBException;
import org.apache.jena.datatypes.xsd.XSDDatatype;
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
import uk.ac.surrey.ee.iot.smartics.model.proprietary.Resource;
import uk.ac.surrey.ee.iot.smartics.model.proprietary.Resources;

/**
 *
 * @author te0003
 */
public class FiestaResAnnotator {

    //reference ontologies
    public String FIESTA_ONT_FILE_RUN = "file:///C://Users/te0003/Documents/NetBeansProjects/SmartICS/src/main/webapp/ontologies/fiesta-iot/fiesta-iot.owl";
    public String FIESTA_ONT_FILE_DEPLOY = "/ontologies/fiesta-iot/fiesta-iot.owl";

    //prefixes
    public static String INDV_NS_PREFIX = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/";
    public static String sensingDevNamePrefix = "resource/";

    public String systemNamePrefix = "system#";
    public String deploymentNamePrefix = "deployment#";
    public String deviceNamePrefix = "device#";
    public String doiPrefix = "doi#";
    public String platformNamePrefix = "platform#";
    public String qkPrefix = "qk#";
    public String unitPrefix = "unit#";
    public String locNamePrefix = "loc#";
    public String servNamePrefix = "service#";
    public String servPathPrefix = "service/";

//    public String ENDPOINT_PREFIX = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/service/";
    //location
    public String LOCATION = "UNIVERSITY_OF_SURREY-";
    public String RELATIVE_LOCATION = "http://sws.geonames.org/6695971/";

    public FiestaResAnnotator() {
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
        Model fiestaOnt = FileManager.get().loadModel(ontFilePath);

        for (Resource res : resources.getResources()) {
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            ontModel.setStrictMode(true);
            ontModel.add(fiestaOnt);
//            ontModel.setNsPrefixes(fiestaOnt.getNsPrefixMap());

            //prefixes
            ontModel.setNsPrefix("sics", INDV_NS_PREFIX);
            String IOT_LITE_PREFIX = fiestaOnt.getNsPrefixURI("iot-lite");
            String M3_LITE_PREFIX = fiestaOnt.getNsPrefixURI("mthreelite");
//            String QU_PREFIX = fiestOnt.getNsPrefixURI("qu");
            String SSN_PREFIX = fiestaOnt.getNsPrefixURI("ssn");
            String GEO_PREFIX = fiestaOnt.getNsPrefixURI("geo");
            String DUL_PREFIX = ontModel.getNsPrefixURI("dul");
            String TIME_PREFIX = ontModel.getNsPrefixURI("time");
            //classes
            OntClass deviceClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Device");
            OntClass sensingDevClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + res.getIotType());
            OntClass systemClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "System");
            OntClass platformClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Platform");
            OntClass deploymentClass = (OntClass) ontModel.getOntClass(SSN_PREFIX + "Deployment");
            OntClass locationClass = (OntClass) ontModel.getOntClass(GEO_PREFIX + "Point");
            OntClass typeClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + res.getQk());
            OntClass unitClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + res.getUnit());
            OntClass doiClass = (OntClass) ontModel.getOntClass(M3_LITE_PREFIX + "BuildingAutomation");
            OntClass serviceClass = (OntClass) ontModel.getOntClass(IOT_LITE_PREFIX + "Service");
            OntClass coverageClass = (OntClass) ontModel.getOntClass(IOT_LITE_PREFIX + "Rectangle"); //Circle, Polygon
            //properties
            Property isSubSystemOf = ontModel.getProperty(IOT_LITE_PREFIX + "isSubSystemOf");
            Property hasSubSystem = ontModel.getProperty(SSN_PREFIX + "hasSubSystem");
            Property geoLocation = ontModel.getProperty(GEO_PREFIX + "location");
            Property geoLat = ontModel.getProperty(GEO_PREFIX + "lat");
            Property geoLong = ontModel.getProperty(GEO_PREFIX + "long");
            Property RelativeLocation = ontModel.getProperty(IOT_LITE_PREFIX + "relativeLocation");
            Property hasDomainOfInterest = ontModel.getProperty(M3_LITE_PREFIX + "hasDomainOfInterest");
            Property onPlatform = ontModel.getProperty(SSN_PREFIX + "onPlatform");
            Property hasDeployment = ontModel.getProperty(SSN_PREFIX + "hasDeployment");
            Property hasQuantityKind = ontModel.getProperty(IOT_LITE_PREFIX + "hasQuantityKind");
            Property hasUnit = ontModel.getProperty(IOT_LITE_PREFIX + "hasUnit");
            Property exposedBy = ontModel.getProperty(IOT_LITE_PREFIX + "exposedBy");
            Property endpoint = ontModel.getProperty(IOT_LITE_PREFIX + "endpoint");
            Property interfaceType = ontModel.getProperty(IOT_LITE_PREFIX + "interfaceType");
            Property hasCoverage = ontModel.getProperty(IOT_LITE_PREFIX + "hasCoverage");
            Property hasPoint = ontModel.getProperty(IOT_LITE_PREFIX + "hasPoint"); //used for coverage only
            Property isMobile = ontModel.getProperty(IOT_LITE_PREFIX + "isMobile");
            Property isOnline = ontModel.getProperty(IOT_LITE_PREFIX + "isOnline");

            //system individual (smart-building)
            String sBuildingSystemIndivUri = INDV_NS_PREFIX + systemNamePrefix + res.getSystem();
            Individual sBuildingSystemIndiv = ontModel.createIndividual(sBuildingSystemIndivUri, systemClass);
            //system individual (smart-campus)
            String scSystemIndivUri = INDV_NS_PREFIX + systemNamePrefix + "smart-campus";
            Individual scSystemIndiv = ontModel.createIndividual(scSystemIndivUri, systemClass);
            sBuildingSystemIndiv.setPropertyValue(isSubSystemOf, scSystemIndiv);
            scSystemIndiv.setPropertyValue(hasSubSystem, sBuildingSystemIndiv);
            //deployment individual (smart-ICS)
            String deploymentIndivUri = INDV_NS_PREFIX + deploymentNamePrefix + res.getDeployment();
            Individual deploymentIndiv = ontModel.createIndividual(deploymentIndivUri, deploymentClass);
//            sBuildingSystemIndiv.setPropertyValue(hasDeployment, deploymentIndiv);
            //device individual
            String deviceIndivUri = INDV_NS_PREFIX + deviceNamePrefix + res.getDeviceId();
            Individual deviceIndiv = ontModel.createIndividual(deviceIndivUri, deviceClass);
            deviceIndiv.setPropertyValue(isSubSystemOf, sBuildingSystemIndiv);
            deviceIndiv.setPropertyValue(hasDeployment, deploymentIndiv);
            sBuildingSystemIndiv.setPropertyValue(hasSubSystem, deviceIndiv);
            //domain of interest individual
            String doiIndivUri = INDV_NS_PREFIX + doiPrefix + "BuildingAutomation";
            Individual doiIndiv = ontModel.createIndividual(doiIndivUri, doiClass);
            deviceIndiv.setPropertyValue(hasDomainOfInterest, doiIndiv);
            //sensing device individual
            String sensorDevIndivUri = INDV_NS_PREFIX + sensingDevNamePrefix + res.getResourceId();
            Individual sensorDevIndiv = ontModel.createIndividual(sensorDevIndivUri, sensingDevClass);
            sensorDevIndiv.setPropertyValue(isSubSystemOf, deviceIndiv);
            deviceIndiv.addProperty(hasSubSystem, sensorDevIndiv);
            //platform individual
            String platformIndivUri = INDV_NS_PREFIX + platformNamePrefix + res.getPlatform();
            Individual platformIndiv = ontModel.createIndividual(platformIndivUri, platformClass);
            platformIndiv.setPropertyValue(isMobile, ontModel.createTypedLiteral(Boolean.parseBoolean(res.getMobile()), XSDDatatype.XSDboolean));
            deviceIndiv.setPropertyValue(onPlatform, platformIndiv);
            //qk individual
            String qkIndivUri = INDV_NS_PREFIX + qkPrefix + res.getQk();
            Individual qkIndiv = ontModel.createIndividual(qkIndivUri, typeClass);
            sensorDevIndiv.setPropertyValue(hasQuantityKind, qkIndiv);
            //unit individual
            String unitIndivUri = INDV_NS_PREFIX + unitPrefix + res.getUnit();
            Individual unitIndiv = ontModel.createIndividual(unitIndivUri, unitClass);
            sensorDevIndiv.setPropertyValue(hasUnit, unitIndiv);

            //smart-ics area
            //51.243503, -0.593393 (NW)         51.243502, -0.592990 (NE)
            //51.243378, -0.593393 (W)          51.243376, -0.592990 (E)
            //51.243253, -0.593393 (SW)         51.243250, -0.592990 (SE)
            //
            // double lat = ThreadLocalRandom.current().nextDouble(51.243223, 51.243512);
            // double lon = ThreadLocalRandom.current().nextDouble(-0.593416, -0.592964);
            //location individual
            String locationIndivUri = INDV_NS_PREFIX + locNamePrefix + LOCATION + res.getPlatform();
            Individual locationIndiv = ontModel.createIndividual(locationIndivUri, locationClass);
            platformIndiv.setPropertyValue(geoLocation, locationIndiv);
            locationIndiv.setPropertyValue(RelativeLocation, ontModel.createLiteral(RELATIVE_LOCATION));
            locationIndiv.setPropertyValue(geoLat, ontModel.createTypedLiteral(res.getLat(), XSDDatatype.XSDdouble));
            locationIndiv.setPropertyValue(geoLong, ontModel.createTypedLiteral(res.getLon(), XSDDatatype.XSDdouble));
            //service individual
            String serviceIndivUri = INDV_NS_PREFIX + servPathPrefix + res.getResourceId();
            Individual serviceIndiv = ontModel.createIndividual(serviceIndivUri, serviceClass);
            sensorDevIndiv.setPropertyValue(exposedBy, serviceIndiv);
            serviceIndiv.setPropertyValue(endpoint, ontModel.createTypedLiteral(INDV_NS_PREFIX + servPathPrefix + res.getResourceId().toLowerCase(), XSDDatatype.XSDanyURI));
            serviceIndiv.setPropertyValue(interfaceType, ontModel.createTypedLiteral("RESTful"));
//            serviceIndiv.setPropertyValue(isOnline, ontModel.createLiteral(Boolean.parse(res.getIsOnline())));

            //extract prefixes and instances
            Model mIndividuals = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            mIndividuals.setNsPrefixes(ontModel.getNsPrefixMap());
            ExtendedIterator<Individual> iterIndv = ontModel.listIndividuals();
            while (iterIndv.hasNext()) {
                Individual indv = (Individual) iterIndv.next();
                mIndividuals.add(indv.getOntModel());
            }
            mIndividuals.remove(fiestaOnt);//.write(System.out, "JSON-LD");
            tpsModel.add(mIndividuals);
//            tpsModel.add(ontModel);
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
                + "		\"resourceId\": \"sc-sics-sp-001-power\",\n"
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
        FiestaResAnnotator ri = new FiestaResAnnotator();
        String result = ri.annotateResources(res);
        System.out.println(result);
    }
}
