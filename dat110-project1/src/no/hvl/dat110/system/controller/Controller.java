package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCServerStopStub;

public class Controller {

	private static final int N = 5;

	public static void main(String[] args) {

		Display display = new Display();
		Sensor sensor = new Sensor();
		RPCClient displayclient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
		RPCClient sensorclient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);
		RPCServerStopStub stopdisplay = new RPCServerStopStub();
		RPCServerStopStub stopsensor = new RPCServerStopStub();

		System.out.println("Controller starting ...");
		
		displayclient.register(display);
		sensorclient.register(sensor);

		displayclient.register(stopdisplay);
		sensorclient.register(stopsensor);

		
		for (int i = 0; i<N; i++) {
			int value = sensor.read();
			display.write(value + " °C");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		stopdisplay.stop();
		stopsensor.stop();

		displayclient.disconnect();
		sensorclient.disconnect();

		System.out.println("Controller stopping ...");

	}
}