package com.bulletinboard;

import java.util.Scanner;

import com.bulletinboard.BulletinBoardGrpc.BulletinBoardBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BulletinBoardClient {
	
	static int port = 6050;
	static BulletinBoardBlockingStub stub;

	public static void main(String[] args) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build();
		stub = BulletinBoardGrpc.newBlockingStub(channel);
		
		Scanner sc = new Scanner(System.in);
		
		// Accept input loop
		while(true) {
			System.out.print("> ");
			String[] input = sc.nextLine().split(" ", 2);
					
			if(input[0].equals("exit"))
				break;
			
			if(input[0].equals("post")) {
				addPost(input[0], input[1]);
			}
			
			if(input[0].equals("list")) {
				listPosts();
			}
		}
		
		sc.close();
	}
	
	public static void addPost(String title, String body) {
		addPostRequest req = addPostRequest.newBuilder().setTitle(title).setBody(body).build();
		
		addPostResponse res = stub.addPost(req);
		
		System.out.println(res.getResponse());
	}
	
	public static void listPosts() {
		System.out.println("Posts: ");
		getPostsRequest req = getPostsRequest.newBuilder().build();
		getPostsResponse res = stub.getPosts(req);
		
		int length = res.getTitleCount();
		for(int i = 0; i < length; i++) {
			String title = res.getTitle(i);
			String body = res.getBody(i);
			System.out.println("\t" + i + ". " + title + " : " + body);
		}
	}

}
