package com.bulletinboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bulletinboard.getPostsResponse.Builder;
import com.bulletinboard.getPostsResponse.Post;

import io.grpc.stub.StreamObserver;

public class BulletinBoardService extends BulletinBoardGrpc.BulletinBoardImplBase {
	
	// This hash table stores all the posts. The key is title and the value is post contents
	HashMap<String, String> POSTS = new HashMap<String, String>();
	
	public void addPost(addPostRequest request, StreamObserver<addPostResponse> responseObserver) {
		String title = new String(request.getTitle());
		String body = new String(request.getBody());
		System.out.println("Posting post with title \"" + title + "\" and body \"" + body + "\"");
		POSTS.put(title, body);
				
		addPostResponse res = addPostResponse.newBuilder().setResponse("success").build();
		responseObserver.onNext(res);
		responseObserver.onCompleted();
	}
	
	public void getPosts(getPostsRequest request, StreamObserver<getPostsResponse> responseObserver) {
		Builder builder = getPostsResponse.newBuilder();
						
		for(String title: POSTS.keySet()) {
			Post post = Post.newBuilder().setTitle(title).setBody(POSTS.get(title)).build();
			builder.addPosts(post);
		}
		
		getPostsResponse res = builder.build();
		responseObserver.onNext(res);
		responseObserver.onCompleted();
		
	}
}
