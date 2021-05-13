/*
* The serializer coverts flightData to json string
* */
package com.flight.app.kafka.util;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.app.kafka.model.FlightData;
import kafka.utils.VerifiableProperties;

import java.util.Map;


public class FlightDataSerializer implements Serializer {
    private static final Logger logger = Logger.getLogger(FlightDataSerializer.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    public FlightDataSerializer() {

    }

//    public byte[] toBytes(FlightData flightEvent) {
//        try {
//            String msg = objectMapper.writeValueAsString(flightEvent);
//            logger.info(msg);
//            return msg.getBytes();
//        } catch (JsonProcessingException e) {
//            logger.error("Error in Serialization", e);
//        }
//        return null;
//    }

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(o);
            logger.info(retVal);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return retVal.getBytes();
    }

    @Override
    public void close() {

    }
}
