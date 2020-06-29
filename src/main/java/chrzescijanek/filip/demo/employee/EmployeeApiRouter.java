package chrzescijanek.filip.demo.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class EmployeeApiRouter {

    public static final String PATH = "/employees";

    @RouterOperations({
            @RouterOperation(
                    path = PATH,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "listEmployees",
                            summary = "List employees",
                            tags = {"Employee API"},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Employees successfully listed",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Employee.class))))
                            })),
            @RouterOperation(
                    path = PATH,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "addEmployee",
                            summary = "Add employee",
                            tags = {"Employee API"},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Employee.Dto.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Employee successfully added",
                                            content = @Content(schema = @Schema(implementation = Employee.class))),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request")
                            })),
            @RouterOperation(
                    path = PATH + "/{id}",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getEmployee",
                            summary = "Get employee by ID",
                            tags = {"Employee API"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Employee ID")},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Employee successfully retrieved",
                                            content = @Content(schema = @Schema(implementation = Employee.class))),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid Employee ID supplied"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Employee not found")
                            })),
            @RouterOperation(
                    path = PATH + "/{id}",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "editEmployee",
                            summary = "Edit employee by ID",
                            tags = {"Employee API"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Employee ID")},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Employee.Dto.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "202",
                                            description = "Employee successfully edited",
                                            content = @Content(schema = @Schema(implementation = Employee.class))),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid Employee ID supplied"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Employee not found")
                            })),
            @RouterOperation(
                    path = PATH + "/{id}",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "removeEmployee",
                            summary = "Remove employee by ID",
                            tags = {"Employee API"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Employee ID")},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Employee successfully removed",
                                            content = @Content(schema = @Schema(implementation = Employee.class))),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid Employee ID supplied"),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Employee not found")
                            }))
    })
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
