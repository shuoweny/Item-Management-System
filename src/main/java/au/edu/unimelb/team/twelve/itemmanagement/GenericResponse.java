package au.edu.unimelb.team.twelve.itemmanagement;

import lombok.NonNull;

public class GenericResponse extends ArgumentResponse<Object> {
    public GenericResponse(@NonNull Boolean success, Object contents) {
        super(success, contents);
    }

    public GenericResponse(@NonNull Boolean success) {
        super(success);
    }
}
