package com.douzone.config.web;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc // Message Converter, handler map, View Resolver 같은 기본 기능을 자동으로 설정해 주기 때문에 생생해야
				// 함
public class MvcConfig extends WebMvcConfigurerAdapter {

	// View Resolver
	@Bean
	public ViewResolver viewResolver() { // spring-servlet.xml 에서 해 놓은 설정 가져오기
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	// Message Converter
	// spring-servlet.xml 부분
	// <!-- validator, conversionService, messageConverter를 자동으로 등록 -->
	// <mvc:annotation-driven>
	// <mvc:message-converters>
	// <bean class="org.springframework.http.converter.StringHttpMessageConverter">
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();

		List<MediaType> list = new ArrayList<>();
//		list.add(new MediaType("text", "html", Charset.forName("utf-8")));
//		messageConverter.setSupportedMediaTypes(list);

		messageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("text", "html", Charset.forName("utf-8"))));

		return messageConverter;
	}

	// <bean
	// class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return null;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

}
