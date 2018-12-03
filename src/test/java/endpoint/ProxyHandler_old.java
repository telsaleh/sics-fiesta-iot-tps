package endpoint;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package uk.ac.surrey.ee.iot.smartics.endpoint.restlet.resource;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.*;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.restlet.data.MediaType;
//import org.restlet.representation.Representation;
//import org.restlet.representation.StringRepresentation;
//import org.restlet.resource.ClientResource;
//import org.restlet.resource.Get;
//import org.restlet.resource.Post;
//import org.restlet.resource.ServerResource;
//import uk.ac.surrey.ee.iot.smartics.annotator.IotNodeDataAnnotator;
//import uk.ac.surrey.ee.iot.smartics.model.fiesta.Context;
//import uk.ac.surrey.ee.iot.smartics.model.fiesta.Graph;
//import uk.ac.surrey.ee.iot.smartics.model.fiesta.IotNodeReading;
//import uk.ac.surrey.ee.iot.smartics.model.reduce.IotNode;
//
///**
// * Resource which has only one representation.
// */
//public class ProxyHandler_old extends ServerResource {
//
//    public String iotNodeURL = "http://131.227.88.96:5000/GetObservations";
//
//    @Get
////    public Representation handleLookup() {
////
////        String nodeId = (String) getRequest().getAttributes().get("node_id");
////        String quantityId = (String) getRequest().getAttributes().get("quantity_kind");
////        String resURI = getRequest().getResourceRef().toUri().toString();
////
////        int ind = resURI.lastIndexOf("/");
////        resURI = new StringBuilder(resURI).replace(ind, ind + 1, "#").toString();
////        System.out.println(resURI);
////        System.out.println("HELLO: " + resURI);
////
////        String result = getIotNodeObservation(nodeId, quantityId);
////        StringRepresentation resultInFormat = new StringRepresentation(result);
////        resultInFormat.setMediaType(MediaType.APPLICATION_JSON);
////        return resultInFormat;
////    }
//    
//    @Post
//    public Representation handlePost(Representation entity) {
//        
////        String nodeId = (String) getRequest().getAttributes().get("node_id");
////        String quantityId = (String) getRequest().getAttributes().get("quantity_kind");
////        String resURI = getRequest().getResourceRef().toUri().toString();
//
////        int ind = resURI.lastIndexOf("/");
////        resURI = new StringBuilder(resURI).replace(ind, ind + 1, "#").toString();
////        System.out.println(resURI);
////        System.out.println("HELLO: " + resURI);
////        String result = getIotNodeObservation(nodeId, quantityId);
//                String result = getIotNodeObservation(entity.getText());
//        StringRepresentation resultInFormat = new StringRepresentation(result);
//        resultInFormat.setMediaType(MediaType.APPLICATION_JSON);
//        return resultInFormat;
//    }
//
//    public String getIotNodeObservation(String tpsRequest) {
//
//        String datasetUrl = iotNodeURL + nodeId;
//        ClientResource smartIcsClientResource = new ClientResource(datasetUrl);
//        InputStream is = null;
//        try {
//            //glaClientResource.get().write(System.out);
//            is = smartIcsClientResource.get().getStream();
//        } catch (IOException ex) {
//            Logger.getLogger(ProxyHandler_old.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Reader jsonReader = new InputStreamReader(is);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        JsonElement jeDataset = new JsonParser().parse(jsonReader);
//        JsonArray jeArray = jeDataset.getAsJsonArray();
//        
//        if (jeArray.size()<1)
//            return gson.toJson(jeDataset);
//        
//        JsonObject joDataset = jeArray.get(0).getAsJsonObject();
//
//        IotNode iotNode = gson.fromJson(joDataset, IotNode.class);
//       
//        String timeStamp = iotNode.getReading().getTimeStamp();
//        
//        Instant instant = Instant.now();
//        instant.minusSeconds(120);
//        timeStamp = instant.toString();
//        
//        String value = "";
//        double valueInt=0.0;
//
//        switch (quantityId) {
//            case "temperature":
//                valueInt = iotNode.getReading().getTemp();
//                value = String.valueOf((((valueInt - 1.9237)/1651.6) - 0.5)/0.01) ;
//                break;
//            case "power":
//                value = String.valueOf(iotNode.getReading().getWatts());
//                break;
//            case "illuminance":
//                value = String.valueOf(iotNode.getReading().getLight());
//                break;
//            case "sound":
//                value = String.valueOf(iotNode.getReading().getMic());
//                break;
//            case "presence":
//                value = String.valueOf(iotNode.getReading().getPIR());
//                break;
//            default:
//                value = String.valueOf(0);
//                break;
//        }
//        
//        IotNodeDataAnnotator ri = new IotNodeDataAnnotator();
//        String iotNodeResp = ri.annotateObservation(nodeId, quantityId, value, timeStamp);  
//        
//        JsonElement jeResult = new JsonParser().parse(iotNodeResp);
//        iotNodeResp = gson.toJson(jeResult);
//        
//        //gson.toJson(iotNode);//        System.out.println("GLA CKAN in Hypercat:\n" + hcJson);
//
//        return iotNodeResp;
//
//    }
//
//}
