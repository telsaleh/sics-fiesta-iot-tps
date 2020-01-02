/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.surrey.ee.iot.smartics.annotator.fiesta;

import java.io.ByteArrayOutputStream;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import static org.apache.jena.datatypes.xsd.XSDDatatype.XSDdateTime;
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
import org.hashids.Hashids;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Observation;
import uk.ac.surrey.ee.iot.smartics.model.data.ics.Observations;

/**
 *
 * @author te0003
 */
public class FiestaObsAnnotator {

    public FiestaObsAnnotator() {
    }

    //reference ontologies
    public String BASE_ONTOLOGY = "/ontologies/fiesta_iot/fiesta-iot.owl";

    //prefixes
    public String INDV_NAMESPACE = "http://smart-ics.ee.surrey.ac.uk/fiesta-iot/";

    //individual prefixes (instances)
    public String sensingDevNamePrefix = "resource/";
    public String observationNamePrefix = "observation#";
    public String obsValueNamePrefix = "observationValue#";
    public String sensorOutputNamePrefix = "sensorOutput#";
    public String obsPropNamePrefix = "observationProperty#";
    public String timeIntNamePrefix = "timeInterval#";
    public String unitPrefix = "unit#";
    public String locNamePrefix = "loc#";

    //location
    public String LOCATION_PREFIX = "UNIVERSITY_OF_SURREY-";
    public String RELATIVE_LOCATION = "http://sws.geonames.org/6695971/";

    //time
    public String TIME_ZONE_PREFIX = "UTC_"; //Calendar.getInstance().getTimeZone().

