package com.example.e_boimela;

public class RegisterObservers {

    private String observerEmailAddress;
    private String observerStallName;
    private String observerStallNumber;

    public RegisterObservers(){//
    }

    public RegisterObservers(String observerEmailAddress, String observerStallName, String observerStallNumber) {
        this.observerEmailAddress = observerEmailAddress;
        this.observerStallName = observerStallName;
        this.observerStallNumber = observerStallNumber;
    }

    public String getObserverEmailAddress() {
        return observerEmailAddress;
    }


    public String getObserverStallName() {
        return observerStallName;
    }


    public String getObserverStallNumber() {
        return observerStallNumber;
    }

}
