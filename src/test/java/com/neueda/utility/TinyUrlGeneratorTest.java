package com.neueda.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.neueda.beans.UrlBean;
import com.neueda.dao.UrlDAO;

@SpringBootTest
@ContextConfiguration(classes = { TinyUrlGenerator.class, UrlDAO.class })
@PropertySource("classpath:application.properties")
class TinyUrlGeneratorTest {
	@Autowired
	private TinyUrlGenerator tinyUrlGenerator;
	@Value("${baseurl.domain}")
	private String baseUrlDomain;
	@Value("${tinyurl.key.length}")
	private String tinyUrlKeyLength;

	//@Test
	public void testCreateUrlMapping() {
		UrlBean urlMapping = tinyUrlGenerator.createUrlMapping("www.google.com");
		assertTrue(urlMapping.getTinyUrl().contains(baseUrlDomain));
		assertTrue(urlMapping.getTinyUrl().replace(baseUrlDomain, "").length() == Integer.valueOf(tinyUrlKeyLength));
	}

	@Test
	public void createTinyUrlWithNoInput() {
		Assertions.assertThrows(NullPointerException.class, ()->{
			tinyUrlGenerator.createUrlMapping("").getTinyUrl().isEmpty();	
		});
	}
}
