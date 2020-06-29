package chrzescijanek.filip.demo.common;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebInputException;

@Component
public class IdValidator implements CustomValidator<String> {

    @Override
    public String validate(String id) {
        try {
            return new ObjectId(id).toHexString();
        } catch (IllegalArgumentException e) {
            throw new ServerWebInputException(e.getMessage());
        }
    }

}
