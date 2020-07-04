package Project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private List<CartItem> content=new ArrayList<CartItem>();

    void add(Product pr){
        if(this.exists(pr))
        {
            this.getCI(pr).setQuantity(this.getCI(pr).getQuantity()+1);
        }
        else {

            CartItem ci = new CartItem();
            ci.setProduct(pr);
            ci.setQuantity(1);
            this.content.add(ci);
        }
    }

    void remove(CartItem ci){
        if(this.content.contains(ci))
            this.content.remove(ci);
        else
            System.out.println("Error removing CartItem: "+ci);
    }//mozna zamienic na funkcje bool

    float sum(){
        float sum=0;
        for (CartItem ci: this.content) {
            sum += (ci.getQuantity() * ci.getProduct().getPrice());
        }
        return sum;
    }

    void refresh(){
        for(CartItem ci: this.content)
        {
            if(ci.getQuantity()==0)
            {
                this.remove(ci);
            }
        }
    }

    CartItem getCI(Product pr){
        for(CartItem ci: this.content){
            if(pr.getId()==ci.getProduct().getId()){
                return ci;
            }
        }
        return null;
    }

    boolean exists(Product pr){
        for(CartItem ci: this.content){
            if(pr.getId()==ci.getProduct().getId()){
                return true;
            }
        }
        return false;
    }

    boolean isEmpty(){
        if(this.content==null || this.content.isEmpty())
            return true;
        else
            return false;
    }

    void update(){
        for(CartItem ci: this.content)
        {
            ci.pr.subStock(ci.getQuantity());
        }
    }

    void removeAll(){
        Iterator<CartItem> ciI = this.content.iterator();
        while(ciI.hasNext())
        {
            ciI.next();
            ciI.remove();
        }
    }


    List<CartItem> getContent(){
        return this.content;
    }
}

class CartItem{
    Product pr;
    int quantity;

    int getQuantity(){return this.quantity;}
    Product getProduct(){return this.pr;}
    void setQuantity(int quantity){this.quantity=quantity;}
    void setProduct(Product product){this.pr=product;}
}
