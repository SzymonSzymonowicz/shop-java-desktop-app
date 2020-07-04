package Project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;

//generowanie categorri fx
public class GenCatFX {
    //generowanie produktow do kategorii fx
    GridPane genButton(Cart cart, Product pr, double width, double height) throws Exception{
        GridPane gp = new GridPane();
        gp.setMaxSize(250,300);

        FileInputStream input=new FileInputStream(pr.getPath());
        Image i=new Image(input);
        ImageView iw=new ImageView(i);

        iw.setFitHeight(height);
        iw.setFitWidth(width);

        Button bt=new Button("",iw);
        bt.setAlignment(Pos.CENTER);
        bt.setStyle("-fx-cursor: hand;");
        bt.setMaxSize(200,200);
        if(!pr.inStock())
            bt.setStyle("-fx-background-color: red;-fx-cursor: hand;");


        //po kliknieciu w przycisk wyswietli sie okno ze szczegolami produktu
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);

                GridPane gp2 = new GridPane();

                ImageView iw2 = new ImageView(i);
                iw2.setFitHeight(2*height);
                iw2.setFitWidth(2*width);
                VBox img = new VBox(iw2);
                VBox lvb = new VBox();
                gp2.addRow(0,img,lvb);

                Label nml = new Label(pr.getName());
                Label prl = new Label(String.format("Cena: %.2f zł",pr.getPrice()));
                Label stl = new Label(String.format("Ilosc na magazynie: %d",pr.getStock()));
                Label state = new Label();

                String str = new String();
                if(pr.inStock()==true) {
                    str = "Produkt jest dostępny";
                    state.setTextFill(Paint.valueOf("green"));
                }
                else {
                    str = "Produkt jest niedostępny";
                    state.setTextFill(Paint.valueOf("red"));
                }
                state.setText(str);

                lvb.setMinWidth(900-width*2);
                lvb.getChildren().addAll(nml,prl,stl,state);
                lvb.setAlignment(Pos.CENTER);

                gp2.setId("detail");
                gp2.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

                Scene dialogScene = new Scene(gp2,900,height*2);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });

        gp.add(bt,0,0,2,1);

        Label name = new Label(pr.getName());
        name.setMaxWidth(250);
        name.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        name.getStyleClass().add("prLabel");
        name.setAlignment(Pos.CENTER);
        gp.add(name,0,1,2,1);

        Label price = new Label(String.format("%.2f zł",pr.getPrice()));
        price.setMaxWidth(120);
        price.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        price.getStyleClass().add("prLabel");
        gp.add(price,0,2,1,1);

        Button buy = new Button("Dodaj do koszyka");
        buy.setMinWidth(180);
        buy.setId("addCart");
        buy.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pr.inStock())
                    cart.add(pr);
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Brak towaru!");
                    alert.setHeaderText("Brak towaru!");
                    alert.setContentText("Nie można dodać produktu do koszyka, ponieważ jest on tymczasowo niedostępny.\nPrzepraszamy za utrudnienia.");
                    alert.showAndWait();
                }
            }
        });
        gp.add(buy,1,2,1,1);

        gp.setAlignment(Pos.CENTER);
        gp.setHgap(10);
        gp.setVgap(10);

        return gp;
    };

    //wygeneruj kategorie fx
    EventHandler<ActionEvent> genContent(Cart cart, ScrollPane scp, Category cat){
        final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scp.setContent(null);
                TilePane tp = new TilePane();
                tp.setPadding(new Insets(20));
                tp.setVgap(10);
                tp.setHgap(45);
                try {
                    if(cat.getName()=="Obuwie")
                    {
                        for (Product pr : cat.getProds()) {
                            tp.getChildren().add(genButton(cart, pr, 250, 190));
                        }
                    }
                    else {
                        for (Product pr : cat.getProds()) {
                            tp.getChildren().add(genButton(cart, pr, 250, 250));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                scp.setContent(tp);
            }
        };
        return myHandler;
    }
}
