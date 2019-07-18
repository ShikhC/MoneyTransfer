package com.moneytransfer.demo.bootstrap;

import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.reflections.Reflections;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.Path;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.EnumSet;
import java.util.Set;

/**
 * Hello world!
 */
public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

    private static final Reflections reflections = new Reflections("com.moneytransfer.demo");


    public static void main(String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MoneyTransferConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<MoneyTransferConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(MoneyTransferConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
        super.initialize(bootstrap);
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS",
                CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

    @Override
    public void run(MoneyTransferConfiguration config, Environment environment) throws Exception {
        environment.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        Injector injector = Guice.createInjector(new MoneyTransferModule(config));
        addAppResources(environment, injector);
        JmxReporter.forRegistry(environment.metrics()).build().start();
        addExceptionMappers(environment, injector);
        configureCors(environment);
    }

    private void addAppResources(Environment environment, Injector injector) {
        Set<Class<?>> resourceClasses = reflections.getTypesAnnotatedWith(Path.class);
        for (Class<?> aClass : resourceClasses) {
            environment.jersey().register(injector.getInstance(aClass));
        }
    }

    private void addExceptionMappers(Environment environment, Injector injector) {
        Set<Class<? extends ExceptionMapper>> exceptionMapperClasses = reflections.getSubTypesOf(ExceptionMapper.class);
        for (Class<? extends ExceptionMapper> aClass : exceptionMapperClasses) {
            environment.jersey().getResourceConfig().register(injector.getInstance(aClass));
        }
    }
}
