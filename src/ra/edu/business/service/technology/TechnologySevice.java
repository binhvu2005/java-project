package ra.edu.business.service.technology;

import ra.edu.business.model.technology.Technology;
import ra.edu.business.service.AppSevice;

import java.util.List;

public interface TechnologySevice extends AppSevice {
    int getTechnologyPage(int in_limit);
    void addTechnology(String name);
    void updateTechnology(int id);
    void deleteTechnology(int id);
    List<Technology> getAllTechnology(int page, int in_limit);
    Technology sp_get_technology_by_id(int id);
}
