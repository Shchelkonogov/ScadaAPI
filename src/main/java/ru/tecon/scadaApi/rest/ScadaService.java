package ru.tecon.scadaApi.rest;

import ru.tecon.scadaApi.ejb.ScadaSB;
import ru.tecon.scadaApi.entity.FittingsEntity;
import ru.tecon.scadaApi.entity.HistLogEntity;
import ru.tecon.scadaApi.entity.TubesEntity;
import ru.tecon.scadaApi.entity.util.FittingsSerializer;
import ru.tecon.scadaApi.entity.util.TubesSerializer;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maksim Shchelkonogov
 */
@Path("/")
public class ScadaService {

    private static final Logger LOGGER = Logger.getLogger(ScadaService.class.getName());

    @EJB
    private ScadaSB scadaBean;

    @GET
    @Path("/hist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHist(@QueryParam("muid") String muid,
                            @QueryParam("startDate") LocalDateTime startDate,
                            @QueryParam("endDate") LocalDateTime endDate) {
        if ((muid != null) && (startDate != null) && (endDate != null)) {
            LOGGER.log(Level.INFO, "get hist by muid and date {0} startDate {1} endDate {2}", new Object[] {muid, startDate, endDate});

            List<HistLogEntity> hist = scadaBean.getHistByMuidAndDate(muid, startDate, endDate);
            if (!hist.isEmpty()) {
                LOGGER.log(Level.INFO, "find hist {0}", hist);

                Jsonb jsonb = JsonbBuilder.create(
                        new JsonbConfig()
                                .withNullValues(true)
                                .withFormatting(true)
                );

                return Response.ok(jsonb.toJson(hist)).build();
            }

            LOGGER.log(Level.INFO, "no hist find for muid and date {0} startDate {1} endDate {2}", new Object[] {muid, startDate, endDate});
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        if (muid != null) {
            LOGGER.log(Level.INFO, "get hist by muid {0}", muid);

            List<HistLogEntity> hist = scadaBean.getHistByMuid(muid);
            if (!hist.isEmpty()) {
                LOGGER.log(Level.INFO, "find hist {0}", hist);

                Jsonb jsonb = JsonbBuilder.create(
                        new JsonbConfig()
                                .withNullValues(true)
                                .withFormatting(true)
                );

                return Response.ok(jsonb.toJson(hist)).build();
            }

            LOGGER.log(Level.INFO, "no hist find for muid {0}", muid);
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        if ((startDate != null) && (endDate != null)) {
            LOGGER.log(Level.INFO, "get hist by date startDate {0} endDate {1}", new Object[] {startDate, endDate});

            List<HistLogEntity> hist = scadaBean.getHistByDate(startDate, endDate);
            if (!hist.isEmpty()) {
                LOGGER.log(Level.INFO, "find hist {0}", hist);

                Jsonb jsonb = JsonbBuilder.create(
                        new JsonbConfig()
                                .withNullValues(true)
                                .withFormatting(true)
                );

                return Response.ok(jsonb.toJson(hist)).build();
            }

            LOGGER.log(Level.INFO, "no hist find for date startDate {0} endDate {1}", new Object[] {startDate, endDate});
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/tubeByBrand")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTubeByBrand(@QueryParam("brand") String brand) {
        LOGGER.log(Level.INFO, "get tube by brand {0}", brand);

        if (brand == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<TubesEntity> tubesByBrand = scadaBean.getTubesByBrand(brand);
        if (!tubesByBrand.isEmpty()) {
            LOGGER.log(Level.INFO, "find tubes {0}", tubesByBrand);

            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withSerializers(new TubesSerializer());
            Jsonb jsonb = JsonbBuilder.create(config);

            return Response.ok(jsonb.toJson(tubesByBrand, new ArrayList<TubesEntity>(){}.getClass().getGenericSuperclass())).build();
        }

        LOGGER.log(Level.INFO, "no tubes find for brand {0}", brand);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/fittingByBrand")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFittingByBrand(@QueryParam("brand") String brand) {
        LOGGER.log(Level.INFO, "get fitting by brand {0}", brand);

        if (brand == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<FittingsEntity> fittingsByBrand = scadaBean.getFittingsByBrand(brand);
        if (!fittingsByBrand.isEmpty()) {
            LOGGER.log(Level.INFO, "find fitting {0}", fittingsByBrand);

            JsonbConfig config = new JsonbConfig()
                    .withFormatting(true)
                    .withSerializers(new FittingsSerializer());
            Jsonb jsonb = JsonbBuilder.create(config);

            return Response.ok(jsonb.toJson(fittingsByBrand, new ArrayList<FittingsEntity>(){}.getClass().getGenericSuperclass())).build();
        }

        LOGGER.log(Level.INFO, "no fitting find for brand {0}", brand);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/entity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntity(@QueryParam("muid") String muid) {
        LOGGER.log(Level.INFO, "get entity {0}", muid);

        if (muid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

        TubesEntity tube = scadaBean.getTubeByMuid(muid);
        if (tube != null) {
            LOGGER.log(Level.INFO, "find tube {0}", tube);
            return Response.ok(jsonb.toJson(tube)).build();
        }

        FittingsEntity fitting = scadaBean.getFittingByMuid(muid);
        if (fitting != null) {
            LOGGER.log(Level.INFO, "find fitting {0}", fitting);
            return Response.ok(jsonb.toJson(fitting)).build();
        }

        LOGGER.log(Level.INFO, "no entity find for muid: {0}", muid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/tube")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response setTube(@QueryParam("muid") String muid, TubesEntity tube, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        boolean result;

        if (muid != null) {
            LOGGER.log(Level.INFO, "update tube {0} new data {1}", new Object[] {muid, tube});
            result = scadaBean.updateTube(muid, tube);
        } else {
            LOGGER.log(Level.INFO, "create tube {0}", tube);
            result = scadaBean.addTube(tube);
        }

        return result ?
                Response.status(Response.Status.CREATED).entity(tube.getMuid()).build() :
                Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/fitting")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response setFitting(@QueryParam("muid") String muid, FittingsEntity fitting, @Context SecurityContext securityContext) {
        if (!securityContext.isUserInRole("USER")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        boolean result;

        if (muid != null) {
            LOGGER.log(Level.INFO, "update fitting {0} new data {1}", new Object[] {muid, fitting});
            result = scadaBean.updateFitting(muid, fitting);
        } else {
            LOGGER.log(Level.INFO, "create fitting {0}", fitting);
            result = scadaBean.addFitting(fitting);
        }
        return result ?
                Response.status(Response.Status.CREATED).entity(fitting.getMuid()).build() :
                Response.status(Response.Status.NO_CONTENT).build();
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

        LOGGER.log(Level.INFO, "remove entity {0}", muid);
        return (scadaBean.deleteTube(muid) || scadaBean.deleteFitting(muid)) ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.NO_CONTENT).build();
    }
}
