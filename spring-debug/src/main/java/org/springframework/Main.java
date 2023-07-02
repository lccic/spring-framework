package org.springframework;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-beans.xml");
		System.out.println(applicationContext.getBean(Person.class));
	}
}