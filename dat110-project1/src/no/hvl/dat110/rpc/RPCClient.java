package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.*;

public class RPCClient {

	private MessagingClient msgclient;
	private Connection connection;

	public RPCClient(String server, int port) {

		msgclient = new MessagingClient(server, port);
	}

	public void register(RPCStub remote) {
		remote.register(this);
	}

	public void connect() {
		// connect using the underlying messaging layer connection

		// Null check necessary, because connection could already be established
		if (connection == null) {
			try {
				connection = msgclient.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void disconnect() {
		// disconnect using the underlying messaging layer connection
		try {
			// Consider throwing error if connection is null?
			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] call(byte[] rpcrequest) {
		Message requestMessage = new Message(rpcrequest);
		connection.send(requestMessage);
		Message receivedMessage = connection.receive();
		byte[] rpcreply = receivedMessage.getData();
		return rpcreply;

	}

}
