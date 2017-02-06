package com.example.minhhung.tribalscale.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by minhhung on 2/5/2017.
 */
public class ResultsList {
    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;
    @SerializedName("info")
    @Expose
    private Info info;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultsList() {
    }

    public ResultsList(ArrayList<Result> results, Info info) {
        super();
        this.results = results;
        this.info = info;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
