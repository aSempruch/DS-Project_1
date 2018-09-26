package com.bulletinboard;

import com.bulletinboard.BulletinBoardGrpc.BulletinBoardBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BulletinBoardClient {

	public static void main(String[] args) {
		
		/* Stuff TA did in recitation */
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5000).userPlainText().build();
		
		BulletinBoardBlockingStub stub = BulletinBoardGrpc.newBlockingStub(channel);
		
		messageRequest req = messageRequest.newBuilder().setMessage("Hello").build();
		
		messageResponse res = stub.processMessage(req);
		
		System.out.println(res.getMessagesCount());
	}

}
