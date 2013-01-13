package com.fozzy.test.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fozzy.api.annotation.DateFormat;
import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.api.model.UrlFormatType;
import com.fozzy.test.model.Post;

@WebServiceHelper(name="PostWebServicHelper", helperPackage="com.fozzy.test.helper", parserPackage="com.fozzy.test.parser")
public interface IPostWebServiceHelper {

	@WebServiceMethod(url="http://garage.ubiquoid.com/postId.json.php", urlFormatType=UrlFormatType.JSON)
	int getPostId();
	
	@WebServiceMethod(url="http://garage.ubiquoid.com/post.json.php", urlFormatType=UrlFormatType.JSON)
    Post getPost();

	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php", urlFormatType=UrlFormatType.JSON)
	ArrayList<Post> getPosts();
	
	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php?date=$s", urlFormatType=UrlFormatType.JSON)
	HashMap<Integer, HashMap<Object, Post>> getPostsByDate(@DateFormat(format = "d/m/Y") Date date, int delta);
}
