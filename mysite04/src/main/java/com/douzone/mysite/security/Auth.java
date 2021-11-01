package com.douzone.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth { // 스프링시큐리티에서는 지원하지만 여기서는 그냥 만든 것!
	// public String value() default "USER";
	public String role() default "USER";
	// public boolean test() default false;
}
