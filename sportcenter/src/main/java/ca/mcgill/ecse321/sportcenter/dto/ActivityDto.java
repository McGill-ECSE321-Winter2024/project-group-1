package ca.mcgill.ecse321.sportcenter.dto;

import org.checkerframework.checker.units.qual.t;

import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

public class ActivityDto {

    private ClassCategory subcategory;
    private String name;
    private boolean isApproved;
    private String description;

    public ActivityDto() {
    }

    public ActivityDto(ClassCategory aSubcategory, String aName, boolean aIsApproved, String aDescription) {
        this.subcategory = aSubcategory;
        this.name = aName;
        this.isApproved = aIsApproved;
        this.description = aDescription;
    }

    public ClassCategory getSubcategory() {
        return subcategory;
    }

    public String getName() {
        return name;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public String getDescription() {
        return description;
    }

    public void setSubcategory(ClassCategory subcategory) {
        this.subcategory = subcategory;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
