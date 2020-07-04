package Project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import java.io.FileInputStream;

public class GenHomeFX {
    //generuj strone glowna, j-jerseye a-akcesoria k-buty
    TilePane genHome(ScrollPane scp, Cart cart, Category j, Category a, Category k) throws Exception{
        GenCatFX gcf = new GenCatFX();

        TilePane tp = new TilePane();

        VBox vK = new VBox();
        FileInputStream inpK = new FileInputStream("resources/Images/Kicks/jordan-max-aura-gs.jpg");
        Image iK = new Image(inpK);
        ImageView ivK = new ImageView(iK);
        ivK.setFitHeight(250);
        ivK.setFitWidth(250);
        Button bK=new Button("",ivK);
        bK.setOnAction(gcf.genContent(cart,scp,k));
        bK.setId("HmK");
        Label lK = new Label("Obuwie");
        lK.getStyleClass().add("homeLabel");
        vK.getChildren().addAll(bK,lK);

        VBox vJ = new VBox();
        FileInputStream inpJ = new FileInputStream("resources/Images/Jersey/JersExample.jpg");
        Image iJ = new Image(inpJ);
        ImageView ivJ = new ImageView(iJ);
        ivJ.setFitHeight(250);
        ivJ.setFitWidth(250);
        Button bJ=new Button("", ivJ);
        bJ.setOnAction(gcf.genContent(cart,scp,j));
        bJ.setId("HmJ");
        Label lJ = new Label("Jerseye");
        lJ.getStyleClass().add("homeLabel");
        vJ.getChildren().addAll(bJ,lJ);

        VBox vA = new VBox();
        FileInputStream inpA = new FileInputStream("resources/Images/Acs/pika-spalding-euroleague.jpg");
        Image iA = new Image(inpA);
        ImageView ivA = new ImageView(iA);
        ivA.setFitHeight(250);
        ivA.setFitWidth(250);
        Button bA=new Button("", ivA);
        bA.setOnAction(gcf.genContent(cart,scp,a));
        bA.setId("HmA");
        Label lA = new Label("Akcesoria");
        lA.getStyleClass().add("homeLabel");
        vA.getChildren().addAll(bA,lA);


        tp.getChildren().addAll(vK,vJ,vA);
        tp.setPadding(new Insets(70,110,60,110));
        tp.setHgap(120);
        tp.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());


        return tp;
    }

    EventHandler<ActionEvent> genHomeAction(ScrollPane scp, Cart cart, Category j, Category a, Category k) throws Exception {
        final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    scp.setContent(null);
                    scp.setContent(genHome(scp, cart, j, a, k));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return myHandler;
    }
}