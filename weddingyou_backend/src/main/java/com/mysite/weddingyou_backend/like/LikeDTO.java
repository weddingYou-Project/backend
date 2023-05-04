package com.mysite.weddingyou_backend.like;

import javax.validation.constraints.NotNull;

import com.mysite.weddingyou_backend.item.ItemDTO;
import com.mysite.weddingyou_backend.userRegister.UserRegisterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {

	@NotNull
	private int likeId;
	
	@NotNull
	private UserRegisterDTO user;
	
	@NotNull
	private ItemDTO item;
}
