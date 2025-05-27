package ${{values.java_package_name}};

import java.util.concurrent.atomic.AtomicBoolean;

import jakarta.ws.rs.core.Application;


public class ApplicationConfig extends Application {
    public static final AtomicBoolean IS_ALIVE = new AtomicBoolean(true);
    public static final AtomicBoolean IS_READY = new AtomicBoolean(true);
}
