package com.jct.rvv.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jct.rvv.constants.Constants;
import com.jct.rvv.controller.service.PlaceholderService;
import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@Api(value = "Placeholder")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
public class PlaceholderController {

	@Autowired
	PlaceholderService phService;

	/**
	 * Method to search a list of Posts
	 * 
	 * @return List<PostDto>
	 */
	@RequestMapping(value = Constants.URL_GET_POSTS, method = RequestMethod.GET)
	public ResponseEntity<List<PostDto>> getPosts() {
		List<PostDto> postsList = phService.getPosts();

		return ResponseEntity.ok(postsList);
	}

	/**
	 * Method to get a list of Comments from a Post by Id
	 * 
	 * @return List<CommentsDto>
	 */
	@RequestMapping(value = Constants.URL_GET_COMMENTS_BY_POSTID, method = RequestMethod.GET)
	public ResponseEntity<List<CommentsDto>> getCommentsByPostId(Integer postId) {
		List<CommentsDto> commentsList = phService.getCommentsByPostId(postId);

		return ResponseEntity.ok(commentsList);
	}

	/**
	 * Method to create new Post
	 * 
	 * @return PostResponseDto
	 */
	@RequestMapping(value = Constants.URL_CREATE_POST, method = RequestMethod.POST)
	public ResponseEntity<PostDto> createPost(
			@ApiParam(value = "Input value object", required = true) @RequestBody @Valid PostCreateDto dto) {
		PostDto createdPost = phService.createPost(dto);

		return ResponseEntity.ok(createdPost);
	}

	/**
	 * Method to delete a Post
	 * 
	 * @return Boolean
	 */
	@RequestMapping(value = Constants.URL_DELETE_POST, method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deletePost(Integer postId) {
		Boolean response = phService.deletePost(postId);

		return ResponseEntity.ok(response);
	}

	/**
	 * Method to save a JSON file with list of Posts
	 * 
	 * @return String
	 */
	@RequestMapping(value = Constants.URL_GET_POSTS_IN_JSON_FILE, method = RequestMethod.GET)
	public ResponseEntity<Resource> getPostsInJSONFile() throws IOException {
		return phService.getPostsInJSONFile();
	}

	/**
	 * Method to save a XML file with list of Posts
	 * 
	 * @return String
	 */
	@RequestMapping(value = Constants.URL_GET_POSTS_IN_XML_FILE, method = RequestMethod.GET)
	public ResponseEntity<Resource> getPostsInXMLFile() throws IOException {
		return phService.getPostsInXMLFile();
	}

}
