package brokerscirlce.com.model;

public class DashboardStyle {

    private int imageSource;
    private String name;
    private Boolean selected;

    public DashboardStyle(int imageSource, String name, Boolean selected) {
        this.imageSource = imageSource;
        this.name = name;
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
