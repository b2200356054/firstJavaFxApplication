import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EditUsersScene {
    public static Scene createScene(Stage theWindow){
        VBox mainVBox = new VBox(10);
        mainVBox.setAlignment(Pos.CENTER);

        TableColumn<Users, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setPrefWidth(100);
        TableColumn<Users, String> clubMemberColumn = new TableColumn<>("Club Member");
        TableColumn<Users, String> adminColumn = new TableColumn<>("Admin");

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        clubMemberColumn.setCellValueFactory(new PropertyValueFactory<>("clubMember"));
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

        TableView<Users> tableView = new TableView<>();
        Data.fillTable(tableView);
        tableView.setPadding(new Insets(20));
        tableView.getColumns().add(usernameColumn);
        tableView.getColumns().add(clubMemberColumn);
        tableView.getColumns().add(adminColumn);


        HBox hBox = new HBox(10); hBox.setPadding(new Insets(20));

        Button backButton = new Button("< BACK");
        backButton.setOnAction(event -> theWindow.setScene(MovieChoosingScene.createScene(theWindow)));

        Button forClubMember = new Button("Promote/Demote Club Member");
        forClubMember.setOnAction(event -> {
            String theUsersName = tableView.getSelectionModel().getSelectedItem().getUsername();
            Users theUser = Data.getUsers().get(theUsersName);
            theUser.setClubMember(!theUser.isClubMember());
            tableView.getItems().clear(); Data.fillTable(tableView);
        });

        Button forAdmin = new Button("Promote/Demote Admin");
        forAdmin.setOnAction(event -> {
            String theUsersName = tableView.getSelectionModel().getSelectedItem().getUsername();
            Users theUser =  Data.getUsers().get(theUsersName);
            theUser.setAdmin(!theUser.isAdmin());
            tableView.getItems().clear(); Data.fillTable(tableView);
        });

        hBox.getChildren().addAll(backButton, forClubMember, forAdmin);
        mainVBox.getChildren().addAll(tableView, hBox);
        return new Scene(mainVBox);
    }
}
