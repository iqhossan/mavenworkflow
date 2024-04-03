package data;

import java.time.LocalDate;

public record Employee(
        Long employeeId,
        String firstName,
        String lastName,
        LocalDate employmentDate,
        Double yearlySalary,
        PensionPlan pensionPlan )
{

    @Override
    public Long employeeId() {
        return employeeId;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public LocalDate employmentDate() {
        return employmentDate;
    }

    @Override
    public Double yearlySalary() {
        return yearlySalary;
    }

    @Override
    public PensionPlan pensionPlan() {
        return pensionPlan;
    }

}
