package com.flight.app.kafka.producer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.kafka.clients.producer.*;
import org.apache.log4j.Logger;

import com.flight.app.kafka.util.PropertyFileReader;
import com.flight.app.kafka.model.FlightData;

import org.apache.kafka.clients.producer.KafkaProducer;

public class FlightDataProducer {
    private static final Logger logger = Logger.getLogger(FlightDataProducer.class);

    public static void main(String[] args) throws Exception {
        //read config file
        Properties prop = PropertyFileReader.readPropertyFile();
        String zookeeper = prop.getProperty("com.flight.app.kafka.zookeeper");
        String brokerList = prop.getProperty("com.flight.app.kafka.brokerlist");
        String topic = prop.getProperty("com.flight.app.kafka.topic");
        logger.info("Using Zookeeper=" + zookeeper + " ,Broker-list=" + brokerList + " and topic " + topic);

        // set producer properties
        Properties properties = new Properties();
        properties.put("zookeeper.connect", zookeeper);
        properties.put("bootstrap.servers", brokerList);
        properties.put("request.required.acks", "1");
        properties.put("key.serializer", "com.flight.app.kafka.util.FlightDataSerializer");
        properties.put("value.serializer", "com.flight.app.kafka.util.FlightDataSerializer");
        //generate event
        Producer<String, FlightData> producer = new KafkaProducer<>(properties);
        FlightDataProducer flightProducer = new FlightDataProducer();
        flightProducer.generateFlightEvent(producer,topic);
    }

    private void generateFlightEvent(Producer<String, FlightData> producer, String topic) throws InterruptedException {
        List<String> routeList = Arrays.asList(new String[]{"Route-1", "Route-2", "Route-3"});
        List<String> flightTypeList = Arrays.asList(new String[]{"AirbusA380", "Boeing747", "Boeing737", "AirbusA319", "AirbusA300"});
        Random rand = new Random();
        logger.info("Sending events");
        // generate event in loop
        while (true) {
            List<FlightData> eventList = new ArrayList<>();
            for (int i = 0; i < 30; i++) {// create 30 vehicles
                String flightId = UUID.randomUUID().toString();
                String flightType = flightTypeList.get(rand.nextInt(5));
                String routeId = routeList.get(rand.nextInt(3));
                Date timestamp = new Date();
                double speed = rand.nextInt(100 - 20) + 20;// random speed between 20 to 100
                double fuelLevel = rand.nextInt(40 - 10) + 10;
                for (int j = 0; j < 5; j++) {// Add 5 events for each vehicle
                    String coords = getCoordinates(routeId);
                    String latitude = coords.substring(0, coords.indexOf(","));
                    String longitude = coords.substring(coords.indexOf(",") + 1, coords.length());
                    FlightData event = new FlightData(flightId, flightType, routeId, latitude, longitude, timestamp, speed,fuelLevel);
                    eventList.add(event);
                }
            }
            Collections.shuffle(eventList);// shuffle for random events
            for (FlightData event : eventList) {
                ProducerRecord<String, FlightData> data = new ProducerRecord<>(topic, event);
                producer.send(data);
                Thread.sleep(rand.nextInt(3000 - 1000) + 1000);//random delay of 1 to 3 seconds
            }
        }
    }

    //Method to generate random latitude and longitude for routes
    private String  getCoordinates(String routeId) {
        Random rand = new Random();
        int latPrefix = 0;
        int longPrefix = -0;
        if (routeId.equals("Route-1")) {
            latPrefix = 33;
            longPrefix = -96;
        } else if (routeId.equals("Route-2")) {
            latPrefix = 34;
            longPrefix = -97;
        } else if (routeId.equals("Route-3")) {
            latPrefix = 35;
            longPrefix = -98;
        }
        float lati = latPrefix + rand.nextFloat();
        float longi = longPrefix + rand.nextFloat();
        return lati + "," + longi;
    }
}
