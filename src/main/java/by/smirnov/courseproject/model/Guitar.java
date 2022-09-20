package by.smirnov.courseproject.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guitars")
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "typeof")
    private String typeOf;

    private String shape;

    private String series;

    private String model;

    @Column(name = "strings_qnt")
    private int stringsQnt;

    private String neck;

    private String bridge;

    @Column(name = "body_material")
    private String bodyMaterial;

    private double price;

    @Column(name = "prod_country")
    private String prodCountry;

    @Column(name = "brand_id")
    private long brandId;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
