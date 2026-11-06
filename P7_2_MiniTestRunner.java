import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class SampleTests {

    @Run
    void testAddition() {
        System.out.println("Addition test passed");
    }

    @Run
    void testText() {
        System.out.println("Text test passed");
    }

    void helper() {
        System.out.println("Not run");
    }
}

public class MiniTestRunner {

    public static void main(String[] args) throws Exception {

        Object test = new SampleTests();

        int count = 0;

        for (Method m : test.getClass().getDeclaredMethods()) {

            if (m.isAnnotationPresent(Run.class)
                    && m.getParameterCount() == 0) {

                m.invoke(test);
                count++;
            }
        }

        System.out.println("Tests ran: " + count);
    }
}
