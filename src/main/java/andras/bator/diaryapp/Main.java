package andras.bator.diaryapp;

import andras.bator.diaryapp.dao.EventDAO;
import andras.bator.diaryapp.model.EventEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        EventDAO eventDAO = new EventDAO();


        EventEntity eventEntity = new EventEntity("név", "leírás", "dátum", "Budapest", "valaki" );

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diaryapp");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(eventEntity);
        em.getTransaction().commit();
        em.close();
        launch(args);
    }
}
