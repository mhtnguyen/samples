package com.example.minhhung.tribalscale.model;

/**
 * Created by minhhung on 2/5/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("postcode")
    @Expose
    private Integer postcode;

    /**
     * No args constructor for use in serialization
     *
     */
    public Location() {
    }

    public Location(String street, String city, String state, Integer postcode) {
        super();
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

}