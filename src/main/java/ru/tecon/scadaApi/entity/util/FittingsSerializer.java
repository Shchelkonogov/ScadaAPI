package ru.tecon.scadaApi.entity.util;

import ru.tecon.scadaApi.entity.FittingsEntity;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * Serializer json для объекта {@link FittingsEntity} с добавлением поля {@link FittingsEntity#getMuid()}
 *
 * @author Maksim Shchelkonogov
 */
public class FittingsSerializer implements JsonbSerializer<FittingsEntity> {

    @Override
    public void serialize(FittingsEntity obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartObject();
        if (obj.getMuid() != null) {
            generator.write("muid", obj.getMuid());
        }
        if (obj.getBrand() != null) {
            generator.write("brand", obj.getBrand());
        }
        if (obj.getFitName() != null) {
            generator.write("fitName", obj.getFitName());
        }
        if (obj.getFitNum() != null) {
            generator.write("fitNum", obj.getFitNum());
        }
        if (obj.getFitType() != null) {
            generator.write("fitType", obj.getFitType());
        }
        if (obj.getFitDesc() != null) {
            generator.write("firDesc", obj.getFitDesc());
        }
        if (obj.getFitDu() != null) {
            generator.write("fitDu", obj.getFitDu());
        }
        if (obj.getFitDriveType() != null) {
            generator.write("fitDriveType", obj.getFitDriveType());
        }
        if (obj.getFitPower() != null) {
            generator.write("fitPower", obj.getFitPower());
        }
        if (obj.getFitStat() != null) {
            generator.write("fitStat", obj.getFitStat());
        }
        if (obj.getFitBypassStat() != null) {
            generator.write("fitBypassStat", obj.getFitBypassStat());
        }
        if (obj.getFitJumperStat() != null) {
            generator.write("fitJumperStat", obj.getFitJumperStat());
        }
        if (obj.getClientId() != null) {
            generator.write("clientId", obj.getClientId());
        }
        if (obj.getTimeStamp() != null) {
            generator.write("timeStamp", new LocalDateTimeAdapter().marshal(obj.getTimeStamp()));
        }
        generator.writeEnd();
    }
}
