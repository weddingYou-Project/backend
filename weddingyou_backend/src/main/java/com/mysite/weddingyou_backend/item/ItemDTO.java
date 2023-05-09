package com.mysite.weddingyou_backend.item;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.mysite.weddingyou_backend.item.Item.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
	

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
