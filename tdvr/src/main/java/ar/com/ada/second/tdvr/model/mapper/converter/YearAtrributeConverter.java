package ar.com.ada.second.tdvr.model.mapper.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearAtrributeConverter implements AttributeConverter<Year, Short> {  // primero el tipo de dato en java
    // y el segundo como el que está en la base de datos

    @Override
    public Short convertToDatabaseColumn(Year year) { //este es el que va a la base de datos
        return year != null
                ? (short) year.getValue()
                : null;
    }

    @Override
    public Year convertToEntityAttribute(Short aShort) { //este  es cuando viene de la db
        return aShort != null
                ?Year.of(aShort)
                : null;
    }
}
