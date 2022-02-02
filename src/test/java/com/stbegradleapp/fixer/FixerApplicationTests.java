package com.stbegradleapp.fixer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class FixerApplicationTests {
//	@Autowired
//	UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	public void testCreateUser(){
//		FixerUser user = userService.createUser();
//		System.out.println(user.toString());
	}
}
