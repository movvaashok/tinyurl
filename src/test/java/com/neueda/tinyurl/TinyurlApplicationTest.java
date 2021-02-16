package com.neueda.tinyurl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.neueda.controllers.UrlController;

@SpringBootTest
class TinyurlApplicationTest {

	@Autowired
	private UrlController urlController;

	@SuppressWarnings("deprecation")
	@Test
	public void contextLoads() {
		Assert.notNull(urlController);
	}

}
