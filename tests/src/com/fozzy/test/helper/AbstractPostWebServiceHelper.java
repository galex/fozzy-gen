package com.fozzy.test.helper;

import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.test.model.Post;

@WebServiceHelper(name="PostWebServiceHelper")
public interface AbstractPostWebServiceHelper {

	@WebServiceMethod(url="http://garage.ubiquoid.com/posts.json.php")
	Post getPost();
}
