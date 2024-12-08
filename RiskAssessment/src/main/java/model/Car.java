package model;

public class Car {
    private int age;                   // Age of the car in years
    private int safetyRating;          // Safety rating of the car (1 to 5, where 5 is the safest)
    private int annualMileage;         // Annual mileage of the car
    private boolean antiTheftDevice;   // Whether the car has an anti-theft device
    private int reliabilityRating;     // Reliability rating of the car (1 to 5, where 5 is the most reliable)

    public Car(int age, int safetyRating, int annualMileage, boolean antiTheftDevice, int reliabilityRating) {
        this.age = age;
        this.safetyRating = safetyRating;
        this.annualMileage = annualMileage;
        this.antiTheftDevice = antiTheftDevice;
        this.reliabilityRating = reliabilityRating;
    }

    // Getter methods
    public int getAge() {
        return age;
    }

    public int getSafetyRating() {
        return safetyRating;
    }

    public int getAnnualMileage() {
        return annualMileage;
    }

    public boolean hasAntiTheftDevice() {
        return antiTheftDevice;
    }

    public int getReliabilityRating() {
        return reliabilityRating;
    }

}
