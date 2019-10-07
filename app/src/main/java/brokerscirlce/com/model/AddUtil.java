package brokerscirlce.com.model;

public class AddUtil {

    private int imageSource;
    private String name;

    public AddUtil() {
    }

    public AddUtil(int imageSource, String name) {
        this.imageSource = imageSource;
        this.name = name;
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
