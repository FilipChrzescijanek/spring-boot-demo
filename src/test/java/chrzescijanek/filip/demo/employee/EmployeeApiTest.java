package chrzescijanek.filip.demo.employee;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static chrzescijanek.filip.demo.employee.EmployeeApiRouter.PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DataLoader dataLoader;

    private Employee[] employees;

    @BeforeEach
    public void setUp() {
        employees = dataLoader.reset().toStream().toArray(Employee[]::new);
    }

    @Test
    public void testList() {
        webTestClient
                .get().uri(PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(Employee.class)
                .hasSize(10)
                .contains(employees);
    }

    @Test
    public void testAdd() {
        val dto = new Employee.Dto("firstName", "lastName", 18, Gender.MALE,
                List.of(new Address("streetName", "1", "2", "12-345", "City", "Country")));
        val mono = Mono.just(dto);
        webTestClient
                .post().uri(PATH)
                .body(mono, Employee.Dto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(Employee.class)
                .consumeWith(response -> Assertions.assertEquals(Objects.requireNonNull(response.getResponseBody()).toDto(), dto));
    }

    @Test
    public void testGet() {
        val employee = Objects.requireNonNull(Flux.just(employees).blockFirst());
        webTestClient
                .get().uri(PATH + "/" + employee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(Employee.class)
                .isEqualTo(employee);
    }

    @Test
    public void testEdit() {
        val employee = Objects.requireNonNull(Flux.just(employees).blockFirst());
        val dto = new Employee.Dto("firstName", "lastName", 18, Gender.MALE,
                List.of(new Address("streetName", "1", "2", "12-345", "City", "Country")));
        val mono = Mono.just(dto);
        webTestClient
                .patch().uri(PATH + "/" + employee.getId())
                .body(mono, Employee.Dto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(Employee.class)
                .consumeWith(response -> Assertions.assertEquals(Objects.requireNonNull(response.getResponseBody()).toDto(), dto))
                .consumeWith(response -> Assertions.assertEquals(Objects.requireNonNull(response.getResponseBody()).getId(), employee.getId()));
    }

    @Test
    public void testRemove() {
        val employee = Objects.requireNonNull(Flux.just(employees).blockFirst());
        webTestClient
                .delete().uri(PATH + "/" + employee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(Employee.class)
                .isEqualTo(employee);
        webTestClient
                .get().uri(PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(Employee.class)
                .hasSize(9)
                .doesNotContain(employee);
    }

}