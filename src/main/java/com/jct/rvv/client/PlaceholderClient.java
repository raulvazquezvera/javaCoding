package com.jct.rvv.client;

import java.util.List;

import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;

public interface PlaceholderClient {

	/**
	 * Client to get a list of Posts
	 * 
	 * @return List<PostResponseDto>
	 */
	List<PostDto> getPosts();

	/**
	 * Client to get a list of Comments from a Post by Id
	 * 
	 * @return List<CommentsDto>
	 */
	List<CommentsDto> getCommentsByPostId(Integer postId);

	/**
	 * Client to create new Post
	 * 
	 * @return PostResponseDto
	 */
	PostDto createPost(PostCreateDto dto);

	/**
	 * Client to delete a Post
	 * 
	 * @return PostResponseDto
	 */
	PostDto deletePost(Integer postId);

}
