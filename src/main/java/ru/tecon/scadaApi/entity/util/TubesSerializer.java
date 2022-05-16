package ru.tecon.scadaApi.entity.util;

import ru.tecon.scadaApi.entity.TubesEntity;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * @author Maksim Shchelkonogov
 */
public class TubesSerializer implements JsonbSerializer<TubesEntity> {

    @Override
    public void serialize(TubesEntity obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartObject();
        if (obj.getMuid() != null) {
            generator.write("muid", obj.getMuid());
        }
        if (obj.getBrand() != null) {
            generator.write("brand", obj.getBrand());
        }
        if (obj.getClientId() != null) {
            generator.write("clientId", obj.getClientId());
        }
        if (obj.getStatus() != null) {
            generator.write("status", obj.getStatus());
        }
        if (obj.getTimeStamp() != null) {
            generator.write("timeStamp", new LocalDateTimeAdapter().marshal(obj.getTimeStamp()));
        }
        generator.writeEnd();
    }
}
