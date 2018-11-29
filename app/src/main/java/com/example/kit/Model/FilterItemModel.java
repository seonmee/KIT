package com.example.kit.Model;

public class FilterItemModel {
    private int id;
    private String title;
    private int color;
    private int isSelected;
    private int parent_id;

    public FilterItemModel(int id, String title, int color, int isSelected, int parent_id) {
        this.id = id;
        this.title = title;
        this.color = color;
        this.isSelected = isSelected;
        this.parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int isSelected() {
        return isSelected;
    }

    public void setSelected(int selected) {
        isSelected = selected;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
