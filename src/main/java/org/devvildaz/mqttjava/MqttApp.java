package org.devvildaz.mqttjava;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;

import org.devvildaz.mqttjava.models.AppConnectOptions;
import org.devvildaz.mqttjava.models.AppParameters;
import org.devvildaz.mqttjava.models.SensorSimu;

public class MqttApp {
    public static void main(String[] args) {
    	System.out.println("===== MQTT APP =====");
    	try 
    	{
    		MqttClient publisher = buildNewClient();
    		publisher.connect(AppConnectOptions
    				.build()
    				.setUsername("admin")
    				.setPassword("admin")
    				.getMqttConnectOptions()
			);
    		System.out.println("Publisher connected: " + publisher.isConnected());
    		
    		
    		MqttClient subscriber = buildNewClient();
    		subscriber.connect(
				AppConnectOptions
				.build()
				.setUsername("client1")
				.setPassword("client1")
				.getMqttConnectOptions()
			);
    		System.out.println("Subcriber connected: " + subscriber.isConnected());
    		SensorSimu sensor = new SensorSimu(publisher);
    		System.out.println("Sending message");
    		sensor.call();
    		CountDownLatch receivedSignal = new CountDownLatch(5);
    		subscriber.subscribe(AppParameters.MAIN_TOPIC, (topic, msg) -> {
    			byte[] payload = msg.getPayload();
    			System.out.println(new String(payload, StandardCharsets.UTF_8));
    			receivedSignal.countDown();
    		});
    		receivedSignal.await();
    		subscriber.close();
    		publisher.close();
    	}
    	catch(MqttException e) 
    	{
    		System.out.print(e.getReasonCode() + ":");
    		System.out.println(e.getMessage());
    	}
    	catch(InterruptedException e)
    	{
    		System.out.println(e.getCause());
    		System.out.println(e.getMessage());
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getLocalizedMessage());
    		System.out.println(e.getMessage());
    	}
    	finally
    	{
    		
    	}
    	
    }

    private static MqttClient buildNewClient() throws MqttException {
        String clientId = UUID.randomUUID().toString();
        return new MqttClient(AppParameters.BROKER, clientId);
    }
    
}
