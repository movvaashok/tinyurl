package com.neueda.beans;

import org.springframework.stereotype.Component;

@Component
public class UrlBean {
	private String originalUrl;
	private String tinyUrl;
	private String hashKey;

	public UrlBean() {

	}

	public UrlBean(String originalUrl, String tinyUrl, String hashKey) {
		this.originalUrl = originalUrl;
		this.tinyUrl = tinyUrl;
		this.hashKey = hashKey;

	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getTinyUrl() {
		return tinyUrl;
	}

	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String toString() {
		return "Original Url :" + this.originalUrl + " Tiny Url :" + this.tinyUrl;
	}

}
