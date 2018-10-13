package com.bulletinboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bulletinboard.getPostsResponse.Builder;

import io.grpc.stub.StreamObserver;

public class BulletinBoardService extends BulletinBoardGrpc.BulletinBoardImplBase {
	
	// This hash table stores all the posts. The key is title and the value is post contents
	HashMap<String, String> POSTS = new HashMap<String, String>();
	
	public void addPost(addPostRequest request, StreamObserver<addPostResponse> responseObserver) {
		String title = request.getTitle();
		String body = request.getBody();
		
		System.out.println("Posting post with title \"" + title + "\" and body \"" + body + "\"");
		POSTS.put(title,  body);
		
		addPostResponse res = addPostResponse.newBuilder().setResponse("success").build();
		responseObserver.onNext(res);
		responseObserver.onCompleted();
	}
	
	public void getPosts(getPostsRequest request, StreamObserver<getPostsResponse> responseObserver) {
		Builder builder = getPostsResponse.newBuilder();
		
		/*
		 * This part doesn't work
		 * Causes index out of bounds exception (not the one you're used to) it's something to do with GRPC
		 * The approach I tried is setting the response body and title to be a repeatable string, so that you can set them by index number
		 * It looks like something I'm doing isn't right and the builder.setTitle(i, key) causes the error I think
		 * might have to first initialize the amount of posts
		 */
				
		List<String> keyList = new ArrayList<String>(POSTS.keySet());
		
		for(int i = 0; i < keyList.size(); i++) {
			System.out.println("About to crash");
			String key = keyList.get(i);
			builder.setTitle(i, key);
			builder.setBody(i, POSTS.get(key));
		}
		
		getPostsResponse res = builder.build();
		responseObserver.onNext(res);
		responseObserver.onCompleted();
		
	}
}
