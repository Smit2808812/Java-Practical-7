import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class StudentRow {

    @Column(name = "student_name")
    String name;

    @Column(name = "age")
    int age;

    public String toString() {
        return name + " - " + age;
    }
}

public class ColumnMapper {

    static StudentRow map(String header, String data) throws Exception {

        String[] h = header.split(",");
        String[] v = data.split(",");

        StudentRow obj = new StudentRow();

        for (Field f : StudentRow.class.getDeclaredFields()) {

            Column c = f.getAnnotation(Column.class);

            for (int i = 0; i < h.length && i < v.length; i++) {

                if (c.name().equals(h[i].trim())) {

                    f.setAccessible(true);

                    if (f.getType() == int.class) {
                        f.setInt(obj, Integer.parseInt(v[i].trim()));
                    } else {
                        f.set(obj, v[i].trim());
                    }
                }
            }
        }

        return obj;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(
                map("student_name,age", "Prince,20")
        );
    }
}
