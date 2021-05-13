/*
* The Flight Data is flight model for simulation
* */
package com.flight.app.kafka.model;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlightData implements Serializable{
    private String flightId;
    private String flightType;
    private String routeId;
    private String longitude;
    private String latitude;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="MST")
    private Date timestamp;
    private double speed;
    private double fuelLevel;

    public FlightData(){

    }

    public FlightData(String flightId, String flightType, String routeId, String latitude, String longitude,
                   Date timestamp, double speed, double fuelLevel) {
        super();
        this.flightId = flightId;
        this.flightType = flightType;
        this.routeId = routeId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.speed = speed;
        this.fuelLevel = fuelLevel;
    }

    // GETTERS
    public String getFlightId() {
        return flightId;
    }

    public String getFlightType() {
        return flightType;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public double getSpeed() {
        return speed;
    }

}
