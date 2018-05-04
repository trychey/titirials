package com.baeldung.contexts.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //XML Context
        //XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        //rootContext.setConfigLocations("/WEB-INF/rootApplicationContext.xml");
        //Annotations Context
        //AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(RootApplicationConfig.class);
        //Registration
        //servletContext.addListener(new ContextLoaderListener(rootContext));
        
        XmlWebApplicationContext normalWebAppContext = new XmlWebApplicationContext();
        normalWebAppContext.setConfigLocation("/WEB-INF/normal-webapp-servlet.xml");
        ServletRegistration.Dynamic normal = servletContext.addServlet("normal-webapp", new DispatcherServlet(normalWebAppContext));
        normal.setLoadOnStartup(1);
        normal.addMapping("/api/*");
    }

}
