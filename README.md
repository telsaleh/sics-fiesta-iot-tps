# FIESTA-IoT Testbed Provider Service (TPS)

This is the reference implementation of the TPS for federating the SmartICS Testbed at the University of Surrey with the FIESTA-IoT Platform. 

The service is also available for independent data consumers, but must comply with the API specification defined below. Data is currently represented only in JSON-LD.

## API specification  

* [getAllResources](#getallresources)  
* [getResource](#getresource-dereferenceable)  
* [getLastObservations](#getlastobservations)  
* [getLastObservation](#getlastobservation)  
* [getObservations](#getobservations)  
* [getObservation](#getobservation)  

## getAllResources 

This method allows a data consumer to retrieve a list of all registered resources.  

``` 
GET http://{hostname}/fiesta-iot/registry/getAllResources 
```  

Response (example):  

```json 
{
    "@graph": [
        {
            "@id": "sics:deployment#smart-ics",
            "@type": "ssn:Deployment"
        },
        {
            "@id": "sics:device#sc-sics-sp-001",
            "@type": "ssn:Device",
            "isSubSystemOf": "sics:system#unis-smart-building",
            "hasDeployment": "sics:deployment#smart-ics",
            "hasSubSystem": "sics:resource/sc-sics-sp-001-power",
            "onPlatform": "sics:platform#desk-ICS-02-11",
            "hasDomainOfInterest": "sics:doi#BuildingAutomation"
        },
        {
            "@id": "sics:doi#BuildingAutomation",
            "@type": "mthreelite:BuildingAutomation"
        },
        {
            "@id": "sics:loc#UNIVERSITY_OF_SURREY",
            "@type": "geo:Point",
            "iot-lite:relativeLocation": "http://sws.geonames.org/6695971/",
            "geo:lat": "51.2433445",
            "geo:long": "-0.5932438"
        },
        {
            "@id": "sics:platform#desk-ICS-02-11",
            "@type": "ssn:Platform",
            "iot-lite:isMobile": "False",
            "location": "sics:loc#UNIVERSITY_OF_SURREY"
        },
        {
            "@id": "sics:qk#Power",
            "@type": "mthreelite:Power"
        },
        {
            "@id": "sics:resource/sc-sics-sp-001-power",
            "@type": "mthreelite:EnergyMeter",
            "exposedBy": "sics:service/sc-sics-sp-001-power",
            "hasQuantityKind": "sics:qk#Power",
            "hasUnit": "sics:unit#Watt",
            "isSubSystemOf": "sics:device#sc-sics-sp-001"
        },
        {
            "@id": "sics:service/sc-sics-sp-001-power",
            "@type": "iot-lite:Service",
            "iot-lite:endpoint": "http://smart-ics.surrey.ac.uk/fiesta-iot/service/sc-sics-sp-001-power",
            "iot-lite:interfaceType": "http://smart-ics.surrey.ac.uk/fiesta-iot/service/RESTful"
        },
        {
            "@id": "sics:system#smart-campus",
            "@type": "ssn:System",
            "hasSubSystem": "sics:system#unis-smart-building"
        },
        {
            "@id": "sics:system#unis-smart-building",
            "@type": "ssn:System",
            "isSubSystemOf": "sics:system#smart-campus",
            "hasSubSystem": "sics:device#sc-sics-sp-001"
        },
        {
            "@id": "sics:unit#Watt",
            "@type": "mthreelite:Watt"
        }
    ],
    "@context": {
        "onPlatform": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#onPlatform",
            "@type": "@id"
        },
        "hasSubSystem": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasSubSystem",
            "@type": "@id"
        },
        "hasDomainOfInterest": {
            "@id": "http://purl.org/iot/vocab/m3-lite#hasDomainOfInterest",
            "@type": "@id"
        },
        "hasDeployment": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasDeployment",
            "@type": "@id"
        },
        "isSubSystemOf": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#isSubSystemOf",
            "@type": "@id"
        },
        "location": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#location",
            "@type": "@id"
        },
        "isMobile": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#isMobile",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "long": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#long",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "lat": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#lat",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "relativeLocation": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#relativeLocation",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "exposedBy": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#exposedBy",
            "@type": "@id"
        },
        "hasUnit": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasUnit",
            "@type": "@id"
        },
        "hasQuantityKind": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasQuantityKind",
            "@type": "@id"
        },
        "interfaceType": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#interfaceType",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "endpoint": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#endpoint",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "onemtom": "http://www.onem2m.org/ontology/Base_Ontology/base_ontology#",
        "qudt": "http://data.qudt.org/qudt/owl/1.0.0/unit.owl#",
        "iot-lite": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#",
        "qu": "http://purl.org/NET/ssnx/qu/qu#",
        "owl": "http://www.w3.org/2002/07/owl#",
        "ns": "http://creativecommons.org/ns#",
        "xsd": "http://www.w3.org/2001/XMLSchema#",
        "fiesta-iot": "http://purl.org/iot/ontology/fiesta-iot#",
        "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
        "ssn": "http://purl.oclc.org/NET/ssnx/ssn#",
        "geo": "http://www.w3.org/2003/01/geo/wgs84_pos#",
        "sics": "http://smart-ics.surrey.ac.uk/fiesta-iot/",
        "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "terms": "http://purl.org/dc/terms/",
        "dul": "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#",
        "time": "http://www.w3.org/2006/time#",
        "mthreelite": "http://purl.org/iot/vocab/m3-lite#",
        "vann": "http://purl.org/vocab/vann/",
        "dc": "http://purl.org/dc/elements/1.1/"
    }
}

``` 

## getResource (dereferenceable)  
  
This method allows the retrieval of a resource description.  
  
```  
GET http://{hostname}/fiesta-iot/resource/{resourceId}
```  

URL (example): 

``` 
 http://{hostname}/fiesta-iot/resource/sc-sics-sp-001-power
```  
Response (example):

```json 
{
    "@graph": [
        {
            "@id": "sics:deployment#smart-ics",
            "@type": "ssn:Deployment"
        },
        {
            "@id": "sics:device#sc-sics-sp-001",
            "@type": "ssn:Device",
            "isSubSystemOf": "sics:system#unis-smart-building",
            "hasDeployment": "sics:deployment#smart-ics",
            "hasSubSystem": "sics:resource/sc-sics-sp-001-power",
            "onPlatform": "sics:platform#desk-ICS-02-11",
            "hasDomainOfInterest": "sics:doi#BuildingAutomation"
        },
        {
            "@id": "sics:doi#BuildingAutomation",
            "@type": "mthreelite:BuildingAutomation"
        },
        {
            "@id": "sics:loc#UNIVERSITY_OF_SURREY",
            "@type": "geo:Point",
            "iot-lite:relativeLocation": "http://sws.geonames.org/6695971/",
            "geo:lat": "51.2433445",
            "geo:long": "-0.5932438"
        },
        {
            "@id": "sics:platform#desk-ICS-02-11",
            "@type": "ssn:Platform",
            "iot-lite:isMobile": "False",
            "location": "sics:loc#UNIVERSITY_OF_SURREY"
        },
        {
            "@id": "sics:qk#Power",
            "@type": "mthreelite:Power"
        },
        {
            "@id": "sics:resource/sc-sics-sp-001-power",
            "@type": "mthreelite:EnergyMeter",
            "exposedBy": "sics:service/sc-sics-sp-001-power",
            "hasQuantityKind": "sics:qk#Power",
            "hasUnit": "sics:unit#Watt",
            "isSubSystemOf": "sics:device#sc-sics-sp-001"
        },
        {
            "@id": "sics:service/sc-sics-sp-001-power",
            "@type": "iot-lite:Service",
            "iot-lite:endpoint": "http://smart-ics.surrey.ac.uk/fiesta-iot/service/sc-sics-sp-001-power",
            "iot-lite:interfaceType": "http://smart-ics.surrey.ac.uk/fiesta-iot/service#RESTful"
        },
        {
            "@id": "sics:system#smart-campus",
            "@type": "ssn:System",
            "hasSubSystem": "sics:system#unis-smart-building"
        },
        {
            "@id": "sics:system#unis-smart-building",
            "@type": "ssn:System",
            "isSubSystemOf": "sics:system#smart-campus",
            "hasSubSystem": "sics:device#sc-sics-sp-001"
        },
        {
            "@id": "sics:unit#Watt",
            "@type": "mthreelite:Watt"
        }
    ],
    "@context": {
        "onPlatform": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#onPlatform",
            "@type": "@id"
        },
        "hasSubSystem": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasSubSystem",
            "@type": "@id"
        },
        "hasDomainOfInterest": {
            "@id": "http://purl.org/iot/vocab/m3-lite#hasDomainOfInterest",
            "@type": "@id"
        },
        "hasDeployment": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasDeployment",
            "@type": "@id"
        },
        "isSubSystemOf": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#isSubSystemOf",
            "@type": "@id"
        },
        "location": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#location",
            "@type": "@id"
        },
        "isMobile": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#isMobile",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "long": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#long",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "lat": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#lat",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "relativeLocation": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#relativeLocation",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "exposedBy": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#exposedBy",
            "@type": "@id"
        },
        "hasUnit": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasUnit",
            "@type": "@id"
        },
        "hasQuantityKind": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasQuantityKind",
            "@type": "@id"
        },
        "interfaceType": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#interfaceType",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "endpoint": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#endpoint",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "onemtom": "http://www.onem2m.org/ontology/Base_Ontology/base_ontology#",
        "qudt": "http://data.qudt.org/qudt/owl/1.0.0/unit.owl#",
        "iot-lite": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#",
        "qu": "http://purl.org/NET/ssnx/qu/qu#",
        "owl": "http://www.w3.org/2002/07/owl#",
        "ns": "http://creativecommons.org/ns#",
        "xsd": "http://www.w3.org/2001/XMLSchema#",
        "fiesta-iot": "http://purl.org/iot/ontology/fiesta-iot#",
        "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
        "ssn": "http://purl.oclc.org/NET/ssnx/ssn#",
        "geo": "http://www.w3.org/2003/01/geo/wgs84_pos#",
        "sics": "http://smart-ics.surrey.ac.uk/fiesta-iot/",
        "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "terms": "http://purl.org/dc/terms/",
        "dul": "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#",
        "time": "http://www.w3.org/2006/time#",
        "mthreelite": "http://purl.org/iot/vocab/m3-lite#",
        "vann": "http://purl.org/vocab/vann/",
        "dc": "http://purl.org/dc/elements/1.1/"
    }
}
``` 

## getLastObservations 

```  
POST http://{hostname}/fiesta-iot/tps/getLastObservations 
```  

Body of the request (example): 

```json 
{
  "sensorIDs": ["http://smart-ics.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-001-power", "http://smart-ics.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-002-power", "http://smart-ics.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-003-power" ]
}
```
The **sensorIDs** are the IDs of the resource in the resource descriptions, e.g. "http://smart-ics.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-001-power"

Response (example):

```json
{
    "@graph": [
        {
            "@id": "sics:loc#ICS",
            "@type": "geo:Point",
            "iot-lite:altRelative": [
                "1",
                "2"
            ],
            "iot-lite:relativeLocation": "http://sws.geonames.org/6695971/",
            "geo:alt": "57.863815",
            "geo:lat": "51.2433445",
            "geo:long": "-0.5932438"
        },
        {
            "@id": "sics:observationName#sc-sics-sp-001-power",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#sc-sics-sp-001-power",
            "observationSamplingTime": "sics:timeInterval#2017-04-05T18:14:00.001",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#ICS"
        },
        {
            "@id": "sics:observationName#sc-sics-sp-002-power",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#sc-sics-sp-002-power",
            "observationSamplingTime": "sics:timeInterval#2017-04-05T18:14:00.001",
            "observedBy": "sics:resource/sc-sics-sp-002-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#ICS"
        },
        {
            "@id": "sics:observationName#sc-sics-sp-003-power",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#sc-sics-sp-003-power",
            "observationSamplingTime": "sics:timeInterval#2017-04-05T18:14:00.001",
            "observedBy": "sics:resource/sc-sics-sp-003-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#ICS"
        },
        {
            "@id": "sics:observationProperty#Power",
            "@type": "mthreelite:Power"
        },
        {
            "@id": "sics:observationValue#sc-sics-sp-001-power",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": "20.798966"
        },
        {
            "@id": "sics:observationValue#sc-sics-sp-002-power",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": "0.803082"
        },
        {
            "@id": "sics:observationValue#sc-sics-sp-003-power",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": "0.891317"
        },
        {
            "@id": "sics:resource/sc-sics-sp-001-power",
            "@type": "mthreelite:EnergyMeter"
        },
        {
            "@id": "sics:resource/sc-sics-sp-002-power",
            "@type": "mthreelite:EnergyMeter"
        },
        {
            "@id": "sics:resource/sc-sics-sp-003-power",
            "@type": "mthreelite:EnergyMeter"
        },
        {
            "@id": "sics:sensorOutput#sc-sics-sp-001-power",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#sc-sics-sp-001-power"
        },
        {
            "@id": "sics:sensorOutput#sc-sics-sp-002-power",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#sc-sics-sp-002-power"
        },
        {
            "@id": "sics:sensorOutput#sc-sics-sp-003-power",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#sc-sics-sp-003-power"
        },
        {
            "@id": "sics:timeInterval#2017-04-05T18:14:00.001",
            "@type": "time:Instant",
            "time:inXSDDateTime": "2017-04-05T18:14:00.001"
        },
        {
            "@id": "sics:unit#Watt",
            "@type": "mthreelite:Watt"
        }
    ],
    "@context": {
        "inXSDDateTime": {
            "@id": "http://www.w3.org/2006/time#inXSDDateTime",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "location": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#location",
            "@type": "@id"
        },
        "observationSamplingTime": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observationSamplingTime",
            "@type": "@id"
        },
        "observedProperty": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observedProperty",
            "@type": "@id"
        },
        "observationResult": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observationResult",
            "@type": "@id"
        },
        "observedBy": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observedBy",
            "@type": "@id"
        },
        "hasValue": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasValue",
            "@type": "@id"
        },
        "hasUnit": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasUnit",
            "@type": "@id"
        },
        "hasDataValue": {
            "@id": "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#hasDataValue",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "altRelative": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#altRelative",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "relativeLocation": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#relativeLocation",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "alt": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#alt",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "long": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#long",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "lat": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#lat",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "onemtom": "http://www.onem2m.org/ontology/Base_Ontology/base_ontology#",
        "qudt": "http://data.qudt.org/qudt/owl/1.0.0/unit.owl#",
        "iot-lite": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#",
        "qu": "http://purl.org/NET/ssnx/qu/qu#",
        "owl": "http://www.w3.org/2002/07/owl#",
        "ns": "http://creativecommons.org/ns#",
        "xsd": "http://www.w3.org/2001/XMLSchema#",
        "fiesta-iot": "http://purl.org/iot/ontology/fiesta-iot#",
        "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
        "ssn": "http://purl.oclc.org/NET/ssnx/ssn#",
        "geo": "http://www.w3.org/2003/01/geo/wgs84_pos#",
        "sics": "http://smart-ics.surrey.ac.uk/fiesta-iot/",
        "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "terms": "http://purl.org/dc/terms/",
        "dul": "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#",
        "time": "http://www.w3.org/2006/time#",
        "mthreelite": "http://purl.org/iot/vocab/m3-lite#",
        "vann": "http://purl.org/vocab/vann/",
        "dc": "http://purl.org/dc/elements/1.1/"
    }
}  

```  

## getLastObservation  

```  
GET http://{hostname}/fiesta-iot/service/{resourceId}
```  

URL (example): 

``` 
 http://{hostname}/fiesta-iot/service/sc-sics-sp-001-power
```  

Response (example):

```json
{
    "@graph": [
        {
            "@id": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118",
            "@type": "geo:Point",
            "iot-lite:altRelative": "2",
            "iot-lite:relativeLocation": "http://sws.geonames.org/6695971/",
            "geo:alt": 57.863815,
            "geo:lat": 51.2433445,
            "geo:long": -0.5932438
        },
        {
            "@id": "sics:observation#nyq7WZVA6r",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#nyq7WZVA6r",
            "observationSamplingTime": "sics:timeInterval#UTC_Ld5QNRQZM2",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observationProperty#Power",
            "@type": "mthreelite:Power"
        },
        {
            "@id": "sics:observationValue#nyq7WZVA6r",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 11.356845
        },
        {
            "@id": "sics:resource/sc-sics-sp-001-power",
            "@type": "mthreelite:EnergyMeter"
        },
        {
            "@id": "sics:sensorOutput#nyq7WZVA6r",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#nyq7WZVA6r"
        },
        {
            "@id": "sics:timeInterval#UTC_Ld5QNRQZM2",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-05-03T17:21:00Z"
        },
        {
            "@id": "sics:unit#Watt",
            "@type": "mthreelite:Watt"
        }
    ],
    "@context": {
        "hasValue": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasValue",
            "@type": "@id"
        },
        "location": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#location",
            "@type": "@id"
        },
        "observationSamplingTime": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observationSamplingTime",
            "@type": "@id"
        },
        "observedProperty": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observedProperty",
            "@type": "@id"
        },
        "observationResult": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observationResult",
            "@type": "@id"
        },
        "observedBy": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observedBy",
            "@type": "@id"
        },
        "inXSDDateTime": {
            "@id": "http://www.w3.org/2006/time#inXSDDateTime",
            "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
        },
        "hasUnit": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasUnit",
            "@type": "@id"
        },
        "hasDataValue": {
            "@id": "http://www.loa.istc.cnr.it/ontologies/DUL.owl#hasDataValue",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "relativeLocation": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#relativeLocation",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "altRelative": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#altRelative",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "alt": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#alt",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "long": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#long",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "lat": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#lat",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "onemtom": "http://www.onem2m.org/ontology/Base_Ontology/base_ontology#",
        "qudt": "http://data.qudt.org/qudt/owl/1.0.0/unit.owl#",
        "iot-lite": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#",
        "qu": "http://purl.org/NET/ssnx/qu/qu#",
        "owl": "http://www.w3.org/2002/07/owl#",
        "ns": "http://creativecommons.org/ns#",
        "xsd": "http://www.w3.org/2001/XMLSchema#",
        "fiesta-iot": "http://purl.org/iot/ontology/fiesta-iot#",
        "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
        "ssn": "http://purl.oclc.org/NET/ssnx/ssn#",
        "geo": "http://www.w3.org/2003/01/geo/wgs84_pos#",
        "sics": "http://smart-ics.surrey.ac.uk/fiesta-iot/",
        "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "terms": "http://purl.org/dc/terms/",
        "dcterms": "http://purl.org/dc/terms/",
        "dul": "http://www.loa.istc.cnr.it/ontologies/DUL.owl#",
        "time": "http://www.w3.org/2006/time#",
        "mthreelite": "http://purl.org/iot/vocab/m3-lite#",
        "vann": "http://purl.org/vocab/vann/",
        "dc": "http://purl.org/dc/elements/1.1/"
    }
}

```

## getObservations

```  
POST http://{hostname}/fiesta-iot/tps/getObservations 
```  

Body of the request (example): 

```json 
{
     "sensorIDs": ["http://smart-ics.surrey.ac.uk/fiesta-iot/resource/sc-sics-sp-001-power"],
     "startDate": "2017-04-28T14:10:00Z",
     "stopDate": "2017-04-28T14:20:00Z"
}
```  
The **sensorIDs** are the **{resourceId}** fields in the resource descriptions

Response (example):

```json
{
    "@graph": [
        {
            "@id": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118",
            "@type": "geo:Point",
            "iot-lite:altRelative": "2",
            "iot-lite:relativeLocation": "http://sws.geonames.org/6695971/",
            "geo:alt": 57.863815,
            "geo:lat": 51.2433445,
            "geo:long": -0.5932438
        },
        {
            "@id": "sics:observation#2DNy4RKv19",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#2DNy4RKv19",
            "observationSamplingTime": "sics:timeInterval#UTC_Zo09MZBLAJ",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#2qveKRVwQY",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#2qveKRVwQY",
            "observationSamplingTime": "sics:timeInterval#UTC_eJ7yOL2aYq",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#5mXKGwKdYQ",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#5mXKGwKdYQ",
            "observationSamplingTime": "sics:timeInterval#UTC_qRp2rR2rWn",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#AwQ4ey4Mxv",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#AwQ4ey4Mxv",
            "observationSamplingTime": "sics:timeInterval#UTC_BdoGeVvR35",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#Ene8GMx6gD",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#Ene8GMx6gD",
            "observationSamplingTime": "sics:timeInterval#UTC_YQrBmj6kEO",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#MyOK8OqbGj",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#MyOK8OqbGj",
            "observationSamplingTime": "sics:timeInterval#UTC_MYG6GNEJLg",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#WEbqG4xm3K",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#WEbqG4xm3K",
            "observationSamplingTime": "sics:timeInterval#UTC_qe0d2zdmja",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#YRzZ028wEj",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#YRzZ028wEj",
            "observationSamplingTime": "sics:timeInterval#UTC_qEZGKAB58r",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observation#p2b4ZQVwaG",
            "@type": "ssn:Observation",
            "observationResult": "sics:sensorOutput#p2b4ZQVwaG",
            "observationSamplingTime": "sics:timeInterval#UTC_8XybVew9Gl",
            "observedBy": "sics:resource/sc-sics-sp-001-power",
            "observedProperty": "sics:observationProperty#Power",
            "location": "sics:loc#UNIVERSITY_OF_SURREY-unis-ics-desk-118"
        },
        {
            "@id": "sics:observationProperty#Power",
            "@type": "mthreelite:Power"
        },
        {
            "@id": "sics:observationValue#2DNy4RKv19",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 13.943551
        },
        {
            "@id": "sics:observationValue#2qveKRVwQY",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 14.015366
        },
        {
            "@id": "sics:observationValue#5mXKGwKdYQ",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 13.51761
        },
        {
            "@id": "sics:observationValue#AwQ4ey4Mxv",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 13.541416
        },
        {
            "@id": "sics:observationValue#Ene8GMx6gD",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 13.473031
        },
        {
            "@id": "sics:observationValue#MyOK8OqbGj",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 13.704301
        },
        {
            "@id": "sics:observationValue#WEbqG4xm3K",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 20.824553
        },
        {
            "@id": "sics:observationValue#YRzZ028wEj",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 13.625563
        },
        {
            "@id": "sics:observationValue#p2b4ZQVwaG",
            "@type": "ssn:ObservationValue",
            "hasUnit": "sics:unit#Watt",
            "dul:hasDataValue": 19.053986
        },
        {
            "@id": "sics:resource/sc-sics-sp-001-power",
            "@type": "mthreelite:EnergyMeter"
        },
        {
            "@id": "sics:sensorOutput#2DNy4RKv19",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#2DNy4RKv19"
        },
        {
            "@id": "sics:sensorOutput#2qveKRVwQY",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#2qveKRVwQY"
        },
        {
            "@id": "sics:sensorOutput#5mXKGwKdYQ",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#5mXKGwKdYQ"
        },
        {
            "@id": "sics:sensorOutput#AwQ4ey4Mxv",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#AwQ4ey4Mxv"
        },
        {
            "@id": "sics:sensorOutput#Ene8GMx6gD",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#Ene8GMx6gD"
        },
        {
            "@id": "sics:sensorOutput#MyOK8OqbGj",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#MyOK8OqbGj"
        },
        {
            "@id": "sics:sensorOutput#WEbqG4xm3K",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#WEbqG4xm3K"
        },
        {
            "@id": "sics:sensorOutput#YRzZ028wEj",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#YRzZ028wEj"
        },
        {
            "@id": "sics:sensorOutput#p2b4ZQVwaG",
            "@type": "ssn:SensorOutput",
            "hasValue": "sics:observationValue#p2b4ZQVwaG"
        },
        {
            "@id": "sics:timeInterval#UTC_8XybVew9Gl",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:14:"
        },
        {
            "@id": "sics:timeInterval#UTC_BdoGeVvR35",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:13:"
        },
        {
            "@id": "sics:timeInterval#UTC_MYG6GNEJLg",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:11:00.0019"
        },
        {
            "@id": "sics:timeInterval#UTC_YQrBmj6kEO",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:15:"
        },
        {
            "@id": "sics:timeInterval#UTC_Zo09MZBLAJ",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:18:"
        },
        {
            "@id": "sics:timeInterval#UTC_eJ7yOL2aYq",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:12:"
        },
        {
            "@id": "sics:timeInterval#UTC_qEZGKAB58r",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:17:"
        },
        {
            "@id": "sics:timeInterval#UTC_qRp2rR2rWn",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:16:"
        },
        {
            "@id": "sics:timeInterval#UTC_qe0d2zdmja",
            "@type": "time:Instant",
            "inXSDDateTime": "2017-04-28T14:19:"
        },
        {
            "@id": "sics:unit#Watt",
            "@type": "mthreelite:Watt"
        }
    ],
    "@context": {
        "hasValue": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#hasValue",
            "@type": "@id"
        },
        "inXSDDateTime": {
            "@id": "http://www.w3.org/2006/time#inXSDDateTime",
            "@type": "http://www.w3.org/2001/XMLSchema#dateTime"
        },
        "hasUnit": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#hasUnit",
            "@type": "@id"
        },
        "hasDataValue": {
            "@id": "http://www.loa.istc.cnr.it/ontologies/DUL.owl#hasDataValue",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "location": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#location",
            "@type": "@id"
        },
        "observationSamplingTime": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observationSamplingTime",
            "@type": "@id"
        },
        "observedProperty": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observedProperty",
            "@type": "@id"
        },
        "observationResult": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observationResult",
            "@type": "@id"
        },
        "observedBy": {
            "@id": "http://purl.oclc.org/NET/ssnx/ssn#observedBy",
            "@type": "@id"
        },
        "relativeLocation": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#relativeLocation",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "altRelative": {
            "@id": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#altRelative",
            "@type": "http://www.w3.org/2001/XMLSchema#string"
        },
        "alt": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#alt",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "long": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#long",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "lat": {
            "@id": "http://www.w3.org/2003/01/geo/wgs84_pos#lat",
            "@type": "http://www.w3.org/2001/XMLSchema#double"
        },
        "onemtom": "http://www.onem2m.org/ontology/Base_Ontology/base_ontology#",
        "qudt": "http://data.qudt.org/qudt/owl/1.0.0/unit.owl#",
        "iot-lite": "http://purl.oclc.org/NET/UNIS/fiware/iot-lite#",
        "qu": "http://purl.org/NET/ssnx/qu/qu#",
        "owl": "http://www.w3.org/2002/07/owl#",
        "ns": "http://creativecommons.org/ns#",
        "xsd": "http://www.w3.org/2001/XMLSchema#",
        "fiesta-iot": "http://purl.org/iot/ontology/fiesta-iot#",
        "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
        "ssn": "http://purl.oclc.org/NET/ssnx/ssn#",
        "geo": "http://www.w3.org/2003/01/geo/wgs84_pos#",
        "sics": "http://smart-ics.surrey.ac.uk/fiesta-iot/",
        "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
        "terms": "http://purl.org/dc/terms/",
        "dcterms": "http://purl.org/dc/terms/",
        "dul": "http://www.loa.istc.cnr.it/ontologies/DUL.owl#",
        "time": "http://www.w3.org/2006/time#",
        "mthreelite": "http://purl.org/iot/vocab/m3-lite#",
        "vann": "http://purl.org/vocab/vann/",
        "dc": "http://purl.org/dc/elements/1.1/"
    }
}

```

## getObservation

(TODO)  

``` 
GET http://{hostname}/getObservation/{resourceId}?startdate={startdate}&stopdate={stopdate}
```  
The **sensorIDs** are the **{resourceId}** fields in the resource descriptions

Response (example):

```json
{
    "Observations": [
        {
            "alt": "53",
            "dataValue": "11.250145",
            "deployment": "unis-smart-campus",
            "deviceId": "sc-sics-sp-001",
            "iot-type": "sensor",
            "lat": "51.1",
            "lon": "-0.1",
            "measurement": "Watts",
            "mobile": "False",
            "platform": "desk-ICS-02-18",
            "qk": "power",
            "relativeAlt": "2",
            "resourceId": "sc-sics-sp-001-power",
            "system": "smart-ics",
            "timestamp": "2017-02-21T11:01:00.001",
            "unit": "Watts"
        },
        {
            "alt": "53",
            "dataValue": "37.483008",
            "deployment": "unis-smart-campus",
            "deviceId": "sc-sics-sp-001",
            "iot-type": "sensor",
            "lat": "51.1",
            "lon": "-0.1",
            "measurement": "Watts",
            "mobile": "False",
            "platform": "desk-ICS-02-18",
            "qk": "power",
            "relativeAlt": "2",
            "resourceId": "sc-sics-sp-001-power",
            "system": "smart-ics",
            "timestamp": "2017-02-21T11:01:00.001",
            "unit": "Watts"
        }
    ]
}

```
