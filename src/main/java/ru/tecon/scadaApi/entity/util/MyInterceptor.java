package ru.tecon.scadaApi.entity.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Перехватчик сообщений переданных по JAX-RS
 *
 * @author Maksim Shchelkonogov
 */
@Provider
@InterceptorRequired
public class MyInterceptor implements ReaderInterceptor {

    private static final Logger LOGGER = Logger.getLogger(MyInterceptor.class.getName());

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        InputStream in = context.getInputStream();
        String body = new BufferedReader(new InputStreamReader(in))
                .lines()
                .collect(Collectors.joining("\n"));
        LOGGER.log(Level.INFO, "incoming data {0}", body);
        context.setInputStream(new ByteArrayInputStream(body.getBytes()));
        return context.proceed();
    }
}
