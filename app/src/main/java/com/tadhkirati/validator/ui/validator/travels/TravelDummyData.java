package com.tadhkirati.validator.ui.validator.travels;

import com.tadhkirati.validator.models.Travel;

import java.util.ArrayList;
import java.util.List;

public class TravelDummyData {

    public static List<Travel> getTravels() {
        List<Travel> travels = new ArrayList<>();
        travels.add(Travel.createTravel().withDepartureStation("bel abbes").withArrivalStation("oran"));
        travels.add(Travel.createTravel().withDepartureStation("annaba").withArrivalStation("setif"));
        travels.add(Travel.createTravel().withDepartureStation("mostaghanem").withArrivalStation("bejaia"));
        travels.add(Travel.createTravel().withDepartureStation("tiaret").withArrivalStation("relizane"));
        travels.add(Travel.createTravel().withDepartureStation("new york").withArrivalStation("boston"));
        return travels;
    }
}
