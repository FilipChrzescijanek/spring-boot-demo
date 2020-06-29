package chrzescijanek.filip.demo.employee;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataLoader {

    @Getter
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    private void load() {
        reset().subscribe();
    }

    Flux<Employee> reset() {
        return getEmployeeRepository()
                .deleteAll()
                .thenMany(getEmployeeRepository()
                        .saveAll(Flux.just(getExampleData())));
    }

    private Employee[] getExampleData() {
        return new Employee[]{
                new Employee("Jimmy", "Doe", 22, Gender.MALE, List.of(
                        new Address("Jimmy Doe's street", "1", "2", "12-345", "Jimmy Doe's City", "Jimmy Doe's Country"))),
                new Employee("Helen", "Mirren", 33, Gender.FEMALE, List.of(
                        new Address("Helen Mirren's street", "2", "3", "12-345", "Helen Mirren's City", "Helen Mirren's Country"))),
                new Employee("Johnny", "Bar", 30, Gender.MALE, List.of(
                        new Address("Johnny Bar's street", "1", "2", "12-345", "Johnny Bar's City", "Johnny Bar's Country"),
                        new Address("Johnny Bar's street", "7", "5", "21-345", "Johnny Bar's Other City", "Johnny Bar's Country"),
                        new Address("Johnny Bar's street", "4", "7", "54-321", "Johnny Bar's Other City", "Johnny Bar's Other Country"))),
                new Employee("Lenny", "Penny", 55, Gender.MALE, List.of(
                        new Address("Lenny Penny's street", "1", "2", "12-345", "Lenny Penny's City", "Lenny Penny's Country"),
                        new Address("Lenny Penny's street", "2", "1", "21-345", "Lenny Penny's Other City", "Lenny Penny's Country"))),
                new Employee("Jimmy", "Rimmel", 50, Gender.MALE, List.of(
                        new Address("Jimmy Rimmel's street", "11", "22", "12-345", "Jimmy Rimmel's City", "Jimmy Rimmel's Country"))),
                new Employee("Hannah", "Montana", 30, Gender.FEMALE, List.of(
                        new Address("Hannah Montana's street", "5", "7", "12-345", "Hannah Montana's City", "Hannah Montana's Country"))),
                new Employee("Rocky", "Balboa", 72, Gender.MALE, List.of(
                        new Address("Rocky Balboa's street", "11", "12", "12-345", "Rocky Balboa's City", "Rocky Balboa's Country"))),
                new Employee("Kelly", "Bundy", 21, Gender.FEMALE, List.of(
                        new Address("Kelly Bundy's street", "1", "7", "12-345", "Kelly Bundy's City", "Kelly Bundy's Country"))),
                new Employee("Jill", "Pattison", 51, Gender.OTHER, List.of(
                        new Address("Jill Pattison's street", "21", "12", "12-345", "Jill Pattison's City", "Jill Pattison's Country"))),
                new Employee("Brianna", "Foo", 28, Gender.FEMALE, List.of(
                        new Address("Brianna Foo's street", "1", "2", "12-345", "Brianna Foo's City", "Brianna Foo's Country"),
                        new Address("Brianna Foo's street", "11", "23", "21-345", "Brianna Foo's Other City", "Brianna Foo's Country"),
                        new Address("Brianna Foo's street", "111", "234", "54-321", "Brianna Foo's Other City", "Brianna Foo's Other Country")))
        };
    }

}