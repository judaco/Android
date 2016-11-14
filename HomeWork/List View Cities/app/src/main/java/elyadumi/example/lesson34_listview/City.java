package elyadumi.example.lesson34_listview;

/**
 * Created by elyadumi on 13/11/2016.
 */

public class City {

    private String name;
    private boolean selected;
    private int image;

    public City(String name, boolean selected, int image) {
        this.name = name;
        this.selected = selected;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void flipSelected(){
        setSelected(!isSelected());
    }

}
