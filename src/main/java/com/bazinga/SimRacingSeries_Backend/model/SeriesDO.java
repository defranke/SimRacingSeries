package com.bazinga.SimRacingSeries_Backend.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class SeriesDO {

    @Id
    private String id;

    private String slugName;

    private String name;

    private String description;

    private String reglement;

    private String password;

    private boolean isPublic;

    private Integer pointsForFastestLap;

    private Integer pointsForQualifying;

    private ArrayList<Integer> points;

    public SeriesDO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReglement() {
        return reglement;
    }

    public void setReglement(String reglement) {
        this.reglement = reglement;
    }

    public Integer getPointsForFastestLap() {
        return pointsForFastestLap;
    }

    public void setPointsForFastestLap(Integer pointsForFastestLap) {
        this.pointsForFastestLap = pointsForFastestLap;
    }

    public Integer getPointsForQualifying() {
        return pointsForQualifying;
    }

    public void setPointsForQualifying(Integer pointsForQualifying) {
        this.pointsForQualifying = pointsForQualifying;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }
}
