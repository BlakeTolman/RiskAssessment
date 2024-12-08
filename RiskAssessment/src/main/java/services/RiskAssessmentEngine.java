package services;

import model.Client;
import model.Car;


 //Evaluates risk scores based on client and car attributes for insurance purposes.

public class RiskAssessmentEngine {


     //Calculates the overall risk score by evaluating both car and client attributes.

    public float calculateRiskScore(Client client, Car car) {
        float riskScore = 0;

        riskScore += evaluateCarAge(car.getAge()) * 0.10;           // Add car age weight
        riskScore += evaluateCarSafetyRating(car.getSafetyRating()) * 0.10; // Add car safety rating weight
        riskScore += evaluateCarMileage(car.getAnnualMileage()) * 0.10;     // Add car mileage weight
        riskScore += evaluateCarAntiTheft(car.hasAntiTheftDevice()) * 0.10; // Add anti-theft weight
        riskScore += evaluateCarReliability(car.getReliabilityRating()) * 0.10; // Add reliability weight

        riskScore += evaluateClientAge(client.getAge()) * 0.15;          // Add client age weight
        riskScore += evaluateYearsLicensed(client.getYearsLicensed()) * 0.10; // Add years licensed weight
        riskScore += evaluateDrivingHistory(client.getAccidentsCount()) * 0.15; // Add driving history weight
        riskScore += evaluateCreditScore(client.getCreditScore()) * 0.10; // Add credit score weight

        return riskScore; // Return the final aggregated risk score
    }

    // Evaluates car age risk score
    private float evaluateCarAge(int age) {
        if (age < 3) return 1;
        if (age <= 5) return 2;
        return 3;
    }

    // Evaluates car safety rating risk score
    private float evaluateCarSafetyRating(int rating) {
        return 6 - rating;
    }

    // Evaluates car mileage risk score
    private float evaluateCarMileage(int mileage) {
        if (mileage < 10000) return 1;
        if (mileage <= 20000) return 2;
        return 3;
    }

    // Evaluates risk score for anti-theft presence
    private float evaluateCarAntiTheft(boolean hasAntiTheftDevice) {
        return hasAntiTheftDevice ? 1 : 3;
    }

    // Evaluates car reliability rating risk score
    private float evaluateCarReliability(int reliability) {
        return 6 - reliability;
    }

    // Evaluates client age risk score
    private float evaluateClientAge(int age) {
        if (age < 25) return 3;
        if (age <= 50) return 1;
        return 2;
    }

    // Evaluates years licensed risk score
    private float evaluateYearsLicensed(int yearsLicensed) {
        if (yearsLicensed < 5) return 3;
        if (yearsLicensed <= 10) return 2;
        return 1;
    }

    // Evaluates driving history risk score
    private float evaluateDrivingHistory(int accidentsCount) {
        if (accidentsCount == 0) return 1;
        if (accidentsCount <= 2) return 2;
        return 3;
    }

    // Evaluates credit score risk score
    private float evaluateCreditScore(int creditScore) {
        if (creditScore >= 750) return 1;
        if (creditScore >= 700) return 2;
        if (creditScore >= 650) return 3;
        return 4;
    }
}
