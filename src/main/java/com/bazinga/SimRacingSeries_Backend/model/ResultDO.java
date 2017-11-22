package com.bazinga.SimRacingSeries_Backend.model;

public class ResultDO {

    private String driverId;
    private String replacementDriver;

    private boolean ignoreForStanding;
    private boolean participated;

    // Both
    private long fastestTime; // in ms

    // Quali
    private int penaltyPositions;

    // Race
    private int gridPosition;
    private int finishPosition;
    private int resultPosition;
    private long totalTime; // in ms
    private int lapsCompleted;
    private int penaltySeconds;
    private int penaltyPoints;
    private boolean dnf;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getReplacementDriver() {
        return replacementDriver;
    }

    public void setReplacementDriver(String replacementDriver) {
        this.replacementDriver = replacementDriver;
    }

    public long getFastestTime() {
        return fastestTime;
    }

    public void setFastestTime(long fastestTime) {
        this.fastestTime = fastestTime;
    }

    public int getPenaltyPositions() {
        return penaltyPositions;
    }

    public void setPenaltyPositions(int penaltyPositions) {
        this.penaltyPositions = penaltyPositions;
    }

    public int getGridPosition() {
        return gridPosition;
    }

    public void setGridPosition(int gridPosition) {
        this.gridPosition = gridPosition;
    }

    public int getResultPosition() {
        return resultPosition;
    }

    public void setResultPosition(int resultPosition) {
        this.resultPosition = resultPosition;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public int getLapsCompleted() {
        return lapsCompleted;
    }

    public void setLapsCompleted(int lapsCompleted) {
        this.lapsCompleted = lapsCompleted;
    }

    public int getPenaltySeconds() {
        return penaltySeconds;
    }

    public void setPenaltySeconds(int penaltySeconds) {
        this.penaltySeconds = penaltySeconds;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

    public boolean isDnf() {
        return dnf;
    }

    public void setDnf(boolean dnf) {
        this.dnf = dnf;
    }

    public int getFinishPosition() {
        return finishPosition;
    }

    public void setFinishPosition(int finishPosition) {
        this.finishPosition = finishPosition;
    }

    public boolean isIgnoreForStanding() {
        return ignoreForStanding;
    }

    public void setIgnoreForStanding(boolean ignoreForStanding) {
        this.ignoreForStanding = ignoreForStanding;
    }

    public boolean isParticipated() {
        return participated;
    }

    public void setParticipated(boolean participated) {
        this.participated = participated;
    }
}
