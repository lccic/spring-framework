package org.springframework.cglibtest;


import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

public class CglibTest {

	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/lccic/Documents/java/project/spring-framework/spring-debug/src/main/java/org/springframework/cglibtest");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Calculator.class);
		enhancer.setCallback(new CustomMethodInterceptor());
		Calculator calculator = (Calculator) enhancer.create();
		calculator.add(1, 2);
	}
}
