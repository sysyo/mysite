package com.douzone.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) 
// TYPE : 클래스 / 인터페이스 / 열거 타입(enum)을 뜻
@Retention(RetentionPolicy.RUNTIME) // 어느 시점까지 어노테이션의 메모리를 가져갈 지 설정
public @interface Auth { // 스프링시큐리티에서는 지원하지만 여기서는 그냥 만든 것!
	// public String value() default "USER";
	public String role() default "USER";
	// public boolean test() default false;
}
