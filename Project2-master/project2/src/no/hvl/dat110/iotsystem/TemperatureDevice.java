package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class TemperatureDevice {
	
	private static final int COUNT = 10;
	private static int BROKER_PORT = 8080;
	
	public static void main(String[] args) {
		
		TemperatureSensor sn = new TemperatureSensor();
		
		int port = BROKER_PORT;
		
		String user = "temp device";
		Client client = new Client(user, "localhost", port);

		client.connect();


		for (int i = 0; i < COUNT; i++){
		    String temp = Integer.toString(sn.read());
			client.publish("Temperature", temp);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
		client.disconnect();
		
		System.out.println("Temperature device stopping ... ");
		
		throw new RuntimeException("not yet implemented");
		
	}
}
