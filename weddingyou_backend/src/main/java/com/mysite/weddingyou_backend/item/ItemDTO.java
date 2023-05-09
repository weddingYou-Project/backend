package com.mysite.weddingyou_backend.item;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.mysite.weddingyou_backend.item.Item.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
	
	public ItemDTO() {
	}

	public ItemDTO(Item item) {
	}

	@NotNull
	private Long itemId;
	
	@NotNull
    private String itemImg;
    
	@NotNull
    private int likeCount;
    
	@NotNull
    private String itemName;
    
	@NotNull
    private LocalDateTime itemWriteDate;
	
	@NotNull
	private String content;
	
	@NotNull
    private Category category;
	

	public static ItemDTO fromEntity(Item item) {
		ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(item.getItemId());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setItemImg(item.getItemImg());
        itemDTO.setLikeCount(item.getLikeCount());
        itemDTO.setItemWriteDate(item.getItemWriteDate());
        itemDTO.setCategory(item.getCategory());
        return itemDTO;
	}

    
}
