package com.neueda.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.neueda.beans.UrlBean;
import com.neueda.utility.TinyUrlGenerator;

import java.io.IOException;

@SpringBootTest
@ContextConfiguration(classes = { TinyUrlGenerator.class, UrlDAO.class })
class UrlDAOTest {
	@Autowired
	UrlDAO urlDao;
	@Autowired
	TinyUrlGenerator tinyUrlGenerator;

	@Test
	void testSaveTinyUrl() {
		UrlBean urlMapping = tinyUrlGenerator.createUrlMapping("http://www.google.com");
		urlDao.saveTinyUrl(urlMapping);
	}

	@Test
	void testGetOriginalUrl() {
		String OriginalUrl = "www.google.com";
		UrlBean urlMapping = tinyUrlGenerator.createUrlMapping(OriginalUrl);
		String tinyUrl = urlMapping.getTinyUrl();
		urlDao.saveTinyUrl(urlMapping);
		assertEquals(OriginalUrl, urlDao.getOriginalUrl(tinyUrl).get().getAsString());
	}

	@Test
	void testKeyExists() {
		String OriginalUrl = "www.google.com";
		UrlBean urlMapping = tinyUrlGenerator.createUrlMapping(OriginalUrl);
		String tinyUrlKey = urlMapping.getHashKey();
		urlDao.saveTinyUrl(urlMapping);
		assertEquals(true, urlDao.keyExists(tinyUrlKey));
	}


	@Test
	void testSaveUrlMappings() throws IOException {
		String OriginalUrl = "www.google.com";
		UrlBean urlMapping = tinyUrlGenerator.createUrlMapping(OriginalUrl);
		urlDao.saveTinyUrl(urlMapping);
		assertEquals(true, urlDao.saveUrlMappings());
	}
}
