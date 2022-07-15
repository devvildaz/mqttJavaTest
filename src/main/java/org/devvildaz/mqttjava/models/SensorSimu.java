package org.devvildaz.mqttjava.models;

import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class SensorSimu implements Callable<Void> {
	
	private IMqttClient _client;
	
	public SensorSimu(IMqttClient client) throws MqttSecurityException, MqttException {
		this._client = client;
	}
	
	@Override
	public Void call() throws Exception {
		MqttMessage msg = readValue();
		msg.setQos(0);
		msg.setRetained(true);
		this._client.publish(AppParameters.MAIN_TOPIC, msg);
		return null;
	}
	
	private MqttMessage readValue() {
		byte[] payload = String.format("%s","SomeContentOfPayload").getBytes();
		return new MqttMessage(payload);
	}
}
