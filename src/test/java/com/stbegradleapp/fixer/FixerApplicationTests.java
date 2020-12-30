package com.stbegradleapp.fixer;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.servises.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class FixerApplicationTests {
	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	public void testCreateUser(){
		FixerUser user = userService.createUser();
		System.out.println(user.toString());
	}
}
