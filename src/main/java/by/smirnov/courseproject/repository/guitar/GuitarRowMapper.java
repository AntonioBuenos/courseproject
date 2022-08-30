package by.smirnov.courseproject.repository.guitar;

import by.smirnov.courseproject.model.Guitar;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.smirnov.courseproject.repository.guitar.GuitarTableColumns.*;

@Component
public class GuitarRowMapper implements RowMapper<Guitar> {

    public Guitar mapRow(ResultSet rs, int i) throws SQLException {
        Guitar guitar = new Guitar();

        guitar.setId(rs.getLong(ID));
        guitar.setTypeOf(rs.getString(TYPEOF));
        guitar.setShape(rs.getString(SHAPE));
        guitar.setSeries(rs.getString(SERIES));
        guitar.setModel(rs.getString(MODEL));
        guitar.setStringsQnt(rs.getInt(STRINGS_QNT));
        guitar.setNeck(rs.getString(NECK));
        guitar.setBridge(rs.getString(BRIDGE));
        guitar.setBodyMaterial(rs.getString(BODY_MATERIAL));
        guitar.setPrice(rs.getDouble(PRICE));
        guitar.setProdCountry(rs.getString(PROD_COUNTRY));
        guitar.setBrandId(rs.getLong(BRAND_ID));
        guitar.setCreationDate(rs.getTimestamp(CREATED));
        guitar.setModificationDate(rs.getTimestamp(CHANGED));
        guitar.setDeleted(rs.getBoolean(IS_DELETED));
        guitar.setTerminationDate(rs.getTimestamp(GuitarTableColumns.TERMINATED));

        return guitar;
    }
}
