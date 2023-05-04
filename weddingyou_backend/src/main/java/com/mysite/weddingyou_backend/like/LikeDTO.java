package com.mysite.weddingyou_backend.like;

import javax.validation.constraints.NotNull;

import com.mysite.weddingyou_backend.item.ItemDTO;
import com.mysite.weddingyou_backend.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {

	@NotNull
	private int likeId;
	
	@NotNull
	private UserDTO user;
	
	@NotNull
	private ItemDTO item;
}
