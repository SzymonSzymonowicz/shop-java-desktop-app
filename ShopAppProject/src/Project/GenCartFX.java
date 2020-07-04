package Project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GenCartFX {

    //wygeneruj przedmiot w koszyku
    HBox genCartEl(Cart cart, CartItem ci, Button refreshButton) throws Exception{
        HBox hb = new HBox();

        hb.setMaxSize(1240,125);
        hb.setMinSize(1240,125);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(200);

        FileInputStream input=new FileInputStream(ci.getProduct().getPath());
        Image i=new Image(input);
        ImageView iw=new ImageView(i);

        iw.setFitHeight(100);
        iw.setFitWidth(100);

        Button img=new Button("",iw);
        img.setMaxSize(100,100);
        img.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);

                ImageView iw2 = new ImageView(i);
                iw2.setFitHeight(i.getHeight()/2);
                iw2.setFitWidth(i.getWidth()/2);
                VBox dvb = new VBox(iw2);

                Scene dialogScene = new Scene(dvb,500,500);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });

        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setAlignment(Pos.CENTER);

        TextField tf = new TextField();
        tf.setMaxWidth(60);
        tf.setMinWidth(60);
        tf.setAlignment(Pos.CENTER);
        tf.setText(Integer.toString(ci.getQuantity()));
        tf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(tf.getText().matches("(?!(0))[0-9][0-9]*") && !tf.getText().isEmpty() && Integer.parseInt(tf.getText())<=ci.getProduct().getStock()) {
                    ci.setQuantity(Integer.parseInt(tf.getText()));
                    refreshButton.fire();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wpisałeś złą wartość w pole ilość!");
                    alert.setContentText("Podaj prawidłową ilość!\nMożliwe powody:\n-podana wartość nie jest liczbą\n-podana liczba jest ujemna lub nie jest całkowita\n-podana liczba jest większa, niż liczba sztuk na magazynie");
                    alert.showAndWait();
                }
            }
        });
        tf.getStyleClass().add("cartLabel");

        Label il = new Label("Ilość");
        il.getStyleClass().add("cartLabel");
        vb.getChildren().addAll(il,tf);

        Label price = new Label(String.format("%d x %.2f zł = %.2f zł",ci.getQuantity(),ci.getProduct().getPrice(),(ci.getProduct().getPrice()*ci.getQuantity())));
        price.setMaxWidth(300);
        price.setMinWidth(300);
        price.setAlignment(Pos.CENTER);
        price.getStyleClass().add("cartLabel");

        Button del = new Button();
        del.setId("del");
        del.setMinSize(82,80);
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cart.remove(ci);
                refreshButton.fire();
            }
        });
        del.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());;

        hb.getChildren().addAll(img,vb,price,del);
        hb.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        hb.getStyleClass().add("hbCart");

        return hb;
    }



    //wygeneruj koszyk
    EventHandler<ActionEvent> genCart(ScrollPane scp, Cart cart, Button refreshButton){
        final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //sprawdzanie czy koszyk przypadkiem nie jest pusty
                if(!cart.isEmpty()) {
                    cart.refresh();
                    scp.setContent(null);
                    GridPane gp = new GridPane();
                    gp.setPadding(new Insets(10, 10, 10, 10));
                    gp.setVgap(10);
                    int i = 0;
                    //dodanie przedmiotow z koszyka
                    for (CartItem x : cart.getContent()) {
                        try {
                            gp.add(genCartEl(cart, x, refreshButton), 0, i, 1, 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        i++;
                    }

                    Label sum = new Label("Do zapłaty: " + cart.sum() + " zł");
                    sum.setMinWidth(900);
                    sum.setAlignment(Pos.CENTER_RIGHT);
                    sum.getStyleClass().add("cartLabel");

                    Button pay = new Button("Zapłac i Zamów");
                    pay.getStyleClass().add("nbBut");
                    pay.setAlignment(Pos.CENTER_RIGHT);
                    //Okno dialogowe zaplac
                    pay.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.NONE);
                            dialog.setTitle("Zapraszamy ponownie!");

                            Label thx = new Label("Dziękujemy za zakupy w naszym sklepie!\nZapraszamy ponownie!");
                            thx.setTextAlignment(TextAlignment.CENTER);
                            thx.setId("thx");
                            thx.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

                            FileInputStream input=null;
                            try {
                                input = new FileInputStream("resources/Images/Utility/fed-ex.gif");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Image i = new Image(input);
                            ImageView iw = new ImageView(i);
                            iw.setFitHeight(300);
                            iw.setFitWidth(400);

                            VBox dvb = new VBox(thx,iw);
                            dvb.setAlignment(Pos.CENTER);

                            Scene dialogScene = new Scene(dvb,700,500);
                            dialog.setScene(dialogScene);
                            dialog.setOnHidden(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            cart.update();
                                            cart.removeAll();
                                            refreshButton.fire();
                                        }
                                    });
                                }
                            });
                            dialog.show();
                        }
                    });

                    HBox hs = new HBox(sum, pay);
                    hs.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                    hs.setSpacing(70);

                    gp.add(hs, 0, i + 1, 1, 1);
                    scp.setContent(gp);
                }
                else {
                    //jesli koszyk jest pusty
                    scp.setContent(null);

                    FileInputStream input= null;
                    try {
                        input = new FileInputStream("resources/Images/Utility/cartBG.png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image i=new Image(input);
                    ImageView iw=new ImageView(i);

                    iw.setFitWidth(i.getWidth()*0.8);
                    iw.setFitHeight(i.getWidth()*0.8);

                    Label emp = new Label("Pusty koszyk! Wróć, jak coś dodasz  :)",iw);
                    emp.setMinWidth(1260);
                    emp.setMaxWidth(1260);
                    emp.setWrapText(true);
                    emp.setAlignment(Pos.CENTER);
                    emp.setId("emp");
                    emp.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

                    scp.setContent(emp);
                }
            }
        };

        return myHandler;
    }
}
