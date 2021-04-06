package com.epam.esm.configuration.additional;

//public class ControllerInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] {"/"};
//    }
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[] {ControllerConfig.class};
//    }
//
//    @Override
//    protected  Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] {DispatcherConfig.class};
//    }
//}










//public class ControllerInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        //create the root Spring application context
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(ApplicationConfig.class);
//
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//
//        //Create the dispatcher servlet's Spring application context
//        AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
//        servletAppContext.register(MVCConfig.class);
//
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
//        // throw NoHandlerFoundException to controller ExceptionHandler.class. Used for <error-page> analogue
//        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
//
//        //register and map the dispatcher servlet
//        //note Dispatcher servlet with constructor arguments
//        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//
//        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
//        encodingFilter.setInitParameter("encoding", "UTF-8");
//        encodingFilter.setInitParameter("forceEncoding", "true");
//        encodingFilter.addMappingForUrlPatterns(null, true, "/*");

//    }
//
//    /**
//     * added to load spring security filter in root context (created in onStartup())
//     */
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[] {ApplicationConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] {"/"};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] {MVCConfig.class};
//    }
//}
