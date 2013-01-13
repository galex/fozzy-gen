package com.fozzy.test.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fozzy.api.annotation.DateFormat;
import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.test.model.Post;

@WebServiceHelper(name="PostWebServicHelper")
public interface IPostWebServiceHelper {

	@WebServiceMethod(url="http://garage.ubiquoid.com/postId.json.php")
	int getPostId();
	
	@WebServiceMethod(url="http://garage.ubiquoid.com/post.json.php")
    Post getPost();

	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php")
	ArrayList<Post> getPosts();
	
	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php?date=$s")
	HashMap<Integer, HashMap<Object, Post>> getPostsByDate(@DateFormat(format = "d/m/Y") Date date, int delta);
}
