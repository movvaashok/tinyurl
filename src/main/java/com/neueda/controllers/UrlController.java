package com.neueda.controllers;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.JsonElement;
import com.neueda.beans.UrlBean;
import com.neueda.dao.UrlDAO;
import com.neueda.utility.TinyUrlGenerator;

@RestController
public class UrlController {
	@Autowired
	TinyUrlGenerator tinyUrlGenerator;
	@Autowired
	UrlDAO urlDao;
	Logger logger = LoggerFactory.getLogger(UrlController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("*/createTinyUrl")
	public ResponseEntity<JSONObject> getTinyUrl(@RequestBody Map<String, String> originalUrlInformation) {
		if (originalUrlInformation.keySet().contains("originalUrl")) {
			logger.info("Creating tiny url for: " + originalUrlInformation.get("originalUrl"));
			UrlBean urlMapping = tinyUrlGenerator.createUrlMapping(originalUrlInformation.get("originalUrl"));
			logger.info("Tiny url created: " + urlMapping.getTinyUrl());
			urlDao.saveTinyUrl(urlMapping);
			JSONObject obj = new JSONObject();
			obj.put("tinyUrl", urlMapping.getTinyUrl());
			return ResponseEntity.ok(obj);
		} else {
			logger.info("Original url not found in request body.");
			JSONObject obj = new JSONObject();
			obj.put("reason", "Original url not found");
			return ResponseEntity.badRequest().body(obj);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("*/getOriginalUrl")
	public ResponseEntity<JSONObject> getLargeUrl(@RequestParam String tiny) {
		logger.info("Fetching tiny url for :" + tiny);
		Optional<JsonElement> originalUrlInfo = urlDao.getOriginalUrl(tiny);
		JSONObject obj = new JSONObject();
		if (originalUrlInfo.isPresent()) {
			logger.info("Original url found.");
			obj.put("OriginalURL", originalUrlInfo.get().getAsString());
			return ResponseEntity.ok(obj);
		} else {
			logger.info("No mapping found for given tiny url.");
			obj.put("reason", "No Mapping found");
			return ResponseEntity.badRequest().body(obj);
		}
	}

	@GetMapping("*/viewOriginalUrl")
	public RedirectView viewOriginalUrl(@RequestParam String tiny) {
		logger.info("Fetching tiny url for :" + tiny);
		Optional<JsonElement> originalUrlInfo = urlDao.getOriginalUrl(tiny);
		if (originalUrlInfo.isPresent()) {
			logger.info("Original url found.");
			RedirectView rv = new RedirectView();
	        rv.setUrl(originalUrlInfo.get().getAsString());
	        return rv;
		} else {
			logger.info("No mapping found for given tiny url.");
			RedirectView rv = new RedirectView();
	        rv.setUrl("./resources/error.html");
	        return rv;
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("*/save")
	public ResponseEntity<JSONObject> saveCreatedMappings() {
		JSONObject obj = new JSONObject();
		logger.info("Trying to save url mappings");
		if (urlDao.saveUrlMappings()) {
			logger.info("Mappings saved successfully");
			obj.put("reason", "mappings saved");
			return ResponseEntity.ok(obj);
		} else {
			logger.info("Can not save mappings");
			obj.put("reason", "unable to save mappings");
			return ResponseEntity.unprocessableEntity().body(obj);
		}
	}
}
