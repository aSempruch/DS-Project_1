package com.bulletinboard;

import java.util.Scanner;

import com.bulletinboard.BulletinBoardGrpc.BulletinBoardBlockingStub;
import com.bulletinboard.getPostsResponse.Post;

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
			String[] input = sc.nextLine().split("\"");

			//split by quotes so there will be an extra space in front if its 1 of these 3
			//other wise exit and list will be fine
			if(input[0].equals("post ") ){ input[0] = "post"; }
			if(input[0].equals("delete ") ){ input[0] = "delete"; }
			if(input[0].equals("get ") ){ input[0] = "get"; }


			if(input[0].equals("exit"))
				break;
			
			//if the input is for a post the parameters will be 1 and 3
			//otherwise if the command is get or delete it will just be 1 and 2
			if(input[0].equals("post")) {
				addPost(input[1], input[3]);
			}
			
			if(input[0].equals("list")) {
				listPosts();
			}

			//delete and get methods
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
		
		int length = res.getPostsCount();
		for(int i = 0; i < length; i++) {
			Post post = res.getPosts(i);
			String title = post.getTitle();
			String body = post.getBody();
			System.out.println("\t" + (i+1) + ". " + title + " : " + body);
		}
	}

}
