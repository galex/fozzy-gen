package com.fozzy.test.helper;

import java.util.Date;
import java.util.List;

import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.test.model.Post;

@WebServiceHelper(name="PostWebServicHelper")
public interface AbstractPostWebServiceHelper {

	@WebServiceMethod(url="http://garage.ubiquoid.com/post.json.php")
    Post getPost();
	
	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php")
	List<Post> getPosts();
	
	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php?date=$s")
	List<Post> getPosts(Date date);
}
