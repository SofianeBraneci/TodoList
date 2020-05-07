package com.sofiane.todolist;

import com.sofiane.todolist.datamodel.TodoData;
import com.sofiane.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField shortDescription;
    @FXML
    private TextArea details;
    @FXML
    private DatePicker deadlinePicker;

    public TodoItem processResult(){
        String shortDesc = shortDescription.getText();
        String moreAbout = details.getText();
        LocalDate date = deadlinePicker.getValue();
        TodoItem item = new TodoItem(shortDesc, moreAbout, date);
        TodoData.getInstance().addTodoItem(item);
        return item;
    }
}
