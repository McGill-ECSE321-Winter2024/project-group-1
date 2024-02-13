package ca.mcgill.ecse321.sportcenter.model;

public class Owner extends Account
{

  private SportCenter sportCenter;

  public Owner(User aUser, SportCenter aSportCenter)
  {
    super(aUser);
    if (aSportCenter == null || aSportCenter.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aSportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    sportCenter = aSportCenter;
  }

  public Owner(User aUser)
  {
    super(aUser);
    sportCenter = new SportCenter(this);
  }

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

}