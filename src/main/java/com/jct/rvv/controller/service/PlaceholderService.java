package com.jct.rvv.controller.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;

public interface PlaceholderService {

	/**
	 * Service to search a list of Posts
	 * 
	 * @return List<PostResponseDto>
	 */
	List<PostDto> getPosts();

	/**
	 * Service to get a list of Comments from a Post by Id
	 * 
	 * @return List<CommentsDto>
	 */
	List<CommentsDto> getCommentsByPostId(Integer postId);

	/**
	 * Service to create new Post
	 * 
	 * @return PostResponseDto
	 */
	PostDto createPost(PostCreateDto dto);

	/**
	 * Service to delete a Post
	 * 
	 * @return Boolean
	 */
	Boolean deletePost(Integer postId);

	/**
	 * Service to save a JSON file with list of Posts
	 * 
	 * @return ResponseEntity<Resource>
	 */
	ResponseEntity<Resource> getPostsInJSONFile();

	/**
	 * Service to save a XML file with list of Posts
	 * 
	 * @return ResponseEntity<Resource>
	 */
	ResponseEntity<Resource> getPostsInXMLFile();

}
