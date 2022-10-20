package com.rpgstats.mappers;

import com.rpgstats.dto.ItemDTO;
import com.rpgstats.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDTO itemToItemDTO(Item item);

    Item itemDTOToItem(ItemDTO itemDTO);

    List<ItemDTO> itemsToItemDTOs(List<Item> categories);

    List<Item> itemDTOsToItems(List<ItemDTO> ItemDTOs);
}

