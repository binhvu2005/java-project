package ra.edu.business.model.technology;

public class Technology {
    private int id;
    private String technologyName;

    public Technology(int id, String technologyName) {
        this.id = id;
        this.technologyName = technologyName;
    }
    public Technology() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return technologyName;
    }
    public void setName(String technologyName) {
        this.technologyName = technologyName;
    }
    @Override
    public String toString() {
        return "Technology{" +
                "id=" + id +
                ", technologyName='" + technologyName + '\'' +
                '}';
    }
}
