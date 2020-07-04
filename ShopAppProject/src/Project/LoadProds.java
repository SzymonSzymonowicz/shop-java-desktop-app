package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

class LoadProds {
    //wczytanie produktow z pliku do kategorii
    static void readDB(Category cat) throws FileNotFoundException {
        File file;

        if(cat.getName()=="Jerseye"){
            file=new File("./resources/DBs/DBjerseys.txt");
        }
        else if(cat.getName()=="Akcesoria"){
            file=new File("./resources/DBs/DBacs.txt");
        }
        else if(cat.getName()=="Obuwie"){
            file=new File("./resources/DBs/DBkicks.txt");
        }
        else{
            System.out.println("Nie utworzyles spisu dla tej kategorii!!!");
            return;
        }

        Scanner input= new Scanner(file);
        String REGEX = ";";
        Pattern pat = Pattern.compile(REGEX);

        while(input.hasNextLine())
        {
            String[] str=pat.split(input.nextLine());
            Product pr = new Product(Integer.parseInt(str[0]),str[1],Integer.parseInt(str[2]),Float.parseFloat(str[3]),str[4]);
            cat.addProduct(pr);
        }
        input.close();
    }
}