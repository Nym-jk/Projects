import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application{
	
	//List<NoteData> notes = new ArrayList<>();

    String appData = System.getenv("APPDATA");
    File dir = new File(appData + "/StickyNotesApp");
    File saveFile = new File(dir, "notes.json");
	
    void createNoteWindow(String text, double x, double y) {

    	Stage noteStage = new Stage();

    	// Main text area
        TextArea note = new TextArea(text);
        note.setPrefSize(250, 200);
        note.setWrapText(true);
        
        // Default color
        Color defaultColor = Color.web("#FFF59D");
        note.setBackground(new Background(new BackgroundFill(defaultColor, CornerRadii.EMPTY, Insets.EMPTY)));

        // Buttons
        Button closeBtn = new Button("X");
        closeBtn.setOnAction(e -> noteStage.close());
        
        ColorPicker colorPicker = new ColorPicker(defaultColor);
        colorPicker.setOnAction(e -> {
            Color c = colorPicker.getValue();
            note.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
        });
        
        HBox topBar = new HBox(5, colorPicker, closeBtn);
        topBar.setPadding(new Insets(5));
        
        VBox root = new VBox(topBar, note);
        Scene scene = new Scene(root);
        
        noteStage.setScene(scene);
        noteStage.initStyle(StageStyle.UNDECORATED);
        noteStage.setX(x);
        noteStage.setY(y);
        
     // Dragging logic
        final double[] offsetX = new double[1];
        final double[] offsetY = new double[1];

        topBar.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX();
            offsetY[0] = event.getSceneY();
        });
        
        topBar.setOnMouseDragged(event -> {
            noteStage.setX(event.getScreenX() - offsetX[0]);
            noteStage.setY(event.getScreenY() - offsetY[0]);
        });

        noteStage.show();
       
    }
	@Override
	public void start(Stage stage) { 
		
		Platform.setImplicitExit(false);

        dir.mkdirs();

        Button newNoteBtn = new Button("New Note");

        newNoteBtn.setOnAction(e -> {
            createNoteWindow("",300,300);
        });
        
        BorderPane root = new BorderPane();
        root.setTop(newNoteBtn);

        Scene scene = new Scene(root, 400, 200);

        stage.setTitle("Sticky Notes");
        stage.setScene(scene);
        stage.show();     
		} 
	
	public static void main(String[] args) { 
		launch(args); 
		}
}
