package com.douzone.config.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.internal.metadata.aggregated.GetterCascadable.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration // Config class 만들 때는 달아주면 좋음
@EnableWebMvc  // extends 할 때
			   // Message Converter, handler map, View Resolver 같은 기본 기능을 자동으로 설정해 주기 때문에 생생해야 함
public class MvcConfig extends WebMvcConfigurerAdapter {

	// View Resolver
//	<!-- ViewResolver 설정 -->
//	<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//		<property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
//		<property name="prefix" value="/WEB-INF/views/" />
//		<property name="suffix" value=".jsp" />
//	</bean>
	@Bean
	public ViewResolver viewResolver() { // spring-servlet.xml 에서 해 놓은 설정 가져오기
		// id 보통 인터페이스 (같은 인터페이스 있을 수도 있으니 조심하기)
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// class - InternalResourceViewResolver => ViewResolver implements 해서 성질 받기
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
		// String으로 변환시켜주는/ Json 문자열, xml ... 로 변환 
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
		// StringHttpMessageConverter : 일반 문자열로 전달 
		
		
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
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
			.indentOutput(true)
			.dateFormat(new SimpleDateFormat("yyyy-mm-dd"));
		
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(builder.build());
		messageConverter.setSupportedMediaTypes(
			Arrays.asList(
				new MediaType("application", "json", Charset.forName("utf-8"))
			)
		);
		
		return messageConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

	// Default Servlet Handler
	// <bean class="com.douzone.mysite.security.AuthUserHandlerMethodArgumentResolver" />
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	

}
