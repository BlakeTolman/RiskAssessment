package services;

import model.Client;
import model.Car;


 //Evaluates risk scores based on client and car attributes for insurance purposes.

public class RiskAssessmentEngine {


     //Calculates the overall risk score by evaluating both car and client attributes.
    public float calculateRiskScore(Client client, Car car) {
        float riskScore = 0;

        riskScore += evaluateCarAge(car.getAge()) * 0.10;
        riskScore += evaluateCarSafetyRating(car.getSafetyRating()) * 0.10;
        riskScore += evaluateCarMileage(car.getAnnualMileage()) * 0.10;
        riskScore += evaluateCarAntiTheft(car.hasAntiTheftDevice()) * 0.10;
        riskScore += evaluateCarReliability(car.getReliabilityRating()) * 0.10;

        riskScore += evaluateClientAge(client.getAge()) * 0.15;
        riskScore += evaluateYearsLicensed(client.getYearsLicensed()) * 0.10;
        riskScore += evaluateDrivingHistory(client.getAccidentsCount()) * 0.15;
        riskScore += evaluateCreditScore(client.getCreditScore()) * 0.10;

        return riskScore;
    }

    // Evaluates car age risk score (1 = new, 5 = old)
    private float evaluateCarAge(int age) {
        if (age < 3) return 1;
        if (age <= 5) return 2;
        if (age <= 10) return 3;
        if (age <= 15) return 4;
        return 5;
    }

    // Evaluates car safety rating risk score (1 = safest, 5 = least safe)
    private float evaluateCarSafetyRating(int rating) {
        return Math.min(Math.max(6 - rating, 1), 5);
    }

    // Evaluates car mileage risk score (1 = low mileage, 5 = high mileage)
    private float evaluateCarMileage(int mileage) {
        if (mileage < 10000) return 1;
        if (mileage <= 20000) return 2;
        if (mileage <= 50000) return 3;
        if (mileage <= 100000) return 4;
        return 5;
    }

    // Evaluates risk score for anti-theft presence (1 = has device, 5 = no device)
    private float evaluateCarAntiTheft(boolean hasAntiTheftDevice) {
        return hasAntiTheftDevice ? 1 : 5;
    }

    // Evaluates car reliability rating risk score (1 = most reliable, 5 = least reliable)
    private float evaluateCarReliability(int reliability) {
        return Math.min(Math.max(6 - reliability, 1), 5);
    }

    // Evaluates client age risk score (1 = ideal age, 5 = high-risk age)
    private float evaluateClientAge(int age) {
        if (age < 25) return 5;
        if (age <= 50) return 1;
        if (age <= 65) return 2;
        if (age <= 75) return 3;
        return 4;
    }

    // Evaluates years licensed risk score (1 = experienced driver, 5 = inexperienced driver)
    private float evaluateYearsLicensed(int yearsLicensed) {
        if (yearsLicensed < 1) return 5;
        if (yearsLicensed < 5) return 4;
        if (yearsLicensed <= 10) return 3;
        if (yearsLicensed <= 15) return 2;
        return 1;
    }

    // Evaluates driving history risk score (1 = no accidents, 5 = many accidents)
    private float evaluateDrivingHistory(int accidentsCount) {
        if (accidentsCount == 0) return 1;
        if (accidentsCount <= 2) return 2;
        if (accidentsCount <= 4) return 3;
        if (accidentsCount <= 6) return 4;
        return 5;
    }

    // Evaluates credit score risk score (1 = excellent credit, 5 = poor credit)
    private float evaluateCreditScore(int creditScore) {
        if (creditScore >= 750) return 1;
        if (creditScore >= 700) return 2;
        if (creditScore >= 650) return 3;
        if (creditScore >= 600) return 4;
        return 5;
    }
}
