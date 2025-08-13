package com.example.otelDemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.profiles.active=test"})
class OtelDemoApplicationTests {

	@Test
	void contextLoads() {
	}
}
