package ca.mcgill.ecse321.sportcenter.model;

public class Customer extends Account
{
  private SportCenter sportCenter;

  public Customer(User aUser, SportCenter aSportCenter)
  {
    super(aUser);
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create customer due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public SportCenter getSportCenter()
  {
    return sportCenter;
  }

  public boolean setSportCenter(SportCenter aSportCenter)
  {
    boolean wasSet = false;
    if (aSportCenter == null)
    {
      return wasSet;
    }

    SportCenter existingSportCenter = sportCenter;
    sportCenter = aSportCenter;
    if (existingSportCenter != null && !existingSportCenter.equals(aSportCenter))
    {
      existingSportCenter.removeCustomer(this);
    }
    sportCenter.addCustomer(this);
    wasSet = true;
    return wasSet;
  }

}