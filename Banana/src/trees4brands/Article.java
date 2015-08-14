package trees4brands;

public class Article {
    String brand;
    String parentBrand;
    
    public Article(String brand, String parentBrand) {
        this.brand = brand;
        this.parentBrand = parentBrand;
    }
    
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getParentBrand() {
        return parentBrand;
    }
    public void setParentBrand(String parentBrand) {
        this.parentBrand = parentBrand;
    }
}
