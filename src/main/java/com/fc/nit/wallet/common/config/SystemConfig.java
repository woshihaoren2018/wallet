package com.fc.nit.wallet.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sys")
public class SystemConfig {

	private String fileUrl;
	private String networkUrl;
	private String lexiconFile;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getNetworkUrl() {
		return networkUrl;
	}

	public void setNetworkUrl(String networkUrl) {
		this.networkUrl = networkUrl;
	}

	public String getLexiconFile() {
		return lexiconFile;
	}

	public void setLexiconFile(String lexiconFile) {
		this.lexiconFile = lexiconFile;
	}
	
}
