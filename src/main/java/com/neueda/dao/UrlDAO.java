package com.neueda.dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neueda.beans.UrlBean;

@Component
public class UrlDAO {
	JsonObject urlMappingData;
	@Value("${urlMapping.file}")
	private String urlMappingsFile;
	@Value("${baseurl.domain}")
	private String domainName;
	Logger logger = LoggerFactory.getLogger(UrlDAO.class);

	public UrlDAO() {
		urlMappingData = getStoredData();
	}

	@SuppressWarnings("deprecation")
	private JsonObject getStoredData() {
		logger.debug("Fetching url mappings from mappings.json");
		try {
			File tempFile = new File("./mappings.json");
			if (tempFile.exists()) {
				logger.info("Mappings file found");
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(new FileReader(tempFile));
				return jsonElement.getAsJsonObject();
			}
		} catch (Exception e) {
			return new JsonObject();
		}
		return new JsonObject();
	}

	public void saveTinyUrl(UrlBean urlMapping) {
		logger.info("Saving url mapping " + urlMapping.toString());
		urlMappingData.addProperty(urlMapping.getTinyUrl(), urlMapping.getOriginalUrl());
		saveUrlMappings();
	}

	public Optional<JsonElement> getOriginalUrl(String tinyUrl) {
		logger.info("Checking for url mapping for: " + tinyUrl);
		return Optional.ofNullable(urlMappingData.get(tinyUrl));
	}

	public boolean keyExists(String tinyUrlkey) {
		logger.info("Checking for tinyurl key exists: " + tinyUrlkey);
		String tinyUrl = domainName + tinyUrlkey;
		return Optional.ofNullable(urlMappingData.get(tinyUrl)).isPresent();
	}

	public boolean saveUrlMappings() {
		try {
			logger.info("Writing url Mappings to file");
			return Files.write(Paths.get(urlMappingsFile), urlMappingData.toString().getBytes()) != null;
		} catch (IOException e) {
			return false;
		}
	}
}
