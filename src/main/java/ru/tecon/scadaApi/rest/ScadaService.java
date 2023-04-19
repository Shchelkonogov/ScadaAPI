package ru.tecon.scadaApi.rest;

import ru.tecon.scadaApi.ScadaApiException;
import ru.tecon.scadaApi.cdi.Json;
import ru.tecon.scadaApi.ejb.ScadaSB;
import ru.tecon.scadaApi.entity.FittingsEntity;
import ru.tecon.scadaApi.entity.HistLogEntity;
import ru.tecon.scadaApi.entity.PassportEntity;
import ru.tecon.scadaApi.entity.TubesEntity;
import ru.tecon.scadaApi.entity.util.FittingsSerializer;
import ru.tecon.scadaApi.entity.util.InterceptorRequired;
import ru.tecon.scadaApi.entity.util.TubesSerializer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Maksim Shchelkonogov
 */
@Path("/")
public class ScadaService {

    @EJB
    private ScadaSB scadaBean;

    @Inject
    private Logger logger;

    @Inject
    @Json(withNull = true)
    private Jsonb jsonb;

    @GET
    @Path("/hist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHist(@QueryParam("muid") String muid,
                            @QueryParam("startDate") LocalDateTime startDate,
                            @QueryParam("endDate") LocalDateTime endDate) {
        if ((muid != null) && (startDate != null) && (endDate != null)) {
            logger.log(Level.INFO, "get hist by muid and date {0} startDate {1} endDate {2}", new Object[]{muid, startDate, endDate});

            List<HistLogEntity> hist = scadaBean.getHistByMuidAndDate(muid, startDate, endDate);
            if (!hist.isEmpty()) {
                logger.log(Level.INFO, "find hist {0}", hist);
                return Response.ok(jsonb.toJson(hist)).build();
            }

            logger.log(Level.INFO, "no hist find for muid and date {0} startDate {1} endDate {2}", new Object[]{muid, startDate, endDate});
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        if (muid != null) {
            logger.log(Level.INFO, "get hist by muid {0}", muid);

            List<HistLogEntity> hist = scadaBean.getHistByMuid(muid);
            if (!hist.isEmpty()) {
                logger.log(Level.INFO, "find hist {0}", hist);
                return Response.ok(jsonb.toJson(hist)).build();
            }

            logger.log(Level.INFO, "no hist find for muid {0}", muid);
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        if ((startDate != null) && (endDate != null)) {
            logger.log(Level.INFO, "get hist by date startDate {0} endDate {1}", new Object[]{startDate, endDate});

            List<HistLogEntity> hist = scadaBean.getHistByDate(startDate, endDate);
            if (!hist.isEmpty()) {
                logger.log(Level.INFO, "find hist {0}", hist);
                return Response.ok(jsonb.toJson(hist)).build();
            }

            logger.log(Level.INFO, "no hist find for date startDate {0} endDate {1}", new Object[]{startDate, endDate});
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/tubeByBrand")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTubeByBrand(@QueryParam("brand") String brand) throws Exception {
        logger.log(Level.INFO, "get tube by brand {0}", brand);

        if (brand == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<TubesEntity> tubesByBrand = scadaBean.getTubesByBrand(brand);
        if (!tubesByBrand.isEmpty()) {
            logger.log(Level.INFO, "find tubes {0}", tubesByBrand);

            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withSerializers(new TubesSerializer());
            try (Jsonb jsonb = JsonbBuilder.create(config)) {
                return Response.ok(jsonb.toJson(tubesByBrand, new ArrayList<TubesEntity>(){}.getClass().getGenericSuperclass())).build();
            }
        }

        logger.log(Level.INFO, "no tubes find for brand {0}", brand);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/fittingByBrand")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFittingByBrand(@QueryParam("brand") String brand) throws Exception {
        logger.log(Level.INFO, "get fitting by brand {0}", brand);

        if (brand == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<FittingsEntity> fittingsByBrand = scadaBean.getFittingsByBrand(brand);
        if (!fittingsByBrand.isEmpty()) {
            logger.log(Level.INFO, "find fitting {0}", fittingsByBrand);

            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withSerializers(new FittingsSerializer());
            try (Jsonb jsonb = JsonbBuilder.create(config)) {
                return Response.ok(jsonb.toJson(fittingsByBrand, new ArrayList<FittingsEntity>(){}.getClass().getGenericSuperclass())).build();
            }
        }

        logger.log(Level.INFO, "no fitting find for brand {0}", brand);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/entity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntity(@QueryParam("muid") String muid) throws Exception {
        logger.log(Level.INFO, "get entity {0}", muid);

        if (muid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try (Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true))) {
            TubesEntity tube = scadaBean.getTubeByMuid(muid);
            if (tube != null) {
                logger.log(Level.INFO, "find tube {0}", tube);
                return Response.ok(jsonb.toJson(tube)).build();
            }

            FittingsEntity fitting = scadaBean.getFittingByMuid(muid);
            if (fitting != null) {
                logger.log(Level.INFO, "find fitting {0}", fitting);
                return Response.ok(jsonb.toJson(fitting)).build();
            }
        }

        logger.log(Level.INFO, "no entity find for muid: {0}", muid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/tube")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    @InterceptorRequired
    public Response setTube(@QueryParam("muid") String muid, TubesEntity tube, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        try {
            if (muid != null) {
                logger.log(Level.INFO, "update tube {0} new data {1}", new Object[]{muid, tube});
                scadaBean.updateTube(muid, tube);
            } else {
                logger.log(Level.INFO, "create tube {0}", tube);
                scadaBean.addTube(tube);
            }
        } catch (ScadaApiException ex) {
            if (ex.getMessage() != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }

        return Response.status(Response.Status.CREATED).entity(tube.getMuid()).build();
    }

    @POST
    @Path("/fitting")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    @InterceptorRequired
    public Response setFitting(@QueryParam("muid") String muid, FittingsEntity fitting, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        try {
            if (muid != null) {
                logger.log(Level.INFO, "update fitting {0} new data {1}", new Object[]{muid, fitting});
                scadaBean.updateFitting(muid, fitting);
            } else {
                logger.log(Level.INFO, "create fitting {0}", fitting);
                scadaBean.addFitting(fitting);
            }
        } catch (ScadaApiException ex) {
            if (ex.getMessage() != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }

        return Response.status(Response.Status.CREATED).entity(fitting.getMuid()).build();
    }

    @DELETE
    @Path("/entity")
    public Response removeEntity(@QueryParam("muid") String muid, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (muid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.log(Level.INFO, "remove entity {0}", muid);
        return (scadaBean.deleteTube(muid) || scadaBean.deleteFitting(muid)) ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/passport")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPassport(@QueryParam("muid") Long muid) throws Exception {
        logger.log(Level.INFO, "get passport {0}", muid);

        if (muid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try (Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true))) {
            PassportEntity passport = scadaBean.getPassportByMuid(muid);
            if (passport != null) {
                logger.log(Level.INFO, "find passport {0}", passport);
                return Response.ok(jsonb.toJson(passport)).build();
            }

            logger.log(Level.INFO, "no passport find for muid: {0}", muid);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @POST
    @Path("/passport")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    @InterceptorRequired
    public Response createPassport(@QueryParam("muid") Long muid, PassportEntity passport, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<PassportEntity>> validate = validator.validate(passport);
            if (!validate.isEmpty()) {
                String validateResult = validate.stream()
                        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                        .collect(Collectors.joining(", ", "Ошибки проверки (", ")"));

                return Response.status(Response.Status.BAD_REQUEST).entity(validateResult).build();
            }
        }

        try {
            if (muid != null) {
                logger.log(Level.INFO, "update passport {0} new data {1}", new Object[]{muid, passport});
                scadaBean.updatePassport(muid, passport);
            } else {
                logger.log(Level.INFO, "create passport {0}", passport);
                scadaBean.addPassport(passport);
            }
        } catch (ScadaApiException e) {
            if (e.getMessage() != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/passport")
    public Response deletePassport(@QueryParam("muid") Long muid, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (muid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger.log(Level.INFO, "remove entity {0}", muid);
        return scadaBean.deletePassport(muid) ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.NO_CONTENT).build();
    }
}
