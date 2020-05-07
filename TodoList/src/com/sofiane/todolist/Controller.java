package com.sofiane.todolist;

import com.sofiane.todolist.datamodel.TodoData;
import com.sofiane.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;

    private List<TodoItem> todoItems;

    public void initialize() {
//        TodoItem item1 = new TodoItem("Mail a birthday card", "Buy a 3oth birthday for john ", LocalDate.of(2020, Month.MAY, 25));
//        TodoItem item2 = new TodoItem("Doctor's Appointment", " Se doctor Smith bring paperwork", LocalDate.of(2020, Month.JULY, 22));
//        TodoItem item3 = new TodoItem("Finish design proposal for client", "I promised Mike I'd email web site mockups", LocalDate.of(2020, Month.JUNE, 17));
//        TodoItem item4 = new TodoItem("Pick up Doug at the train station", " Doug's arriving on Mach 23 ", LocalDate.of(2020, Month.MARCH, 23));
//        TodoItem item5 = new TodoItem("Pick up dry cleaning", " Clothes should be ready", LocalDate.of(2020, Month.AUGUST, 21));
//        todoItems = new ArrayList<>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);
//        TodoData.getInstance().setTodoItems(todoItems);
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                itemDetailsTextArea.setText(item.getDetails());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                deadlineLabel.setText(df.format(item.getDeadline()));
            }
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }


    @FXML
    public void handleClickListView(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());

        //System.out.println(item.getShortDescription());


//        StringBuilder builder = new StringBuilder(item.getDetails());
//        builder.append("\n\n\n\n");
//        builder.append("Due: ");
//        builder.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(builder.toString());

    }


    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Todo Item");
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try{

            dialog.getDialogPane().setContent(loader.load());
        }catch (IOException e){
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)){
            DialogController controller = loader.getController();
           TodoItem item =  controller.processResult();
           todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
           todoListView.getSelectionModel().select(item);

        }else {
            System.out.println("Cancel pressed");
        }


    }


}