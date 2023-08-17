package org.springframework.aoptest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop-config.xml");
		UserService userService = applicationContext.getBean(UserService.class);
		userService.find();
	}
}
