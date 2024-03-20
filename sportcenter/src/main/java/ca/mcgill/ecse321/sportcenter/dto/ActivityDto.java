package ca.mcgill.ecse321.sportcenter.dto;
import java.util.*;
import java.util.ArrayList;

import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

public class ActivityDto {

    private String name;
    private String description;
    private ClassCategory subcategory;

    public ActivityDto(String name, String description, ClassCategory subcategory) {
        this.name = name;
        this.description = description;
        this.subcategory = subcategory;
        }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ClassCategory getSubcategory() {
        return subcategory;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubcategory(ClassCategory subcategory) {
        this.subcategory = subcategory;
    }


}
