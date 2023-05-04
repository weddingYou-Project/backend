package com.mysite.weddingyou_backend.item;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
	
	@NotNull
	private int itemId;
	
	@NotNull
    private byte[] itemImg;
	
	@NotNull
    private int userId;
    
	@NotNull
    private int likeCount;
    
	@NotNull
    private String itemName;
    
	@NotNull
    private String category;

    
}
