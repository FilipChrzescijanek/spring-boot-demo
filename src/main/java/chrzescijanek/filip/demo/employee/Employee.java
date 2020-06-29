package chrzescijanek.filip.demo.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Schema(required = true)
    private String id;

    @NotBlank(message = "Employee has to have a first name")
    private String firstName;

    @NotBlank(message = "Employee has to have a last name")
    private String lastName;

    @NotNull(message = "Employee's age has to be specified")
    @Min(value = 18, message = "Employee has to be an adult (with age greater than or equal to 18)")
    private Integer age;

    @NotNull(message = "Employee's gender has to be specified")
    private Gender gender;

    @NotEmpty(message = "Employee has to have at least one address")
    private List<@Valid Address> addresses;

    public Employee(String firstName, String lastName, Integer age, Gender gender, List<Address> addresses) {
        this(null, firstName, lastName, age, gender, addresses);
    }

    public Employee apply(@NonNull Dto dto) {
        return new Employee(
                this.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge(),
                dto.getGender(),
                dto.getAddresses());
    }

    public Dto toDto() {
        return new Dto(
                getFirstName(),
                getLastName(),
                getAge(),
                getGender(),
                getAddresses());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dto {

        @Schema(required = true)
        private String firstName;

        @Schema(required = true)
        private String lastName;

        @Schema(required = true)
        private Integer age;

        @Schema(required = true)
        private Gender gender;

        @Schema(required = true)
        private List<Address> addresses;

        public Employee toEmployee() {
            return new Employee(
                    getFirstName(),
                    getLastName(),
                    getAge(),
                    getGender(),
                    getAddresses());
        }

    }

}
