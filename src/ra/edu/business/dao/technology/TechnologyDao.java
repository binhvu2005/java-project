package ra.edu.business.dao.technology;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.technology.Technology;

import java.util.List;

public interface TechnologyDao extends AppDao {
    int getTechnologyPage(int in_limit);
    List<Technology> getAllTechnology(int page, int in_limit);
    Technology sp_get_technology_by_id(int id);
    void addTechnology(String name);
    void updateTechnology(int id);
    void deleteTechnology(int id);

}
