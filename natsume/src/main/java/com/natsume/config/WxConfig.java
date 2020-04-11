package com.natsume.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxConfig {
	private String appId;

	private String mchKey;

	private String openIdUrl;
}
