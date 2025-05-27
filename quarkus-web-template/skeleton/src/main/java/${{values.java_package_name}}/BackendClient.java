package ${{values.java_package_name}};

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@RegisterClientHeaders(BackendClientHeaderFactory.class)
@RegisterProvider(ClientResponseExceptionMapper.class)
public interface BackendClient {

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    Response sendMessage();
}
