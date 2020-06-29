package chrzescijanek.filip.demo.employee;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class EmployeeHandler {

    @Getter
    @Autowired
    private EmployeeRepository employeeRepository;

    public Mono<ServerResponse> add(ServerRequest request) {
        return notFound().build();
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        return notFound().build();
    }

    public Mono<ServerResponse> edit(ServerRequest request) {
        return notFound().build();
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        return notFound().build();
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return notFound().build();
    }

}

