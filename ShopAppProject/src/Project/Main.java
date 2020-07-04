package Project;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception{
        Cart cart=new Cart();

        Category Jerseys = new Category("Jerseye");
        Category Kicks = new Category("Obuwie");
        Category Accessories = new Category("Akcesoria");

        LoadProds.readDB(Jerseys);
        LoadProds.readDB(Kicks);
        LoadProds.readDB(Accessories);

        GenCartFX gcrtf = new GenCartFX();
        GenCatFX gcf = new GenCatFX();
        GenHomeFX ghf = new GenHomeFX();

        GridPane okno=new GridPane();

        stage.initStyle(StageStyle.UNDECORATED);

        okno.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX()-event.getScreenX();
                yOffset = stage.getY()-event.getScreenY();
            }
        });

        okno.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX()+xOffset);
                stage.setY(event.getScreenY()+yOffset);
            }
        });

        ScrollPane scp=new ScrollPane();

        Scene scena=new Scene(okno,1280,960);
        stage.setScene(scena);
        stage.setTitle("Hoopz");

        Button zamknij=new Button("X");
        zamknij.setId("Close");
        zamknij.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        zamknij.setOnAction(Zamknij->{stage.close();});

        Button zwin=new Button("_");
        zwin.setId("Min");
        zwin.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        zwin.setOnAction(Zwin-> { stage.setIconified(true); });


        HBox menu=new HBox();
        menu.setMinWidth(1280);
        menu.setPrefHeight(40);
        menu.setStyle("-fx-background-color: black;");
        menu.setAlignment(Pos.CENTER_RIGHT);
        menu.setSpacing(5);
        menu.getChildren().addAll(zwin,zamknij);
        okno.add(menu,0,0,2,1);

        Button Logo=new Button();
        Logo.setId("Logo");
        Logo.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        Logo.setMinSize(400,400);
        Logo.setOnAction(ghf.genHomeAction(scp,cart,Jerseys,Accessories,Kicks));


        HBox LogoBox=new HBox();
        LogoBox.setMinWidth(400);
        LogoBox.setPrefHeight(400);
        LogoBox.setId("LogoBox");
        LogoBox.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        LogoBox.getChildren().add(Logo);
        okno.add(LogoBox,0,1,1,2);

        HBox NameB=new HBox();
        NameB.setMinWidth(880);
        NameB.setPrefHeight(400);
        NameB.setStyle("-fx-background-color: #fcab14");
        okno.add(NameB,1,1,1,1);

        Label nm=new Label("Hoopz");
        nm.setId("Name");
        nm.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        NameB.getChildren().add(nm);
        NameB.setAlignment(Pos.CENTER);


        HBox NavB=new HBox();
        NavB.setMinWidth(1280);
        NavB.setMinHeight(70);
        NavB.setStyle("-fx-background-color: black");
        okno.add(NavB,0,2,2,1);

        Button Home=new Button("Strona główna");
        Home.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Home.getStyleClass().add("nbBut");
        Home.setPrefHeight(60);
        Home.setOnAction(ghf.genHomeAction(scp,cart,Jerseys,Accessories,Kicks));

        Button Kcs=new Button("Obuwie");
        Kcs.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Kcs.getStyleClass().add("nbBut");
        Kcs.setPrefHeight(60);
        Kcs.setOnAction(gcf.genContent(cart,scp,Kicks));

        Button Jrs=new Button("Jerseye");
        Jrs.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Jrs.getStyleClass().add("nbBut");
        Jrs.setPrefHeight(60);
        Jrs.setOnAction(gcf.genContent(cart,scp,Jerseys));

        Button Acs=new Button("Akcesoria");
        Acs.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Acs.getStyleClass().add("nbBut");
        Acs.setPrefHeight(60);
        Acs.setOnAction(gcf.genContent(cart,scp,Accessories));

        Button Crt=new Button();
        Crt.setId("cart");
        Crt.setStyle(null);
        Crt.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Crt.setPrefHeight(60);
        Crt.setMinWidth(60);
        Crt.setOnAction(gcrtf.genCart(scp,cart,Crt));


        NavB.getChildren().addAll(Home,Kcs,Jrs,Acs,Crt);
        NavB.setAlignment(Pos.CENTER);
        NavB.setSpacing(60);
        NavB.setPadding(new Insets(0,5,0,5));

        scp.setMinHeight(500);
        scp.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        scp.setContent(ghf.genHome(scp,cart,Jerseys,Accessories,Kicks));
        scp.setFitToHeight(true);
        scp.setFitToWidth(true);
        okno.add(scp,0,3,2,1);


        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}