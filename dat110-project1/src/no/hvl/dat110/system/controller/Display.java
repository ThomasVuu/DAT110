package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class Display extends RPCStub {

	private byte RPCID = 1;

	public void write(String message) {
		
		rmiclient.connect();
		
		byte[] bytesToSend = RPCUtils.marshallString(RPCID, message);
		
		RPCUtils.unmarshallVoid(rmiclient.call(bytesToSend));

	}
}
