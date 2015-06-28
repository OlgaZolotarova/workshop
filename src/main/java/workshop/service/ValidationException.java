package workshop.service;

import java.util.List;

import net.sf.oval.ConstraintViolation;

/**
 * This exception is thrown by ClientService methods in case client contains invalid values.
 * @author olga
 *
 */
public class ValidationException extends RuntimeException {

    
    private static final long serialVersionUID = 805307695180654691L;
    private List<ConstraintViolation> errors;

    public ValidationException(List<ConstraintViolation> errors) {
        this.errors = errors;
    }
    public List<ConstraintViolation> getErrors() {
        return errors;
    }
    @Override
    public String toString() {
        return "ValidationException [errors=" + errors + "]";
    }
    
    
}
