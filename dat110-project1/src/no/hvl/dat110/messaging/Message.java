package no.hvl.dat110.messaging;

import java.util.Arrays;

public class Message {

	private byte[] payload;

	public Message(byte[] payload) {
		if(payload.length > 127) {
			//Payload size is not in range
			this.payload = null;
		}
		this.payload = payload;
	}

	public Message() {
		super();
	}

	public byte[] getData() {
		return this.payload; 
	}

	public byte[] encapsulate() {
		byte[] encoded = new byte[128];
		encoded[0] = (byte) payload.length;
		for(int a= 0 ; a < payload.length ; a++) {
			encoded[a+1] = payload[a];
		}

		return encoded;
		
	}

	public void decapsulate(byte[] received) {
		byte[] payload = new byte[received[0]];
		for(int a = 0 ; a < received[0] ; a++) {
			payload[a] = received[a+1];
		}
		this.payload = payload;
	}
}

  
