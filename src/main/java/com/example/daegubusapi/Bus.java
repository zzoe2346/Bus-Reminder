package com.example.daegubusapi;

public class Bus {
    private String busNumber;
    private int remainingBusStop;

    public Bus(String busNumber, int remainingBusStop) {
        this.busNumber = busNumber;
        this.remainingBusStop = remainingBusStop;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public int getRemainingBusStop() {
        return remainingBusStop;
    }
}
