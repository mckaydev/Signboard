package com.project.Web;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("contextConfigLocation", "/WEB-INF/applicationContext.xml");

        ServletContextListener listener = new ContextLoaderListener();
        servletContext.addListener(listener);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet());
        dispatcher.setInitParameter("contextConfigLocation", "/WEB-INF/dispatcher-servlet.xml");
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration encoding = servletContext
                .addFilter("encodingFilter", CharacterEncodingFilter.class);
        encoding.setInitParameter("encoding","UTF-8");
        encoding.setInitParameter("forceEncoding", "true");
        encoding.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");

    }
}