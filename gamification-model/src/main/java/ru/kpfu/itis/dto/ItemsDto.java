package ru.kpfu.itis.dto;

import com.wordnik.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by timur on 13.07.15.
 */
@ApiModel("Items")
public class ItemsDto<T> {

    private List<T> items;

    public ItemsDto(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
