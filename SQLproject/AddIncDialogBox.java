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
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AddIncDialogBox
{

    public static String [] display()
    {
        Stage window = new Stage();
        window.setTitle("Add Incident");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        String [] vals = new String [9];

        final int TF_LEN = 250;
        TextField incIDtf = new TextField();
        incIDtf.setPromptText("Ex: 1");
        incIDtf.setPrefWidth(TF_LEN);
        TextField dataAdded = new TextField();
        dataAdded.setPromptText("Ex: YYYY-MM-DD");
        dataAdded.setPrefWidth(TF_LEN);
        TextField priorityTf = new TextField();
        priorityTf.setPromptText("Ex: low, moderate, or high");
        priorityTf.setPrefWidth(TF_LEN);
        TextField statusTf = new TextField();
        statusTf.setPromptText("Ex: Some Status");
        statusTf.setPrefWidth(TF_LEN);
        TextField employeeIdTf = new TextField();
        employeeIdTf.setPromptText("Ex: 1");
        employeeIdTf.setPrefWidth(TF_LEN);
        TextField eventIdTf = new TextField();
        eventIdTf.setPromptText("Ex: 1");
        eventIdTf.setPrefWidth(TF_LEN);
        TextField sysIdTf = new TextField();
        sysIdTf.setPromptText("Ex: 1");
        statusTf.setPrefWidth(TF_LEN);
        TextField vulIdTf = new TextField();
        vulIdTf.setPromptText("Ex: 1");
        vulIdTf.setPrefWidth(TF_LEN);
        TextField threatIdTf = new TextField();
        threatIdTf.setPromptText("Ex: 1");
        threatIdTf.setPrefWidth(TF_LEN);

        final int BTN_LEN = 70;
        Button OkBtn = new Button("Ok");
        OkBtn.setPrefWidth(BTN_LEN);
        Button CancelBtn = new Button("Cancel");
        CancelBtn.setPrefWidth(BTN_LEN);
        Label Errorlb = new Label("");
        Errorlb.setStyle("-fx-text-fill :red;");

        HBox hb = new HBox(8);
        hb.getChildren().addAll(OkBtn,CancelBtn);

        GridPane gp = new GridPane();
        gp.setHgap(8);
        gp.setVgap(8);
        gp.setAlignment(Pos.CENTER);
        gp.add(new Label("Incident ID:"),0,0);
        gp.add(incIDtf,1,0);
        gp.add(new Label("Date Added:"),0,1);
        gp.add(dataAdded,1,1);
        gp.add(new Label("Priority:"),0,2);
        gp.add(priorityTf,1,2);
        gp.add(new Label("Status:"),0,3);
        gp.add(statusTf,1,3);
        gp.add(new Label("Employee ID:"),0,4);
        gp.add(employeeIdTf,1,4);
        gp.add(new Label("Event ID:"),0,5);
        gp.add(eventIdTf,1,5);
        gp.add(new Label("System ID"),0,6);
        gp.add(sysIdTf,1,6);
        gp.add(new Label("Vulnerability ID:"),0,7);
        gp.add(vulIdTf,1,7);
        gp.add(new Label("Threat ID:"),0,8);
        gp.add(threatIdTf,1,8);
        gp.add(hb,1,9);
        gp.add(Errorlb,1,10);

        OkBtn.setOnAction(e ->
        {
            boolean isInt;
            boolean isDate;
            boolean isPri;
            String errorText = "";

            //Check if input is int
            vals[0] = incIDtf.getText();
            vals[4] = employeeIdTf.getText();
            vals[5] = eventIdTf.getText();
            vals[6] = sysIdTf.getText();
            vals[7] = vulIdTf.getText();
            vals[8] = threatIdTf.getText();

            try
            {
                Integer.parseInt(vals[0]);
                Integer.parseInt(vals[4]);
                Integer.parseInt(vals[5]);
                Integer.parseInt(vals[6]);
                Integer.parseInt(vals[7]);
                Integer.parseInt(vals[8]);
                isInt = true;
            }
            catch (NumberFormatException ex)
            {
                ex.printStackTrace();
                errorText += "Error: All ID fields must be an integer.\n";
                isInt = false;
            }

            //check if input is date
            try
            {
                vals[1] = dataAdded.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(vals[1]);
                isDate = true;
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
                errorText += "Error: Date not in correct format\n";
                isDate = false;
            }

            //check priority level
            vals[2] = priorityTf.getText().toLowerCase();
            if(vals[2].equals("low") || vals[2].equals("moderate")|| vals[2].equals("high"))
                isPri = true;
            else
            {
                errorText += "Error: Priority must be low ,moderate, or high\n";
                isPri = false;
            }

            vals[3] = statusTf.getText();

            if(isInt && isDate && isPri)
                window.close();
            else
                Errorlb.setText(errorText);

        });

        CancelBtn.setOnAction(e ->
        {
            vals[0] = null;
            window.close();
        });

        Scene sc = new Scene(gp,400,500);
        window.setScene(sc);
        window.showAndWait();
        return vals;


    }

}
