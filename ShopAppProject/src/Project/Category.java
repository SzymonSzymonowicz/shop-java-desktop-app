package Project;

import java.util.ArrayList;
import java.util.List;

class Category {
    private String name;
    private List<Product> Prods = new ArrayList<Product>();

    Category(String name){
        this.name=name;
    }

    void addProduct(Product pr){
        this.Prods.add(pr);
    }

    List<Product> getProds(){
        return this.Prods;
    }

    String getName(){
        return this.name;
    }

    void setName(String name) {
        this.name = name;
    }

    void setProds(List<Product> prods) {
        this.Prods = prods;
    }
}
