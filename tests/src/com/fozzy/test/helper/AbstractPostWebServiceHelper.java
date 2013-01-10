package com.fozzy.test.helper;

import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.test.model.Post;

@WebServiceHelper(name="PosterHelper2")

public interface AbstractPostWebServiceHelper {

	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php")
	Post getPost();
	
	//@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php")
	//public abstract List<Post> getPosts();	
}
