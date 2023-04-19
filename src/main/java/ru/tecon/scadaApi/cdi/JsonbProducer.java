package ru.tecon.scadaApi.cdi;

import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * @author Maksim Shchelkonogov
 * 19.04.2023
 */
public class JsonbProducer {

    @Produces
    @Json(withNull = true)
    private Jsonb getJsonb() {
        return JsonbBuilder.create(
                new JsonbConfig()
                        .withNullValues(true)
                        .withFormatting(true));
    }
}
