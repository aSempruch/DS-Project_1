package com.bulletinboard;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class BulletinBoardServer {

	public static void main(String[] args) {
		
		/* Stuff TA did in recitation */
		
		Server server = ServerBuilder.forPort(5000).addService(new BulletinBoardService()).build();
		
		server.start();
		
		System.out.println("Server Starting on port 5000");
		
		server.awaitTermination();
	}
}
