package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;

	// hashmap to register RPC methods which are required to implement RPCImpl

	private HashMap<Integer, RPCImpl> services;

	public RPCServer(int port) {

		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer, RPCImpl>();

		// the stop RPC methods is built into the server
		services.put((int) RPCCommon.RPIDSTOP, new RPCServerStopImpl());
	}

	public void run() {
		System.out.println("RPC SERVER RUN - Services: " + services.size());
		connection = msgserver.accept();
		System.out.println("RPC SERVER ACCEPTED");
		boolean stop = false;
		while (!stop) {
			Message receivedMessage = connection.receive();
			byte[] data = receivedMessage.getData();
			int rpcid = data[0];
			RPCImpl find = services.get(rpcid);
			byte[] answer = find.invoke(data);
			Message response = new Message(answer);
			connection.send(response);
			if (rpcid == RPCCommon.RPIDSTOP) {
				stop = true;
			}
		}

	}

	public void register(int rmid, RPCImpl impl) {
		services.put(rmid, impl);
	}

	public void stop() {
		connection.close();
		msgserver.stop();

	}
}
