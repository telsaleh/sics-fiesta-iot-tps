package models;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import java.time.Instant;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import uk.ac.surrey.ee.iot.smartics.model.reduce.IotNode;
//import uk.ac.surrey.ee.iot.smartics.model.reduce.Reading;
//
///**
// *
// * @author te0003
// */
//public class CreateReduce {
//    
//    public static void main(String[] args){
//
//        String timeStamp = "";
//        
//        Instant instant = Instant.now();
//        instant.minusSeconds(120);
//        timeStamp = instant.toString();
//        
//        Reading reading = new Reading(Integer.decode("501"), Integer.decode("20"), 
//                Integer.decode("001"), Integer.decode("1"), Integer.decode("23"), timeStamp, "2.43");
//        IotNode iotNode = new IotNode(reading);
//        
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        
//        String discRespMsg = "";//= "";
//        
//        try {
//            discRespMsg = objectMapper.writeValueAsString(iotNode);
//        } catch (JsonProcessingException ex) {
//            Logger.getLogger(CreateReduce.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        System.out.println(discRespMsg);
//
//}
//    
//}
