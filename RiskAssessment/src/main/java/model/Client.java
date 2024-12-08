package model;

public class Client {
    private String name;
    private int age;
    private int creditScore;
    private String claimHistory;
    private int yearsLicensed;       // New property to store the number of years licensed
    private int accidentsCount;      // New property to store the number of accidents

    public Client(String name, int age, int creditScore, String claimHistory, int yearsLicensed, int accidentsCount) {
        this.name = name;
        this.age = age;
        this.creditScore = creditScore;
        this.claimHistory = claimHistory;
        this.yearsLicensed = yearsLicensed;
        this.accidentsCount = accidentsCount;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public String getClaimHistory() {
        return claimHistory;
    }

    public int getYearsLicensed() {
        return yearsLicensed;
    }

    public int getAccidentsCount() {
        return accidentsCount;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }
}
