package uk.co.solong.listtf.core.pojo.mongo.converters;

import org.springframework.core.convert.converter.Converter;

import uk.co.solong.listtf.core.pojo.mongo.WantedDocumentId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class WantedDocumentIdWriterConverter implements Converter<WantedDocumentId, DBObject> {

    private static final int FIELDS = 2;

    @Override
    public DBObject convert(WantedDocumentId source) {
        DBObject o = new BasicDBObject(FIELDS);
        o.put("userId", source.getUserId());
        o.put("defIndex", source.getDefIndex());
        return o;
    }

}