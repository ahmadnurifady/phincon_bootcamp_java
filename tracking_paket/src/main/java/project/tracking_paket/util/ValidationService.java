package project.tracking_paket.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

    @Autowired
    private Validator validator;

    public void validate(Object request) {
        Set<ConstraintViolation<Object>> validate = validator.validate(request);

        log.info(getClass().getSimpleName() + " validate -> " + validate);

        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
    }
}
