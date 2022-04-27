package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class DelEmpDialogBox
{
    public static int display()
    {
        Stage window = new Stage();
        window.setTitle("Delete Employee");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        AtomicInteger empID = new AtomicInteger(-1);

        TextField employeeIdTf = new TextField();
        employeeIdTf.setPromptText("Ex: 1");
        employeeIdTf.setPrefWidth(315);
        int btnLen = 70;
        Button OkBtn = new Button("Ok");
        OkBtn.setPrefWidth(btnLen);
        Button CancelBtn = new Button("Cancel");
        CancelBtn.setPrefWidth(btnLen);
        Label Errorlb = new Label("");
        Errorlb.setStyle("-fx-text-fill :red;");

        HBox hb = new HBox(8);
        hb.getChildren().addAll(OkBtn,CancelBtn);

        GridPane gp = new GridPane();
        gp.setHgap(8);
        gp.setVgap(8);
        gp.setAlignment(Pos.CENTER);
        gp.add(new Label("Employee ID:"),0,0);
        gp.add(employeeIdTf,1,0);
        gp.add(hb,1,1);
        gp.add(Errorlb,1,2);

        OkBtn.setOnAction(e ->
        {

            boolean isNumber = false;
            boolean isPositive = false;
            String errorText = "";
            try
            {
                empID.set(Integer.parseInt(employeeIdTf.getText()));
                isNumber = true;

            }
            catch (NumberFormatException ex)
            {
                ex.printStackTrace();
                errorText += "Error: Must me an intger";
            }

            if(empID.get() >= 0)
                isPositive = true;
            else
                errorText += "Error: Number must not be below 0";

            if(isNumber && isPositive)
                window.close();
            else
                Errorlb.setText(errorText);


        });

        CancelBtn.setOnAction(e ->
        {
            window.close();
        });

        Scene sc = new Scene(gp,525,200);
        window.setScene(sc);
        window.showAndWait();
        return empID.get();

    }



}
