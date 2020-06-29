package chrzescijanek.filip.demo.employee;

import chrzescijanek.filip.demo.common.CustomValidator;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

@Component
public class EmployeeValidator implements CustomValidator<Employee> {

    @Getter
    @Autowired
    private Validator validator;

    @Override
    public Employee validate(Employee employee) {
        val errors = new BeanPropertyBindingResult(employee, "employee");
        getValidator().validate(employee, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
        return employee;
    }

}
