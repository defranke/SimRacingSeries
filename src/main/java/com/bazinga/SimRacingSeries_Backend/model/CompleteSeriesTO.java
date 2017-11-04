package com.bazinga.SimRacingSeries_Backend.model;

import java.util.List;

public class CompleteSeriesTO {

    private SeriesDO series;

    private List<TeamDO> teams;

    private List<DriverDO> drivers;

    public SeriesDO getSeries() {
        return series;
    }

    public void setSeries(SeriesDO series) {
        this.series = series;
    }

    public List<TeamDO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDO> teams) {
        this.teams = teams;
    }

    public List<DriverDO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDO> drivers) {
        this.drivers = drivers;
    }
}
