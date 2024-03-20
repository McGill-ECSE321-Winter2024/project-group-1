package ca.mcgill.ecse321.sportcenter.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

/**
 * @author Anslean Albert Jeyaras (GumballGB on GitHub) and Patrick Zakaria
 *         dao and CRUD for Activity
 */
public interface ActivityRepository extends CrudRepository<Activity, String> {

    public Activity findActivityByName(String name);

    public List<Activity> findActivityBySubcategory(ClassCategory subcategory);

    public List<Activity> findActivityByIsApproved(boolean isApproved);
}