    public String annotateObservations(Observations obs) {

        //instantiate models
        String ontFilePath = "";
        ontFilePath = getClass().getResource(BASE_ONTOLOGY).toString();
        Model tpsModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        Model fiestaOnt = FileManager.get().loadModel(ontFilePath);

        try {
            //iterate over observations
            for (Observation ob : obs.getObservations()) {

                try {
                    OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
                    ontModel.setStrictMode(true);
                    ontModel.add(fiestaOnt);

                    //ontology prefixes
                    ontModel.setNsPrefix("sics", INDV_NAMESPACE);
                    String IOT_LITE_PREFIX = fiestaOnt.getNsPrefixURI("iot-lite");
                    String M3_LITE_PREFIX = fiestaOnt.getNsPrefixURI("mthreelite");
                    String SSN_PREFIX = fiestaOnt.getNsPrefixURI("ssn");
                    String GEO_PREFIX = fiestaOnt.getNsPrefixURI("geo");
                    String DUL_PREFIX = ontModel.getNsPrefixURI("dul");
                    String TIME_PREFIX = ontModel.getNsPrefixURI("time");

                    //classes
                    OntClass observationClass = null;
                    OntClass sensingDevClass = null;
                    OntClass sensorOutputClass = null;
                    OntClass observationValueClass = null;
                    OntClass unitClass = null;
                    OntClass qkClass = null;

                    OntClass geoPointClass = null;
                    OntClass timeIntervalClass = null;

                    //object properties
                    Property observedBy = null;
                    Property observedProperty = null;
                    Property geoLocation = null;
                    Property hasUnit = null;
                    Property observationResult = null;
                    Property obserationSamplingTime = null;
                    Property hasValue = null;

                    //datatype properties
                    Property geoLat = null;
                    Property geoLong = null;
                    Property geoAlt = null;
                    Property geoAltRel = null;
                    Property geoRelLoc = null;
                    Property inXSDDateTime = null;
                    Property hasDataValue = null;

                    try {
                        observationClass = ontModel.getOntResource(SSN_PREFIX + "Observation").asClass(); //ok
//                        System.out.println(M3_LITE_PREFIX+ob.getIotType());
                        sensingDevClass = ontModel.getOntResource(M3_LITE_PREFIX + ob.getIotType()).asClass(); //ok
                        sensorOutputClass = ontModel.getOntResource(SSN_PREFIX + "SensorOutput").asClass(); //ok
                        observationValueClass = ontModel.getOntResource(SSN_PREFIX + "ObservationValue").asClass(); //ok
                        unitClass = ontModel.getOntResource(M3_LITE_PREFIX + ob.getUnit()).asClass(); //ok
                        qkClass = ontModel.getOntResource(M3_LITE_PREFIX + ob.getQk()).asClass(); //ok

                        geoPointClass = ontModel.getOntResource(GEO_PREFIX + "Point").asClass(); //ok
                        timeIntervalClass = ontModel.getOntResource(TIME_PREFIX + "Instant").asClass();

                        //object properties
                        observedBy = ontModel.getObjectProperty(SSN_PREFIX + "observedBy").asObjectProperty(); //ok
                        observedProperty = ontModel.getObjectProperty(SSN_PREFIX + "observedProperty").asObjectProperty(); //ok
                        geoLocation = ontModel.getObjectProperty(GEO_PREFIX + "location").asObjectProperty(); //ok
                        hasUnit = ontModel.getObjectProperty(IOT_LITE_PREFIX + "hasUnit").asObjectProperty(); //ok
                        observationResult = ontModel.getObjectProperty(SSN_PREFIX + "observationResult").asObjectProperty(); //ok
                        obserationSamplingTime = ontModel.getObjectProperty(SSN_PREFIX + "observationSamplingTime").asObjectProperty(); //ok
                        hasValue = ontModel.getObjectProperty(SSN_PREFIX + "hasValue").asObjectProperty();

                        //datatype properties                    
                        inXSDDateTime = ontModel.getDatatypeProperty(TIME_PREFIX + "inXSDDateTime").asDatatypeProperty(); //ok
                        hasDataValue = ontModel.getDatatypeProperty(DUL_PREFIX + "hasDataValue").asDatatypeProperty(); //ok

                        //annotation properties                    
                        geoLat = ontModel.getAnnotationProperty(GEO_PREFIX + "lat").asAnnotationProperty(); //ok
                        geoLong = ontModel.getAnnotationProperty(GEO_PREFIX + "long").asAnnotationProperty(); //ok
                        geoAlt = ontModel.getAnnotationProperty(GEO_PREFIX + "alt").asAnnotationProperty(); //ok
                        geoAltRel = ontModel.getAnnotationProperty(IOT_LITE_PREFIX + "altRelative").asAnnotationProperty(); //ok
                        geoRelLoc = ontModel.getAnnotationProperty(IOT_LITE_PREFIX + "relativeLocation").asAnnotationProperty(); //ok
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                        System.out.println("Check Ontology Resources");
                    }

                    //sensing device
                    String sensingDevUri = INDV_NAMESPACE + sensingDevNamePrefix + ob.getResourceId();
                    Individual sensorDevIndiv = ontModel.createIndividual(sensingDevUri, sensingDevClass);

                    //generate unique hash id for observations, sensor outputs, observation values and time instants.
                    String obsInstanceId = ob.getResourceId().concat("@" + ob.getTimestamp());
                    Hashids obsHashId = new Hashids(obsInstanceId, 10);
                    Hashids timeHashId = new Hashids(ob.getTimestamp(), 10);
                    String obsInstanceHash = obsHashId.encode(1L);
                    String obsTimeHash = timeHashId.encode(1L);

                    //observation 
                    String observationUri = INDV_NAMESPACE + observationNamePrefix + obsInstanceHash;
                    Individual observationIndiv = ontModel.createIndividual(observationUri, observationClass);
                    observationIndiv.setPropertyValue(observedBy, sensorDevIndiv);

                    //sensor output
                    String sensorOutputUri = INDV_NAMESPACE + sensorOutputNamePrefix + obsInstanceHash; //is SensorOutput unique for each observations?
                    Individual sensorOutputIndiv = ontModel.createIndividual(sensorOutputUri, sensorOutputClass);
                    observationIndiv.setPropertyValue(observationResult, sensorOutputIndiv);

                    //observation Value
                    String obsValueUri = INDV_NAMESPACE + obsValueNamePrefix + obsInstanceHash;
                    Individual obsValueIndiv = ontModel.createIndividual(obsValueUri, observationValueClass);
                    sensorOutputIndiv.setPropertyValue(hasValue, obsValueIndiv);
                    String dataValue = ob.getDataValue();
                    if (dataValue.contains("NaN")) {
                        obsValueIndiv.setPropertyValue(hasDataValue, ontModel.createTypedLiteral(ob.getDataValue(), XSDDatatype.XSDstring));
                    } else {
                        obsValueIndiv.setPropertyValue(hasDataValue, ontModel.createTypedLiteral(ob.getDataValue(), XSDDatatype.XSDdouble));
                    }

                    //observed property
                    String obsPropUri = INDV_NAMESPACE + obsPropNamePrefix + ob.getQk();
                    Individual obsPropIndiv = ontModel.createIndividual(obsPropUri, qkClass);
                    observationIndiv.setPropertyValue(observedProperty, obsPropIndiv);

                    //time interval                    
                    String timeIntUri = INDV_NAMESPACE + timeIntNamePrefix + TIME_ZONE_PREFIX + obsTimeHash;
                    Individual timeIntervalIndiv = ontModel.createIndividual(timeIntUri, timeIntervalClass);
                    observationIndiv.setPropertyValue(obserationSamplingTime, timeIntervalIndiv);
                    timeIntervalIndiv.setPropertyValue(inXSDDateTime, ontModel.createTypedLiteral(ob.getTimestamp(), XSDdateTime));

                    //unit individual
                    String unitUri = INDV_NAMESPACE + unitPrefix + ob.getUnit();
                    Individual unitIndiv = ontModel.createIndividual(unitUri, unitClass);
                    obsValueIndiv.setPropertyValue(hasUnit, unitIndiv);

                    //location individual
                    String locationUri = INDV_NAMESPACE + locNamePrefix + LOCATION_PREFIX + ob.getPlatform();
                    Individual locationIndiv = ontModel.createIndividual(locationUri, geoPointClass);
                    locationIndiv.setPropertyValue(geoLat, ontModel.createTypedLiteral(ob.getLat(), XSDDatatype.XSDdouble));
                    locationIndiv.setPropertyValue(geoLong, ontModel.createTypedLiteral(ob.getLon(), XSDDatatype.XSDdouble));
                    locationIndiv.setPropertyValue(geoAlt, ontModel.createTypedLiteral(ob.getAlt(), XSDDatatype.XSDdouble));
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
                    mIndividuals.remove(fiestaOnt);
                    tpsModel.add(mIndividuals);
//                    tpsModel.add(ontModel);

                } catch (NullPointerException npe) {
                    System.out.println("null returned from smart-ics...");
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("null returned from smart-ics1...");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        tpsModel.write(bos, RDFLanguages.strLangJSONLD);
        String sicsAnnotated = bos.toString();

        return sicsAnnotated;
    }
    
}
