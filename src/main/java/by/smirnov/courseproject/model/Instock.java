package by.smirnov.courseproject.model;

import by.smirnov.courseproject.model.enums.GoodStatus;
import by.smirnov.courseproject.model.enums.Placement;
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
@Table(name = "instock")
public class Instock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "good_id")
    private long goodId;

    @Enumerated(EnumType.STRING)
    private Placement placement;

    @Column(name = "good_status")
    @Enumerated(EnumType.STRING)
    private GoodStatus goodStatus;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "date_sold")
    private boolean dateSold;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
