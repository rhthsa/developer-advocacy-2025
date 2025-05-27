package ${{values.java_package_name}};

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
@ApplicationScoped
public class ClientResponseExceptionMapper implements ResponseExceptionMapper<RuntimeException>  {
    @Inject
    Logger logger;
    
    @Override
    public RuntimeException toThrowable(Response response) {
        logger.error("Return Code: " + response.getStatus());
        // throw new RuntimeException("The remote service responded with HTTP "+response.getStatus());
        return null;
    }   
}
