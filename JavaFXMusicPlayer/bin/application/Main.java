package application;


import java.io.File;
import java.util.List;
import javafx.application.Application;

import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    HBox hBox = new HBox();
    RadioButton radioButton = new RadioButton();
    Text radioText = new Text(" Loop");

    HBox hbox1 = new HBox();
    RadioButton radioButton1 = new RadioButton();

    Text groopName = new Text("© RegEx_Natives");
    Text radioText1 = new Text(" Shuffling");

    LinkedList songs = new LinkedList();
    Button playbtn ;
    boolean loop = true;
    boolean shuffling ;
    String playicon = "▶";

    Node currentPlaying;
   MediaPlayer mediaPlayer;
    Media media;
    Slider slider;
    boolean playing ;
    boolean paused ;




    public void playSongs() {

        if (currentPlaying == null) {
            currentPlaying = songs.head;
        }

        File file = new File(currentPlaying.filepath);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        slider.setOnMouseClicked(e -> {
            mediaPlayer.seek(Duration.seconds(slider.getValue()));
        });

        mediaPlayer.currentTimeProperty()
                .addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                    slider.setMax(media.getDuration().toSeconds());
                    slider.setValue(newValue.toSeconds());
                });

        mediaPlayer.setOnEndOfMedia(() -> {
            if (currentPlaying.next != null) {
                currentPlaying = currentPlaying.next;
                mediaPlayer.stop();
                playSongs();

            } else if (loop) {
                currentPlaying = songs.head;
                mediaPlayer.stop();
                playSongs();

            }
        });
        paused =false ;
		playicon = "II";
        playbtn.setText(playicon);
        playing = true;
        mediaPlayer.play();

    }

    @Override
    public void start(Stage primaryStage) {

        radioButton.setSelected(true);
        radioButton1.setSelected(false);

        radioButton.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) -> {
            if (isNowSelected) {
                radioText.setText(" Loop");
                loop = true;
            } else {
                radioText.setText(" No Loop");
                loop = false;
            }
        });



        try {
            Group root = new Group();
            Scene scene = new Scene(root, 600, 600, Color.YELLOW);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setWidth(850);
            primaryStage.setHeight(380);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Music Player");
            Text text = new Text();
            text.setText(" ");
            text.setX(150);
            text.setY(100);
            groopName.setTranslateX(10);
            groopName.setTranslateY(primaryStage.getHeight() - 30);


            hBox.getChildren().addAll(radioButton, radioText);
            hBox.setTranslateX(750);
            hBox.setTranslateY(270);

            hbox1.getChildren().addAll(radioButton1, radioText1);
            hbox1.setTranslateX(750);
            hbox1.setTranslateY(295);


            playbtn = new Button();
            double r1=40;
            playbtn.setShape(new Circle(r1));
            playbtn.setMinSize(2*r1, 2*r1);
            playbtn.setText(playicon);
            playbtn.setTranslateX(382.5);
            playbtn.setTranslateY(255);

            int r2=30;
            Button prevbtn = new Button();
            prevbtn.setText("⬅");
            prevbtn.setShape(new Circle(r2));

            prevbtn.setMinSize(2*r2, 2*r2);
            prevbtn.setTranslateX(310);
            prevbtn.setTranslateY(257.5+5);

            Button nextbtn = new Button();
            nextbtn.setText("➡");
            nextbtn.setShape(new Circle(r2));
            nextbtn.setMinSize(2*r2, 2*r2);
            nextbtn.setTranslateX(475);
            nextbtn.setTranslateY(257.5+5);

            Button resetbtn = new Button();

            resetbtn.setMinWidth(40);
            resetbtn.setMinHeight(40);

            resetbtn.setText("R");
            resetbtn.setTranslateX(630);
            resetbtn.setTranslateY(272.5);

            Button insertbtn = new Button();

            insertbtn.setText("⏏");
            insertbtn.setTranslateX( 50);
            insertbtn.setTranslateY(257.5+15);
            insertbtn.setMinWidth(45);
            insertbtn.setMinHeight(45);


            Button shufflebtn = new Button();
            shufflebtn.setText("Shuffle");
            shufflebtn.setTranslateX(700);
            shufflebtn.setTranslateY(250+15);

            Pane pane1 = new Pane ();

            pane1.setMinWidth(150);
            pane1.setMinHeight(150);
            pane1.setTranslateX(50);
            pane1.setTranslateY(20);




            slider = new Slider();
            slider.setMinWidth(750);
            slider.setTranslateX(50);
            slider.setTranslateY(221);

            root.getChildren().add(slider);
            root.getChildren().add(hBox);
            root.getChildren().add(hbox1);
            root.getChildren().add(pane1);
            root.getChildren().add(playbtn);
            root.getChildren().add(resetbtn);
            root.getChildren().add(prevbtn);
            root.getChildren().add(nextbtn);
            root.getChildren().add(insertbtn);
            root.getChildren().add(groopName);

            root.getChildren().add(text);

            playbtn.setOnAction(e -> {


                if (songs.head != null && playing == false) {
                    playSongs();

                }
                else if (playing  && !paused)  {
                	playicon = "▶";
                    playbtn.setText(playicon);
                	mediaPlayer.pause();
                	paused = true;

                }else if(paused){
                	playicon = "II";
                    playbtn.setText(playicon);
                	mediaPlayer.play();
                	paused = false;
                }

            });

            insertbtn.setOnAction(e -> {

                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("AUDIO files ", "*.mp3", "*.wav"));

                List<File> list = fileChooser.showOpenMultipleDialog(null);
                list.forEach(i -> songs.addToEnd(i.toString()));

            });

            primaryStage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
