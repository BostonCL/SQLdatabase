package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

    //Declare var
    Connection conn;
    Statement statement;
    ResultSet result;
    Label statusLb;

    @Override
    public void start(Stage primaryStage)
    {

        //Set Title
        primaryStage.setTitle("IT Security Support System");

        //Buttons
        final int BTN_WIDTH = 200;
        Button addEmpBtn = new Button("Add Employee");
        addEmpBtn.setPrefWidth(BTN_WIDTH);
        Button updateEmpBtn = new Button("Update Employee");
        updateEmpBtn.setPrefWidth(BTN_WIDTH);
        Button addIncBtn = new Button("Add Incident");
        addIncBtn.setPrefWidth(BTN_WIDTH);
        Button updateIncBtn = new Button("Update Incident");
        updateIncBtn.setPrefWidth(BTN_WIDTH);
        Button incLevelPerMbtn = new Button("Incident level per month");
        incLevelPerMbtn.setPrefWidth(BTN_WIDTH);
        Button clearBtn = new Button("Clear Console");
        clearBtn.setPrefWidth(BTN_WIDTH);
        Button showEmpBtn = new Button("Show Employees");
        showEmpBtn.setPrefWidth(BTN_WIDTH);
        Button delEmpBtn = new Button("Delete Employee");
        delEmpBtn.setPrefWidth(BTN_WIDTH);
        Button showEmpUserNamesBtn = new Button("Show Employee Usernames");
        showEmpUserNamesBtn.setPrefWidth(BTN_WIDTH);
        Button getSysCountBtn = new Button("Get System Counts");
        getSysCountBtn.setPrefWidth(BTN_WIDTH);


        //Query Output Text Area
        TextArea queryOutputTa = new TextArea();
        queryOutputTa.setPrefWidth(500);
        queryOutputTa.setPrefHeight(500);
        queryOutputTa.setEditable(false);

        //Labels
        //Please use UpdateStatus Function to change Status and Color
        //Do NOT change text directly
        statusLb = new Label("Status: No Status");
        statusLb.setPrefHeight(25);

        //Connect to DataBase
        connectToDB("jdbc:mySQL://localhost:3306/it_sss","PEMalavet","sega666");

        //Adds Emp to DB
        addEmpBtn.setOnAction(e ->
        {

            UpdateStatus("Adding Employee","black");

            try
            {
                String [] vals = AddEmpDialogBox.display();
                if(vals[0] != null)
                {
                    UpdateData("INSERT INTO Employee VALUES(" + vals[0] +",'"+ vals[1] + "','" + vals[2] + "','" + vals[3] + "','" + vals[4] +"','"+ vals[5] + "')");
                    UpdateStatus("Employee Added Successfully","green");
                    conn.commit();
                    queryOutputTa.setText(RetrieveData("SELECT * FROM EMPLOYEE ORDER BY EmployeeID",6));
                }
                else
                    UpdateStatus("Canceled","black");

            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to Add Employee","red");
            }

        });


        //Updates Employee in DB
        updateEmpBtn.setOnAction(e ->
        {
            try
            {
                String [] vals = UpdateEmpDialogBox.display();
                if(vals[0] != null)
                {
                    if(!vals[1].equals(""))
                        UpdateData("UPDATE EMPLOYEE SET LastName = '" + vals[1] + "' WHERE EmployeeID = " + vals[0]);

                    if(!vals[2].equals(""))
                        UpdateData("UPDATE EMPLOYEE SET FirstName = '" + vals[2] + "' WHERE EmployeeID = " + vals[0]);

                    if(!vals[3].equals(""))
                        UpdateData("UPDATE EMPLOYEE SET Email = '" + vals[3] + "' WHERE EmployeeID = " + vals[0]);

                    if(!vals[4].equals(""))
                        UpdateData("UPDATE EMPLOYEE SET Phone = '" + vals[4] + "' WHERE EmployeeID = " + vals[0]);

                    if(!vals[5].equals(""))
                        UpdateData("UPDATE EMPLOYEE SET Position = '" + vals[5] + "' WHERE EmployeeID = " + vals[0]);

                    queryOutputTa.setText(RetrieveData("SELECT * FROM EMPLOYEE ORDER BY EmployeeID",6));
                    UpdateStatus("Successfully updated Employee","green");
                    conn.commit();

                }
                else
                    UpdateStatus("Canceled","black");
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to Update Employee","red");
            }
        });

        delEmpBtn.setOnAction(e ->
        {
            int val = DelEmpDialogBox.display();

            try
            {
                UpdateData("DELETE FROM EMPLOYEE WHERE EmployeeId = " + val);
                conn.commit();
                UpdateStatus("Successfully Deleted Employee","green");
                queryOutputTa.setText(RetrieveData("SELECT * FROM EMPLOYEE ORDER BY EmployeeID",6));
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to Deleted Employee","red");
            }
        });

        showEmpUserNamesBtn.setOnAction(e ->
        {
            try
            {
                queryOutputTa.setText(RetrieveData("SELECT e.LastName,e.FirstName,u.Username FROM EMPLOYEE AS e CROSS JOIN USER_TAB AS u WHERE e.EmployeeID = u.EmployeeID",3));
                UpdateStatus("Successfully Retrived Employee Usernames","green");
                conn.commit();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Failed the Retrive Employee Usernames","red");
            }
        });

        showEmpBtn.setOnAction(e ->
        {
            try
            {
                queryOutputTa.setText(RetrieveData("SELECT * FROM EmployeeNoContact",4));
                UpdateStatus("Successfully Retrieved Employee Data","green");
                conn.commit();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed Retrieved Employee Data","red");
            }
        });

        //Add an Inc
        addIncBtn.setOnAction(e ->
        {
            try
            {
                String [] vals = AddIncDialogBox.display();

                if(vals[0] != null)
                {
                    UpdateData("INSERT INTO INCIDENT VALUES(" + vals[0] + ",'" + vals[1] + "','" + vals[2] + "','" + vals[3] + "'," + vals[4] + "," + vals[5] + "," + vals[6] + "," + vals[7] + "," + vals[8] + ")");
                    queryOutputTa.setText(RetrieveData("SELECT * FROM INCIDENT ORDER BY IncidentID",9));
                    UpdateStatus("Successfully Added Incident","green");
                    conn.commit();
                }
                else
                    UpdateStatus("Canceled","black");

            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to add Incident","red");
            }

        });

        //Updates an Incident
        updateIncBtn.setOnAction(e ->
        {
            try
            {
                String [] vals = UpdateIncDialogBox.display();

                if(vals[0] != null)
                {
                    if(!vals[1].equals(""))
                        UpdateData("UPDATE INCIDENT SET DateAdded = '" + vals[1] + "' WHERE IncidentID = " + vals[0]);

                    if(!vals[2].equals(""))
                        UpdateData("UPDATE INCIDENT SET Priority = '" + vals[2] + "' WHERE IncidentID = " + vals[0]);

                    if(!vals[3].equals(""))
                        UpdateData("UPDATE INCIDENT SET StatusAtr = " + vals[3] + " WHERE IncidentID = " + vals[0]);

                    if(!vals[4].equals(""))
                        UpdateData("UPDATE INCIDENT SET EmployeeID = " + vals[4] + " WHERE IncidentID = " + vals[0]);

                    if(!vals[5].equals(""))
                        UpdateData("UPDATE INCIDENT SET EventID = " + vals[5] + " WHERE IncidentID = " + vals[0]);

                    if(!vals[6].equals(""))
                        UpdateData("UPDATE INCIDENT SET SystemID = " + vals[6] + " WHERE IncidentID = " + vals[0]);

                    if(!vals[7].equals(""))
                        UpdateData("UPDATE INCIDENT SET VulnerabilityID = " + vals[7] + " WHERE IncidentID = " + vals[0]);

                    if(!vals[8].equals(""))
                        UpdateData("UPDATE INCIDENT SET ThreatID = " + vals[8] + " WHERE IncidentID = " + vals[0]);

                    queryOutputTa.setText(RetrieveData("SELECT * FROM INCIDENT ORDER BY IncidentID",9));
                    UpdateStatus("Successfully Updated Incident","green");
                    conn.commit();

                }
                else
                    UpdateStatus("Canceled","black");

            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to update Incident","red");
            }
        });

        //Gets Incident Per month
        incLevelPerMbtn.setOnAction(event ->
        {
            try
            {
                queryOutputTa.setText(RetrieveData("CALL Incident_label()",1));
                UpdateStatus("Successfully Retrieved Incident Levels Per Month","green");
                conn.commit();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to Retrieve Incident Levels Per Month","red");
            }
        });

        //Gets Sys Counts
        getSysCountBtn.setOnAction(e ->
        {
            try
            {
                queryOutputTa.setText(RetrieveData("SELECT s.NameAtr,count(*) FROM SYSTEM_TAB AS s,USER_TAB u WHERE u.SystemID = 0 AND s.SystemID = 0 UNION\n" +
                        "SELECT s.NameAtr,count(*) FROM SYSTEM_TAB AS s,USER_TAB u WHERE u.SystemID = 1 AND s.SystemID = 1 UNION\n" +
                        "SELECT s.NameAtr,count(*) FROM SYSTEM_TAB AS s,USER_TAB u WHERE u.SystemID = 2 AND s.SystemID = 2;",2));
                UpdateStatus("Retrieved System Counts Successfully","green");
                conn.commit();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                UpdateStatus("Error Failed to get System Counts","red");
            }
        });


        //Clears Console
        clearBtn.setOnAction(e ->
        {
            queryOutputTa.setText("");
            UpdateStatus("Console Cleared","black");
        });

        BorderPane bp = new BorderPane();

        //GridPane for Button placement
        GridPane btnGp = new GridPane();
        btnGp.setHgap(8);
        btnGp.setVgap(8);
        btnGp.add(new Label("Actions"),0,0);
        btnGp.add(new Label("Manage Employees:"),0,1);
        btnGp.add(addEmpBtn,1,1);
        btnGp.add(updateEmpBtn,2,1);
        btnGp.add(delEmpBtn,3,1);
        btnGp.add(showEmpUserNamesBtn,4,1);
        btnGp.add(showEmpBtn,5,1);
        btnGp.add(new Label("Manage Incidents:"),0,2);
        btnGp.add(addIncBtn,1,2);
        btnGp.add(updateIncBtn,2,2);
        btnGp.add(incLevelPerMbtn,3,2);
        btnGp.add(new Label("Manage Systems"),0,3 );
        btnGp.add(getSysCountBtn,1,3);

        btnGp.add(clearBtn,1,6);

        //VBox For Query Output
        VBox queryOutputVb = new VBox(8);
        queryOutputVb.getChildren().addAll(new Label("Query Output"),queryOutputTa,btnGp);

        bp.setCenter(queryOutputVb);
        bp.setBottom(statusLb);
        primaryStage.setScene(new Scene(bp, 1280, 900));
        primaryStage.show();
    }

    //Connect to DataBase
    private void connectToDB(String url,String uname,String password)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, uname, password);
            conn.setAutoCommit(false);
            UpdateStatus("Successfully Connected to DataBase at " + url,"green");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            UpdateStatus("Error Failed to connect to DataBase at " + url,"red");
            ex.printStackTrace();
        }
    }

    //Runs Query and Updates DB
    private void UpdateData(String query) throws SQLException
    {
        statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    //Runs Query and Returns output
    private String RetrieveData(String query,int numOfCol) throws SQLException
    {

        statement = conn.createStatement();
        result = statement.executeQuery(query);

        String myData = "";
        while (result.next())
        {

            for (int i = 1; i <= numOfCol; i++)
            {
                myData += result.getString(i);
                if(i != numOfCol)
                    myData += " | ";
            }
            myData += "\n";

        }

        if(myData.equals(""))
            myData = "No Results Returned Table(s) are empty";

        return myData;
    }

    //Updates Status Label
    private void UpdateStatus(String text,String color)
    {
        statusLb.setText("Status: " + text);
        statusLb.setStyle("-fx-text-fill :" + color + ";");
    }

    //For IDE's without javaFX
    public static void main(String[] args)
    {
        launch(args);
    }
}
