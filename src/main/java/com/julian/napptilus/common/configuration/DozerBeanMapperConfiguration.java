package com.julian.napptilus.common.configuration;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DozerBeanMapperConfiguration {

    @Bean
    public DozerBeanMapper dozerBean() {
        List<String> mappingFiles = List.of("dozerJdk8Converters.xml");
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

}
