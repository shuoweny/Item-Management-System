package au.edu.unimelb.team.twelve.itemmanagement;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthedUser {
    String value() default "user";
}
