import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {

    @NotBlank
    @MaxLength(20)
    String name;

    @NotBlank
    @MaxLength(40)
    String email;

    SignupForm(String n, String e) {
        name = n;
        email = e;
    }
}

public class FormValidator {

    static List<String> validate(Object obj) throws IllegalAccessException {

        List<String> errors = new ArrayList<>();

        for (Field f : obj.getClass().getDeclaredFields()) {

            f.setAccessible(true);

            Object v = f.get(obj);

            if (f.isAnnotationPresent(NotBlank.class)
                    && (v == null || v.toString().isBlank())) {

                errors.add(f.getName() + " must not be blank");
            }

            MaxLength m = f.getAnnotation(MaxLength.class);

            if (m != null && v != null
                    && v.toString().length() > m.value()) {

                errors.add(f.getName() + " exceeds "
                        + m.value() + " characters");
            }
        }

        return errors;
    }

    public static void main(String[] args) throws Exception {

        SignupForm f = new SignupForm(
                "",
                "a-very-long-email-address-that-is-too-long@example.com"
        );

        System.out.println(validate(f));
    }
}     
