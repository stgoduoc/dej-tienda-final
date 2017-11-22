package cl.duoc.dej.tienda.validacion;

import cl.duoc.dej.tienda.entity.Categoria;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;

public class CategoriaValidacionTest {
    
    @Test
    public void validar() {
        Categoria categoria = new Categoria("a", "");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Categoria>> violaciones = validator.validate(categoria);
        for(ConstraintViolation<Categoria> v:violaciones) {
            System.out.println(v.getPropertyPath().toString() +" : "+ v.getMessage());
        }
    }
    
}
