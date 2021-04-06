package com.epam.esm.configuration;

//public class AnnotationWebAppInitializer implements WebApplicationInitializer {
//
//    private static final String PROD_PROFILE = "prod";
//    private static final String PACKAGE_PATH = "com.epam.esm.controller";
//
//    @Override
//    public void onStartup(ServletContext servletContext) {
//        AnnotationConfigWebApplicationContext context
//                = new AnnotationConfigWebApplicationContext();
//
//        context.getEnvironment().setActiveProfiles(PROD_PROFILE);
//
//        context.scan(PACKAGE_PATH);
//        servletContext.addListener(new ContextLoaderListener(context));
//
//        ServletRegistration.Dynamic dispatcher = servletContext
//                .addServlet("dispatcher", new DispatcherServlet(new AnnotationConfigWebApplicationContext()));
//
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//
//    }


//        private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
//
//        @Override
//        public void onStartup(ServletContext servletContext) throws ServletException {
//            AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//            // регистрируем конфигурацию созданую высше
//            ctx.register(ServletConfig.class);
//            // добавляем в контекст слушателя с нашей конфигурацией
//            servletContext.addListener(new ContextLoaderListener(ctx));
//
//            ctx.setServletContext(servletContext);
//
//            // настраиваем маппинг Dispatcher Servlet-а
//            ServletRegistration.Dynamic servlet =
//                    servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(ctx));
//            servlet.addMapping("/");
//            servlet.setLoadOnStartup(1);
//        }

//}