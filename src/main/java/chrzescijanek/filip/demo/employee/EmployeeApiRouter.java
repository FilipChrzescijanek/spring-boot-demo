package chrzescijanek.filip.demo.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class EmployeeApiRouter {

    public static final String PATH = "/employees";

    @Bean
    public RouterFunction<ServerResponse> route(EmployeeHandler employeeHandler) {
        return RouterFunctions.route()
                .path(PATH, builder -> builder
                        .nest(accept(APPLICATION_JSON), nested -> nested
                                .GET("", employeeHandler::list)
                                .POST("", employeeHandler::add)
                                .GET("/{id}", employeeHandler::get)
                                .PATCH("/{id}", employeeHandler::edit)
                                .DELETE("/{id}", employeeHandler::remove))
                )
                .build();
    }

}
