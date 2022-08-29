package by.smirnov.courseproject.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //необходимо будет вставить валидаторы
    private long id;
    private String firstName;
    private String lastName;
    private String userRole;
    private Timestamp creationDate;
    private Timestamp modificationDate;
    private boolean isDeleted;
    private Timestamp terminationDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
