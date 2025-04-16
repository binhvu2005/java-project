package ra.edu.business.service.technology;

import ra.edu.business.dao.admin.AdminDao;
import ra.edu.business.dao.admin.AdminDaoImp;
import ra.edu.business.dao.technology.TechnologyDao;
import ra.edu.business.dao.technology.TechnologyDaoImp;
import ra.edu.business.model.technology.Technology;

import java.util.List;

public class TechnologySeviceImp implements TechnologySevice {
    private TechnologyDao dao;
    public TechnologySeviceImp() {
        this.dao = new TechnologyDaoImp();
    }

    @Override
    public int getTechnologyPage(int in_limit) {
       return dao.getTechnologyPage(in_limit);
    }

    @Override
    public void addTechnology(String name) {
        dao.addTechnology(name);
    }
    @Override
    public void updateTechnology(int id) {
        dao.updateTechnology(id);
    }
    @Override
    public void deleteTechnology(int id) {
        dao.deleteTechnology(id);
    }

    @Override
    public List<Technology> getAllTechnology(int page, int in_limit) {
        return dao.getAllTechnology(page, in_limit);
    }

    @Override
    public Technology sp_get_technology_by_id(int id) {
        return dao.sp_get_technology_by_id(id);
    }


    @Override
    public void logout() {

    }
}
