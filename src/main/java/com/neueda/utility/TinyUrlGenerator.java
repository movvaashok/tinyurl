package com.neueda.utility;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;
import com.neueda.beans.UrlBean;
import com.neueda.controllers.UrlController;
import com.neueda.dao.UrlDAO;

@Component
public class TinyUrlGenerator {
	@Autowired
	UrlDAO urlDao;
	@Value("${baseurl.domain}")
	private String baseUrlDomain;
	@Value("${tinyurl.key.length}")
	private String tinyUrlKeyLength;
	Logger logger = LoggerFactory.getLogger(TinyUrlGenerator.class);

	private Optional<String> generateUniqueKey(String originalUrl) {
		if (originalUrl.length() > 0 && Integer.valueOf(tinyUrlKeyLength) > 0) {
			logger.info("Creating unique key for:" + originalUrl);
			return Optional.of(Hashing.sha256().hashString(originalUrl, StandardCharsets.UTF_8).toString().substring(0,
					Integer.valueOf(tinyUrlKeyLength)));
		} else {
			return Optional.empty();
		}
	}

	public UrlBean createUrlMapping(String originalUrl) {
		logger.info("Creating Url mapping for :" + originalUrl);
		Optional<String> uniqueKey = generateUniqueKey(originalUrl);
		if (uniqueKey.isPresent()) {
			logger.info("Generated unique key:" + uniqueKey.get());
			String tinyUrl = baseUrlDomain + uniqueKey.get();
			String hashKey = uniqueKey.get();
			return new UrlBean(originalUrl, tinyUrl, hashKey);
		} else {
			logger.info("Unable to create url mapping");
			return new UrlBean();
		}
	}
}
