package Project;

class Product {
    private int id;
    private String name;
    private int stock;
    private float price;
    private String path;

    void subStock(int val){
        if(this.stock>0)
            this.stock-=val;
    }

    boolean inStock(){
        if(this.stock!=0)
            return true;
        else
            return false;
    }

    Product(int id, String name, int stock, float price, String path){
        this.id=id;
        this.name=name;
        this.stock=stock;
        this.price=price;
        this.path=path;
    }

    int getId() { return id; }
    String getName() { return name; }
    int getStock() { return stock; }
    float getPrice() { return price; }
    String getPath() { return path; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPath(String path) { this.path = path; }
    public void setPrice(float price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}
