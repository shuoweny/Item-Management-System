package au.edu.unimelb.team.twelve.itemmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ArgumentResponse<T> {
    @NonNull
    Boolean success;
    T contents;
}
