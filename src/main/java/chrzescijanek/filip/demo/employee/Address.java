package chrzescijanek.filip.demo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @NotBlank
    private String streetName;

    @NotBlank
    private String buildingNo;

    @NotBlank
    private String apartmentNo;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

}
