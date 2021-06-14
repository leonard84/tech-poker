package com.github.leonard84.techpoker.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

// currently not provided by spring boot 2 autoconfig, copied from spring boot 1
@Configuration
public class SpringMobileConfiguration {

    @Bean
    public SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor() {
        return new SitePreferenceHandlerInterceptor();
    }

    @Bean
    public SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver() {
        return new SitePreferenceHandlerMethodArgumentResolver();
    }

    @Bean
    @ConditionalOnBean({ThymeleafViewResolver.class})
    public LiteDeviceDelegatingViewResolver deviceDelegatingThymeleafViewResolver( ThymeleafViewResolver viewResolver) {
        LiteDeviceDelegatingViewResolver resolver =
                new LiteDeviceDelegatingViewResolver(viewResolver);

        resolver.setMobilePrefix("mobile/");
        resolver.setTabletPrefix("tablet/");
        return resolver;
    }

    @Configuration
    protected static class SitePreferenceMvcConfiguration implements WebMvcConfigurer {
        private final SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor;
        private final SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver;

        protected SitePreferenceMvcConfiguration(SitePreferenceHandlerInterceptor sitePreferenceHandlerInterceptor,
                SitePreferenceHandlerMethodArgumentResolver sitePreferenceHandlerMethodArgumentResolver) {
            this.sitePreferenceHandlerInterceptor = sitePreferenceHandlerInterceptor;
            this.sitePreferenceHandlerMethodArgumentResolver = sitePreferenceHandlerMethodArgumentResolver;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(this.sitePreferenceHandlerInterceptor);
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(this.sitePreferenceHandlerMethodArgumentResolver);
        }
    }


    @Bean
    @ConditionalOnMissingBean({ DeviceResolverHandlerInterceptor.class})
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }

    @Configuration
    @Order(0)
    protected static class DeviceResolverMvcConfiguration implements WebMvcConfigurer  {
        private DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor;
        private DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver;

        protected DeviceResolverMvcConfiguration(DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor,
                DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver) {
            this.deviceResolverHandlerInterceptor = deviceResolverHandlerInterceptor;
            this.deviceHandlerMethodArgumentResolver = deviceHandlerMethodArgumentResolver;
        }

        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(this.deviceResolverHandlerInterceptor);
        }

        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(this.deviceHandlerMethodArgumentResolver);
        }
    }

}
