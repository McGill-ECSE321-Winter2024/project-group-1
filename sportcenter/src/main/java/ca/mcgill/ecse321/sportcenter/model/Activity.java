
package ca.mcgill.ecse321.sportcenter.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Activity
{

  public enum ClassCategory { Strength, Cardio, Stretch }
 
  private ClassCategory subcategory;

  @Id //name is the PM
  private String name;
  private boolean isApproved;
  private String description;

  //CONSTRUCTORS

  public Activity() {
    
  }

  public Activity(ClassCategory aSubcategory, String aName, boolean aIsApproved, String aDescription)
  {
    subcategory = aSubcategory;
    if (!setName(aName)){
      throw new RuntimeException("Cannot create due to duplicate name.");
    }
    isApproved = aIsApproved;
    description = aDescription;
  
  }

  //SETTERS

  public boolean setSubcategory(ClassCategory aSubcategory)
  {
    boolean wasSet = false;
    subcategory = aSubcategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  //GETTERS

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public ClassCategory getSubcategory()
  {
    return subcategory;
  }

  public String getName()
  {
    return name;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }

  public String getDescription() {

    return description;
  }


}