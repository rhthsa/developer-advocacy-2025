package ${{values.java_package_name}};

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Path("/")
// @RegisterProvider(ClientResponseExceptionMapper.class)
public class BackendResource {
    
    @ConfigProperty(name = "app.version", defaultValue = "v1")
    String version;

    @ConfigProperty(name = "app.backend", defaultValue = "http://localhost:8080/version")
    String backend;

    @ConfigProperty(name = "app.message", defaultValue = "Hello, World ^_^ ")
    String message;

    @ConfigProperty(name = "app.errorCodeNotLive", defaultValue = "503")
    String errorCodeNotLive;

    @ConfigProperty(name = "app.errorCodeNotReady", defaultValue = "504")
    String errorCodeNotReady;

    @ConfigProperty(name = "app.showResponse", defaultValue = "true")
    String showResponse;

    @Inject
    Logger logger;

    private final MeterRegistry registry;
    
    BackendResource(MeterRegistry registry) {
        
        this.registry = registry;
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Call Service")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))


    public Response callBackend(@Context HttpHeaders headers)  {
        if (ApplicationConfig.IS_ALIVE.get() && ApplicationConfig.IS_READY.get()) { 

                logger.info("Request to: " + backend);
                BackendClient backendClient =  RestClientBuilder.newBuilder()
                                                                .baseUri(URI.create(backend))
                                                                .connectTimeout(5, TimeUnit.SECONDS)
                                                                .readTimeout(5, TimeUnit.SECONDS)
                                                                .build(BackendClient.class);
                registry.counter("com.example.quarkus.BackendResource.countBackend").increment();
                Timer timer = registry.timer("com.example.quarkus.BackendResource.timeBackend");
                return timer.record(() -> {
                    Response response = backendClient.sendMessage();
                                    final int returnCode=response.getStatus();
                logger.info("Return Code: " + returnCode);
                if (Boolean.parseBoolean(showResponse)) {
                        message = response.readEntity(String.class);
                        logger.info("Response Body: " + message );
                    }
                    return Response.status(returnCode).encoding("text/plain")
                    .entity(generateMessage(message, Integer.toString(returnCode)))
                    .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                    .build();
                });
            } else {
                if (!ApplicationConfig.IS_ALIVE.get()) {
                    logger.info("Applicartion liveness is set to false, return " + errorCodeNotLive);
                    return Response.status(Integer.parseInt(errorCodeNotLive)).encoding("text/plain")
                        .entity(generateMessage("Application liveness is set to false", errorCodeNotLive))
                        .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                        .build();
                } else {
                    logger.info("Applicartion readiness is set to false, return " + errorCodeNotReady);
                    return Response.status(Integer.parseInt(errorCodeNotReady)).encoding("text/plain")
                        .entity(generateMessage("Application readiness is set to false", errorCodeNotReady))
                        .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                        .build();
                }

            }
        }

        @GET
        @Path("/version")
        @Produces(MediaType.TEXT_PLAIN)
        @Operation(summary = "Show Version")
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
        public Response version() {
            logger.info("Get Version:"+version);
            registry.counter("com.example.quarkus.BackendResource.countVersion").increment();
            return Response.ok()
                    .encoding("text/plain")
                    .entity(generateMessage("", "200"))
                    .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                    .build();
        }

        @GET
        @Path("/stop")
        @Produces(MediaType.TEXT_PLAIN)
        @Operation(summary = "Set Liveness to false")
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
        public Response stopApp() {
            ApplicationConfig.IS_ALIVE.set(false);
            logger.info("Set Liveness to false");
            registry.counter("com.example.quarkus.BackendResource.countStop").increment();
            return Response.ok()
                .encoding("text/plain")
                .entity(generateMessage("Liveness: " + ApplicationConfig.IS_ALIVE.get(), "200"))
                .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                .build();
        }

        @GET
        @Path("/not_ready")
        @Produces(MediaType.TEXT_PLAIN)
        @Operation(summary = "Set Readiness to false")
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
        public Response notReadyApp() {
            ApplicationConfig.IS_READY.set(false);
            logger.info("Set Readiness to false");
            registry.counter("com.example.quarkus.BackendResource.countNotReady").increment();
            return Response.ok().encoding("text/plain")
                .entity(generateMessage("Readiness: " + ApplicationConfig.IS_READY.get(), "200"))
                .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                .build();
        }

        @GET
        @Path("/start")
        @Produces(MediaType.TEXT_PLAIN)
        @Operation(summary = "Set Liveness to true")
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
        public Response startApp() {
            logger.info("Set Liveness to true");
            registry.counter("com.example.quarkus.BackendResource.countStart").increment();
            if (!ApplicationConfig.IS_ALIVE.get())
                ApplicationConfig.IS_ALIVE.set(true);
            return Response.ok().encoding("text/plain")
                .entity(generateMessage("Liveness: " + ApplicationConfig.IS_ALIVE.get(), "200"))
                .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                .build();
        }

        @GET
        @Path("/ready")
        @Produces(MediaType.TEXT_PLAIN)
        @Operation(summary = "Set Readiness to true")
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
        public Response readyApp() {
            logger.info("Set Readiness to true");
            ApplicationConfig.IS_READY.set(true);
            return Response.ok().encoding("text/plain")
                .entity(generateMessage("Readiness: " + ApplicationConfig.IS_READY.get(), "200"))
                .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                .build();
        }

        @GET
        @Path("/status")
        @Produces(MediaType.TEXT_PLAIN)
        @Operation(summary = "Show Liveness and Readiness")
        @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.TEXT_PLAIN))
        public Response statusApp() {
            logger.info("Check status");
            final String msg = "Liveness=" + ApplicationConfig.IS_ALIVE.get() + " Readiness=" +
                ApplicationConfig.IS_READY.get();
            return Response.ok()
                    .entity(generateMessage(msg, "200"))
                    .expires(Date.from(Instant.now().plus(Duration.ofMillis(0))))
                    .build();
        }

        private String generateMessage(final String msg, final String status) {
            //return "Backend version: " + version + ", Hostname: " + getLocalHostname() + ", Status: " + status + ", Message: " + msg;
            return "Backend version:" + version + ", Response:" + status + ", Host:" + getLocalHostname() + ", Status:" + status + ", Message: " + msg;
        }

        private String getLocalHostname() {
            InetAddress inetAddr;
            String hostname = "";
            try {
                inetAddr = InetAddress.getLocalHost();
                hostname = inetAddr.getHostName();
            } catch (final UnknownHostException e) {
                logger.error("Error get local hostname: " + e.getMessage());
            }
            return hostname;
        }
        // private String getHeader(HttpHeaders headers,String header){
        //     if(headers.getRequestHeaders().containsKey(header))
        //         return headers.getRequestHeader(header).get(0);
        //     else
        //         return "";
        // }


        
    }
