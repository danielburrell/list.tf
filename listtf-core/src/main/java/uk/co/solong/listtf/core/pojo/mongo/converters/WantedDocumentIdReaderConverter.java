package uk.co.solong.listtf.core.pojo.mongo.converters;

import org.springframework.core.convert.converter.Converter;

import uk.co.solong.listtf.core.pojo.mongo.WantedDocumentId;

import com.mongodb.DBObject;

public class WantedDocumentIdReaderConverter implements Converter<DBObject, WantedDocumentId> {

    @Override
    public WantedDocumentId convert(DBObject o) {
        String userId = (String) o.get("userId");
        Integer defIndex = (Integer) o.get("defIndex");
        WantedDocumentId id = new WantedDocumentId(userId, defIndex);
        return id;
    }

}
