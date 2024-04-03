package data;

import java.time.LocalDate;

public class PensionPlan {
    String planReferenceNumber;
    LocalDate enrollmentDate;
    Double monthlyContribution;

    public PensionPlan(){

    }

    public PensionPlan(String planReferenceNumber, LocalDate enrollmentDate, Double monthlyContribution) {
        this.planReferenceNumber = planReferenceNumber;
        this.enrollmentDate = enrollmentDate;
        this.monthlyContribution = monthlyContribution;
    }

    public String getPlanReferenceNumber() {
        return planReferenceNumber;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public Double getMonthlyContribution() {
        return monthlyContribution;
    }

}
