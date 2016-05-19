package camt.se331.shoppingcart.config;


import camt.se331.shoppingcart.common.SerializableResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Locale;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"camt.se331.shoppingcart"})
@EnableAspectJAutoProxy
@Import({PersistenceContext.class})
public class AppConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(false)
//                .allowedOrigins("*")
//                .allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE")
//                .exposedHeaders("Authorization", "Content-Type");
//    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // The localization here
    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver ret = new SessionLocaleResolver();
        ret.setDefaultLocale(new Locale("en"));
        return ret;
    }

    @Bean
    public MessageSource messageSource() {
        final SerializableResourceBundleMessageSource ret = new SerializableResourceBundleMessageSource();
        ret.setBasename("classpath:message");
        ret.setDefaultEncoding("UTF-8");
        return ret;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public HandlerInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localChangeInterceptor = new LocaleChangeInterceptor();
        localChangeInterceptor.setParamName("lang");
        return localChangeInterceptor;
    }

//    @Bean
//    public MultipartResolver filterMultipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(1000000);
//        return multipartResolver;
//    }

}
