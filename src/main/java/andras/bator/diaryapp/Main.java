package andras.bator.diaryapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {

    public static String language="English";

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Locale locale = new Locale("en", "EN");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"), ResourceBundle.getBundle("language.EN", locale));
        stage.setTitle("Diary-App");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
