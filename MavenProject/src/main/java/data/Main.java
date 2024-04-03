package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(101L,"Daniel","Agar", LocalDate.of(2018,01,17), 105945.50,null));
        employees.add(new Employee(102L,"Benard","Shaw", LocalDate.of(2019,04,03), 197750.00,null));
        employees.add(new Employee(103L,"Carly","Agar", LocalDate.of(2014,05,16), 842000.75,null));
        employees.add(new Employee(104L,"Wesley","Schneider", LocalDate.of(2019,05,02), 74500.00,null));
        updatePensionList(employees);
    }


    private static void updatePensionList(List<Employee> employees){

       // List<Employee> allEmp = new ArrayList<>();
        // List<Employee> upcomingEmp = new ArrayList<>();
        AtomicInteger sl= new AtomicInteger(100);

        //Calculate all the employees
        List<Employee> allEmp = employees.stream()
                .map(employee -> {
                    LocalDate newDt = employee.employmentDate().plusYears(5);
                    if (newDt.isBefore(LocalDate.now()) || newDt.isEqual(LocalDate.now())) {
                        return new Employee(employee.employeeId(),
                                employee.firstName(),employee.lastName(),
                                employee.employmentDate(),employee.yearlySalary(),
                                new PensionPlan("EX" + sl.getAndIncrement(),newDt,
                                        employee.yearlySalary()*2/100));
                        //System.out.println("Employee " + employee.firstName() + " " + employee.lastName() + " has worked for at least 5 years.");
                    }else{
                        return new Employee(employee.employeeId(),
                                employee.firstName(),employee.lastName(),
                                employee.employmentDate(),employee.yearlySalary(),null);
                    }
                })
                .collect(Collectors.toList());


        //Calculate the upcoming employee
        List<Employee> upcomingEmp = employees.stream()
                .map(employee -> {
                    LocalDate newDt = employee.employmentDate().plusYears(5);
                    int month = LocalDate.now().getMonthValue()+1;
                    if ((newDt.isAfter(LocalDate.now()) || newDt.isEqual(LocalDate.now()) )
                            && month == newDt.getMonthValue() ) {
                        return new Employee(employee.employeeId(),
                                employee.firstName(),employee.lastName(),
                                employee.employmentDate(),employee.yearlySalary(),
                                new PensionPlan("EX" + sl.getAndIncrement(),newDt,
                                        employee.yearlySalary()*2/100));
                        // System.out.println("Employee " + employee.firstName() + " " + employee.lastName() + " has worked for at least 5 years.");
                    }else{
                        return null;
                    }

                }).collect(Collectors.toList());

//        for (Employee employee : employees) {
//            LocalDate newDt = employee.employmentDate().plusYears(5);
//                int month = LocalDate.now().getMonthValue()+1;
//                if ((newDt.isAfter(LocalDate.now()) || newDt.isEqual(LocalDate.now()) )
//                        && month == newDt.getMonthValue() ) {
//                    upcomingEmp.add(new Employee(employee.employeeId(),
//                            employee.firstName(),employee.lastName(),
//                            employee.employmentDate(),employee.yearlySalary(),
//                            new PensionPlan("EX" + sl.getAndIncrement(),newDt,employee.yearlySalary()*2/100)));
//                   // System.out.println("Employee " + employee.firstName() + " " + employee.lastName() + " has worked for at least 5 years.");
//                }
//        }

        // Sorting
        Collections.sort(allEmp, Comparator.comparing(Employee::lastName)
                .thenComparing(Employee::yearlySalary, Comparator.reverseOrder()));
        System.out.println("JSON-formatted list of all Employees:");
        productToJson(allEmp);

        System.out.println();
        Collections.sort(upcomingEmp, Comparator.nullsFirst(Comparator.comparing(Employee::employmentDate)));
        System.out.println("JSON-formatted list of upcoming Employees:");
        productToJson(upcomingEmp);

    }

    private static void productToJson(List<Employee> employees) {
        System.out.println("[");
        for (Employee employee : employees) {
            if(employee != null){
                if (employee.pensionPlan() != null && employee.pensionPlan().planReferenceNumber != null) {
                    System.out.println("""
                        {
                            "Plan Reference Number":%s,
                            "first name":"%s",
                            "last name":"%s",
                            "Yearly Salary in (USD)":"%s",
                            "Employment Date":%s,
                            "Enrollment Date":%s,
                            "Monthly Contribution":%s
                        },
                        """.formatted(
                                employee.pensionPlan().planReferenceNumber,
                            employee.firstName(),
                            employee.lastName(),
                            employee.yearlySalary(),
                            employee.employmentDate(),
                            employee.pensionPlan().enrollmentDate,
                            employee.pensionPlan().monthlyContribution
                    ));
                }else {

                    System.out.println("""
                        {
                            "Plan Reference Number":%s,
                            "first name":"%s",
                            "last name":"%s",
                            "Yearly Salary in (USD)":"%s",
                            "Employment Date":%s,
                            "Enrollment Date":%s,
                            "Monthly Contribution":%s
                        },
                        """.formatted(
                                " ",
                            employee.firstName(),
                            employee.lastName(),
                            employee.yearlySalary(),
                            employee.employmentDate(),
                            "",
                            ""
                    ));
                }
            }
        }
        System.out.println("]");
    }



}