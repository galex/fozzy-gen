package com.fozzy.test;

import com.fozzy.test.helper.PostHelper;
import com.fozzy.test.model.Post;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Post post = new Post(); 
		
		System.out.println("Helper = " + PostHelper.class);
	}
}