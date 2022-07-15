package org.devvildaz.mqttjava.models;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class AppConnectOptions {
	private MqttConnectOptions options;
	
	private AppConnectOptions() {
		this.options = getDefaultOptions();
	}
	
	public MqttConnectOptions getMqttConnectOptions() {
		return this.options;
	}
	
	public static AppConnectOptions build() {
		return new AppConnectOptions();
	}
	
	public AppConnectOptions setUsername(String clientId) {
		this.options.setUserName(clientId);
		return this;
	}
	
	public AppConnectOptions setPassword(String password) {
		this.options.setPassword(password.toCharArray());
		return this;
	}
	
	private static MqttConnectOptions getDefaultOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(true);
		options.setConnectionTimeout(1000);
		return options;
	}
}
