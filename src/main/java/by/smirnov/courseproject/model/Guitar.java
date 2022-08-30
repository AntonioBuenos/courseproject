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
public class Guitar {

    private long id;
    private String typeOf;
    private String shape;
    private String series;
    private String model;
    private int stringsQnt;
    private String neck;
    private String bridge;
    private String bodyMaterial;
    private double price;
    private String prodCountry;
    private long brandId;
    private Timestamp creationDate;
    private Timestamp modificationDate;
    private boolean isDeleted;
    private Timestamp terminationDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
