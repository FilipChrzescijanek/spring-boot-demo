package chrzescijanek.filip.demo.employee;

import chrzescijanek.filip.demo.common.IdValidator;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class EmployeeHandler {

    @Getter
    @Autowired
    private EmployeeValidator employeeValidator;

    @Getter
    @Autowired
    private IdValidator IdValidator;

    @Getter
    @Autowired
    private EmployeeRepository employeeRepository;

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(Employee.Dto.class)
                .map(Employee.Dto::toEmployee)
                .doOnNext(getEmployeeValidator()::validate)
                .flatMap(getEmployeeRepository()::save)
                .flatMap(u -> ok().contentType(APPLICATION_JSON).bodyValue(u))
                .switchIfEmpty(badRequest().build());
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        val userId = getIdValidator().validate(request.pathVariable("id"));
        return getEmployeeRepository().findById(userId)
                .flatMap(u -> getEmployeeRepository().deleteById(u.getId()).thenReturn(u))
                .flatMap(u -> ok().contentType(APPLICATION_JSON).bodyValue(u))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> edit(ServerRequest request) {
        val userId = getIdValidator().validate(request.pathVariable("id"));
        return request.bodyToMono(Employee.Dto.class)
                .flatMap(dto -> getEmployeeRepository().findById(userId).map(u -> u.apply(dto)))
                .doOnNext(getEmployeeValidator()::validate)
                .flatMap(getEmployeeRepository()::save)
                .flatMap(u -> accepted().contentType(APPLICATION_JSON).bodyValue(u))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        val userId = getIdValidator().validate(request.pathVariable("id"));
        return getEmployeeRepository().findById(userId)
                .flatMap(u -> ok().contentType(APPLICATION_JSON).bodyValue(u))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(getEmployeeRepository().findAll(), Employee.class);
    }

}

