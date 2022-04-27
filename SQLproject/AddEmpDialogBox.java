package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class AddEmpDialogBox
{

    public static String [] display()
    {
        Stage window = new Stage();
        window.setTitle("Add Employee");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        String [] vals = new String [6];

        final int TF_LEN = 200;
        TextField employeeIdTf = new TextField();
        employeeIdTf.setPromptText("Ex: 1");
        employeeIdTf.setPrefWidth(TF_LEN);
        TextField lastNameTf = new TextField();
        lastNameTf.setPromptText("Ex: Mays");
        lastNameTf.setPrefWidth(TF_LEN);
        TextField firstNameTf = new TextField();
        firstNameTf.setPromptText("Ex: Billy");
        firstNameTf.setPrefWidth(TF_LEN);
        TextField emailTf = new TextField();
        emailTf.setPromptText("Ex: example@example.com");
        emailTf.setPrefWidth(TF_LEN);
        TextField phoneTf = new TextField();
        phoneTf.setPromptText("Ex: 555-555-5555");
        phoneTf.setPrefWidth(TF_LEN);
        TextField positionTf = new TextField();
        positionTf.setPromptText("Ex: Software Analyst");
        positionTf.setPrefWidth(TF_LEN);
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
        gp.add(new Label("Employee ID:"),0,0);
        gp.add(employeeIdTf,1,0);
        gp.add(new Label("Last Name:"),0,1);
        gp.add(lastNameTf,1,1);
        gp.add(new Label("First Name:"),0,2);
        gp.add(firstNameTf,1,2);
        gp.add(new Label("E-mail:"),0,3);
        gp.add(emailTf,1,3);
        gp.add(new Label("Phone:"),0,4);
        gp.add(phoneTf,1,4);
        gp.add(new Label("Position:"),0,5);
        gp.add(positionTf,1,5);
        gp.add(hb,1,6);
        gp.add(Errorlb,1,7);

        OkBtn.setOnAction(e ->
        {

            boolean isInt;
            boolean isEmail;
            String errorText = "";
            //Check if input is int
            vals[0] = employeeIdTf.getText();
            try
            {
                Integer.parseInt(vals[0]);
                isInt = true;
            }
            catch (NumberFormatException ex)
            {
                errorText += "Error: Employee ID must be an integer.\n";
                isInt = false;
            }

            vals[1] = lastNameTf.getText();
            vals[2] = firstNameTf.getText();

            //check if email is Valid
            vals[3] = emailTf.getText();
            if(vals[3].contains("@") && (vals[3].contains(".com")||vals[3].contains(".gov")||vals[3].contains(".org")||vals[3].contains(".edu")))
                isEmail = true;
            else
            {
                errorText += "Error: email not valid\n";
                isEmail = false;
            }



            vals[4] = phoneTf.getText();
            vals[5] = positionTf.getText();

            if(isInt && isEmail)
                window.close();
            else
                Errorlb.setText(errorText);
        });

        CancelBtn.setOnAction(e ->
        {
            vals[0] = null;
            window.close();
        });

        Scene sc = new Scene(gp,300,350);
        window.setScene(sc);
        window.showAndWait();
        return vals;


    }

}
