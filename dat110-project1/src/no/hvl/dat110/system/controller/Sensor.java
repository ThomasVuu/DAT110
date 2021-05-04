package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class Sensor extends RPCStub {

	private byte RPCID = 2;
	
	public int read() {
	
	
		rmiclient.connect();
	
		
		byte[] bytesToSend = RPCUtils.marshallInteger(RPCID, 0);
		
		byte[] byteReceived = rmiclient.call(bytesToSend);
		
		int temp = RPCUtils.unmarshallInteger(byteReceived);
		
		return temp;
	}
	
}
