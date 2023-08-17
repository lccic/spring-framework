package org.springframework.aoptest;

public class UserService {

	public void save() {
		System.out.println("保存用户!");
	}
	public void delete() {
		System.out.println("删除用户!");
	}
	public void update() {
		System.out.println("更新用户!");
	}
	public void find() {
		System.out.println("查找用户!");
	}
}
