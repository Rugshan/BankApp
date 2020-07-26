package coe528.project;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 *
 * @author Rugshan Gnanandram
 */
public class BankApp extends Application{
    
    private ArrayList<User> users;
    private String tempCustomerUsername;
    
    public BankApp(){
    
        // To stop deleting user files under the credentials folder, comment
        // out the portion below.
        // *** Beginning of Section *** 
        File dir = new File("credentials/");
        for(File file: dir.listFiles()){
            if(!file.isDirectory())
                file.delete();
        }
        // *** End of Section *** 
        
        users = new ArrayList<User>();
        User manager = new Manager();
        users.add(manager);
        
    }

    // Main Window
    Stage window;
    Image bankAppImage = new Image("File:resources/images/BankApp.png");
    
    // Log In Scene
    Scene logInScene;
    TextField usernameField;
    PasswordField passwordField;
    Button exitButton, logInButton, logOutButton, endApplicationButton;
    HBox usernameBox;
    HBox passwordBox;
     
    // Manager Scene
    Scene managerScene;
    Button addCustomerButton, deleteCustomerButton;
    
    // Customer Scene
    Scene customerScene;
    Button withdrawButton, depositButton, onlinePurchaseButton;
    TextField amountField;
    
    // Others
    Label customerSceneLabel;
    
    @Override
    public void start(Stage window) throws Exception{
          
        // Log In Scene Contents
        Label welcomeLabel = new Label("Welcome to BankApp, please log in.");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password: ");
        usernameField = new TextField();
        passwordField = new PasswordField();
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        logInButton = new Button("Log In");
        logInButton.setMinWidth(104);
        exitButton = new Button("Exit");
        exitButton.setMinWidth(104);
        exitButton.setOnAction(exit -> window.close());
        logInButton.setOnAction(e -> {
            
            if(usernameField.getText().equals("admin")){
             
                try{
                    Scanner read = new Scanner(new File("credentials/admin.txt"));

                    if(read.nextLine().equals(passwordField.getText()) && read.nextLine().equals("manager")){
                        
                        welcomeLabel.setText("Welcome to BankApp, please log in.");    
                        usernameField.clear();
                        passwordField.clear();
                        window.setScene(managerScene);
                        window.setTitle("Manager  |  BankApp");

                    }
                    else{
                        welcomeLabel.setText("Error: Invalid username or password. Try again.");
                        passwordField.clear();
                        throw new IllegalArgumentException("Username and password incorrect.");
                    }
                }
                catch (IOException x) {
                    
                    System.out.println("An error occurred.");
                    x.printStackTrace();
                    
                }  
            }
            else{
                if(logIn(usernameField.getText(), passwordField.getText())){
                    tempCustomerUsername = usernameField.getText();
                    welcomeLabel.setText("Welcome to BankApp, please log in.");
                    customerSceneLabel.setText("Account Balance: $" + getBalance(tempCustomerUsername));
                    usernameField.clear();
                    passwordField.clear();
                    window.setScene(customerScene);
                    window.setTitle("Logged In  |  BankApp");
                }
                else{
                    welcomeLabel.setText("Error: Invalid username or password. Try again.");
                    passwordField.clear();
                }
            }      
        });
               
        // Log In Scene Layout
        GridPane logInGridPane = new GridPane();
            VBox logInVBox = new VBox(20);
                usernameBox = new HBox(10);
                passwordBox = new HBox(10);
                HBox logInExitHBox = new HBox(10);
                logInExitHBox.getChildren().addAll(logInButton, exitButton);
                logInExitHBox.setAlignment(Pos.CENTER);
                usernameBox.getChildren().addAll(usernameLabel, usernameField);
                usernameBox.setAlignment(Pos.CENTER);
                passwordBox.getChildren().addAll(passwordLabel, passwordField);
                passwordBox.setAlignment(Pos.CENTER);
            logInVBox.getChildren().addAll(new ImageView(bankAppImage), welcomeLabel, usernameBox, passwordBox, logInExitHBox);
            logInVBox.setAlignment(Pos.CENTER);
        logInGridPane.getChildren().add(logInVBox);
        logInGridPane.setAlignment(Pos.CENTER);
        logInScene = new Scene(logInGridPane, 960, 540);
               
        // Manager Scene Contents
        Label managerSceneLabel = new Label("Hello, admin.");
        addCustomerButton = new Button("Add Customer");
        addCustomerButton.setMinWidth(180);
        deleteCustomerButton = new Button("Delete Customer");
        deleteCustomerButton.setMinWidth(180);
        logOutButton = new Button("Log Out");
        logOutButton.setMinWidth(180);
        logOutButton.setOnAction(logOut -> {
            
            window.setTitle("Log In  |  BankApp");
            managerSceneLabel.setText("Hello, admin.");
            window.setScene(logInScene);
                    
        });
        
        // Manager Scene Layout
        GridPane managerGridPane = new GridPane();
            VBox managerVBox = new VBox(7);
            managerVBox.getChildren().addAll(managerSceneLabel, addCustomerButton, deleteCustomerButton, logOutButton);
            managerVBox.setAlignment(Pos.CENTER);
        managerGridPane.getChildren().add(managerVBox);
        managerGridPane.setAlignment(Pos.CENTER);
        managerScene = new Scene(managerGridPane, 960, 540);
        
        // Add Customer Pop-up
        addCustomerButton.setOnAction(addCustomer -> {
            
            // Pop-up Contents
            Stage addCustomerPopUpStage = new Stage();
            addCustomerPopUpStage.initModality(Modality.APPLICATION_MODAL);
            addCustomerPopUpStage.setTitle("Add Customer  |  BankApp");
            addCustomerPopUpStage.getIcons().add(new Image("File:resources/images/BankApp.png"));
            Label addCustomerLabel = new Label("Fill in new customer's details below.");
            Label newCustomerUsernameLabel = new Label("Username:");
            Label newCustomerPasswordLabel = new Label("Password: ");
            TextField newCustomerUsernameField = new TextField();
            TextField newCustomerPasswordField = new PasswordField();
            newCustomerUsernameField.setPromptText("New Username");
            newCustomerPasswordField.setPromptText("New Password");
            
            // Pop-up Size
            addCustomerPopUpStage.setMinWidth(400);
            addCustomerPopUpStage.setMinHeight(225);

            // Confirm / Cancel Buttons
            Button confirmAddCustomerButton = new Button("Confirm");
            Button addCustomerCancelButton = new Button("Cancel");
            confirmAddCustomerButton.setMinWidth(100);
            addCustomerCancelButton.setMinWidth(100);
            HBox confirmCancelAddCustomerHBox;
            confirmCancelAddCustomerHBox = new HBox(10);
            confirmCancelAddCustomerHBox.getChildren().addAll(confirmAddCustomerButton, addCustomerCancelButton);
            confirmCancelAddCustomerHBox.setAlignment(Pos.CENTER);
            addCustomerCancelButton.setOnAction(e -> addCustomerPopUpStage.close());
            
            // Layout
            GridPane addCustomerGridPane = new GridPane();
                VBox addCustomerVBox = new VBox(20);
                   HBox newCustomerUsernameBox = new HBox(10);
                   HBox newCustomerPasswordBox = new HBox(10);
                   newCustomerUsernameBox.getChildren().addAll(newCustomerUsernameLabel, newCustomerUsernameField);
                   newCustomerPasswordBox.getChildren().addAll(newCustomerPasswordLabel, newCustomerPasswordField);
                addCustomerVBox.getChildren().addAll(addCustomerLabel, newCustomerUsernameBox, newCustomerPasswordBox, confirmCancelAddCustomerHBox);
                addCustomerVBox.setAlignment(Pos.CENTER);
            addCustomerGridPane.getChildren().add(addCustomerVBox);
            addCustomerGridPane.setAlignment(Pos.CENTER);
            
            // Confirm Button
            confirmAddCustomerButton.setOnAction(confirmAddCustomer -> {
                
                if (newCustomerUsernameField.getText() != null && !newCustomerUsernameField.getText().trim().isEmpty() && newCustomerPasswordField.getText() != null && !newCustomerPasswordField.getText().trim().isEmpty()){
                    
                    User newUser = new Customer(newCustomerUsernameField.getText(), newCustomerPasswordField.getText(), "customer");
                    users.add(newUser);
                    newCustomerUsernameField.clear();
                    newCustomerPasswordField.clear();
                    managerSceneLabel.setText("Customer added successfully.");
                    addCustomerPopUpStage.close();
                    
                }
                else{
                    addCustomerLabel.setText("Username and Password fields must not be blank.");
                }   
            });
            
            Scene addCustomerScene = new Scene(addCustomerGridPane);
            addCustomerPopUpStage.setScene(addCustomerScene);
            addCustomerPopUpStage.showAndWait();
        
        });
  
        // Delete Customer Pop-up
        deleteCustomerButton.setOnAction(e ->{
            
            // Pop-up Contents
            Stage deleteCustomerPopUpStage = new Stage();
            deleteCustomerPopUpStage.initModality(Modality.APPLICATION_MODAL);
            deleteCustomerPopUpStage.setTitle("Delete Customer  |  BankApp");
            deleteCustomerPopUpStage.getIcons().add(new Image("File:resources/images/BankApp.png"));
            Label deleteCustomerLabel = new Label("Enter the username of a customer to delete.");
            Label deleteCustomerUsernameLabel = new Label("Username :");
            TextField deleteCustomerTextField = new TextField();
            deleteCustomerTextField.setPromptText("Username");

            // Pop-up Size
            deleteCustomerPopUpStage.setMinWidth(400);
            deleteCustomerPopUpStage.setMinHeight(225);
            
            // Confirm / Cancel Buttons
            Button confirmDeleteCustomerButton = new Button("Confirm");
            Button deleteCustomerCancelButton = new Button("Cancel");
            confirmDeleteCustomerButton.setMinWidth(100);
            deleteCustomerCancelButton.setMinWidth(100);
            HBox confirmCancelDeleteCustomerHBox;
            confirmCancelDeleteCustomerHBox = new HBox(10);
            confirmCancelDeleteCustomerHBox.getChildren().addAll(confirmDeleteCustomerButton, deleteCustomerCancelButton);
            confirmCancelDeleteCustomerHBox.setAlignment(Pos.CENTER);
            deleteCustomerCancelButton.setOnAction(deleteCustomer -> deleteCustomerPopUpStage.close());

            // Layout
            GridPane deleteCustomerGridPane = new GridPane();
                VBox deleteCustomerVBox = new VBox (20);
                    HBox deleteCustomerHBox = new HBox(10);
                    deleteCustomerHBox.getChildren().addAll(deleteCustomerLabel, deleteCustomerTextField);
                    deleteCustomerHBox.setAlignment(Pos.CENTER);
                deleteCustomerVBox.getChildren().addAll(deleteCustomerLabel, deleteCustomerHBox, confirmCancelDeleteCustomerHBox);
                deleteCustomerVBox.setAlignment(Pos.CENTER);
            deleteCustomerGridPane.getChildren().add(deleteCustomerVBox);
            deleteCustomerGridPane.setAlignment(Pos.CENTER);
                
            // Confirm Delete Button
            confirmDeleteCustomerButton.setOnAction(confirmDeleteCustomer -> {
                
                try{

                    String deleteUsername = deleteCustomerTextField.getText();
                    
                    // Delete Customer From ArrayList
                    int i = users.indexOf(getCustomer(deleteUsername));
                    users.remove(i);
                    
                    // Delete From File
                    File deleteFile = new File("credentials/" + deleteUsername + ".txt");
                    deleteFile.delete();

                    // Update Text
                    managerSceneLabel.setText("Customer deleted successfully.");

                    deleteCustomerPopUpStage.close();
                }
                catch (Exception deleteCustomer){
                    deleteCustomerLabel.setText("ERROR: Customer does not exist.");
                }
            });

            Scene deleteCustomerScene = new Scene(deleteCustomerGridPane);
            deleteCustomerPopUpStage.setScene(deleteCustomerScene);
            deleteCustomerPopUpStage.showAndWait();
 
        });   
            
        // Customer Scene Contents
        customerSceneLabel = new Label(""); //This gets updated by Log In Button
        depositButton = new Button("Deposit");
        depositButton.setMinWidth(180);
        onlinePurchaseButton = new Button ("Online Purchase");
        onlinePurchaseButton.setMinWidth(180);
        withdrawButton = new Button("Withdraw");
        withdrawButton.setMinWidth(180);
        logOutButton = new Button("Log Out");
        logOutButton.setMinWidth(180);
        logOutButton.setOnAction(logOut -> {
            
            window.setTitle("Log In  |  BankApp");
            window.setScene(logInScene);
                    
        });
        
        // Customer Scene Layout
        GridPane customerGridPane = new GridPane();
            VBox customerVBox = new VBox(7);
            customerVBox.getChildren().addAll(customerSceneLabel, depositButton, withdrawButton, onlinePurchaseButton, logOutButton);
            customerVBox.setAlignment(Pos.CENTER);
        customerGridPane.getChildren().add(customerVBox);
        customerGridPane.setAlignment(Pos.CENTER);
        customerScene = new Scene(customerGridPane, 960, 540);
        
        // Customer Deposit Pop-up    
        depositButton.setOnAction(deposit -> {
            
            // Pop-up Contents
            Stage depositPopUpStage = new Stage();
            depositPopUpStage.initModality(Modality.APPLICATION_MODAL);
            depositPopUpStage.setTitle("Deposit  |  BankApp");
            depositPopUpStage.getIcons().add(new Image("File:resources/images/BankApp.png"));
            Label depositLabel = new Label("Enter an amount to deposit.");
            Label depositAmountLabel = new Label("Deposit: $");
            TextField depositAmountTextField = new TextField();
            depositAmountTextField.setPromptText("100.00");
            
            // Pop-up Size
            depositPopUpStage.setMinWidth(400);
            depositPopUpStage.setMinHeight(225);
            
            // Confirm / Cancel Buttons
            Button confirmDepositButton = new Button("Confirm");
            Button depositCancelButton = new Button("Cancel");
            confirmDepositButton.setMinWidth(100);
            depositCancelButton.setMinWidth(100);
            HBox confirmCancelDepositHBox;
            confirmCancelDepositHBox = new HBox(10);
            confirmCancelDepositHBox.getChildren().addAll(confirmDepositButton, depositCancelButton);
            confirmCancelDepositHBox.setAlignment(Pos.CENTER);
            depositCancelButton.setOnAction(e -> depositPopUpStage.close());

            // Layout
            GridPane depositGridPane = new GridPane();
                VBox depositVBox = new VBox (20);
                    HBox depositHBox = new HBox(10);
                    depositHBox.getChildren().addAll(depositAmountLabel, depositAmountTextField);
                    depositHBox.setAlignment(Pos.CENTER);
                depositVBox.getChildren().addAll(depositLabel, depositHBox, confirmCancelDepositHBox);
                depositVBox.setAlignment(Pos.CENTER);
            depositGridPane.getChildren().add(depositVBox);
            depositGridPane.setAlignment(Pos.CENTER);
                
            // Confirm Deposit Button
            confirmDepositButton.setOnAction(confirmDeposit -> {
                
                try{
                    double amt = Double.parseDouble(depositAmountTextField.getText());
                    ((Customer) getCustomer(tempCustomerUsername)).getBankAccount().depositMoney(amt);
                    customerSceneLabel.setText("Deposit Successful, New Balance: $" + getBalance(tempCustomerUsername));
                    ((Customer) getCustomer(tempCustomerUsername)).updateLevel();
                    depositPopUpStage.close();
                }
                catch (Exception e){
                    depositLabel.setText("INVALID INPUT: Type in an amount greater than $0.");
                }
            });
            
            Scene depositScene = new Scene(depositGridPane);
            depositPopUpStage.setScene(depositScene);
            depositPopUpStage.showAndWait();
        
        });
        
        // Customer Withdraw Pop-up    
        withdrawButton.setOnAction(withdraw -> {
            
            // Pop-up Contents
            Stage withdrawPopUpStage = new Stage();
            withdrawPopUpStage.initModality(Modality.APPLICATION_MODAL);
            withdrawPopUpStage.setTitle("Withdraw  |  BankApp");
            withdrawPopUpStage.getIcons().add(new Image("File:resources/images/BankApp.png"));
            Label withdrawLabel = new Label("Enter an amount to withdraw.");
            Label withdrawAmountLabel = new Label("Withdraw: $");
            TextField withdrawAmountTextField = new TextField();
            withdrawAmountTextField.setPromptText("100.00");
            
            // Pop-up Size
            withdrawPopUpStage.setMinWidth(400);
            withdrawPopUpStage.setMinHeight(225);
            
            // Confirm / Cancel Buttons
            Button confirmWithdrawButton = new Button("Confirm");
            Button withdrawCancelButton = new Button("Cancel");
            confirmWithdrawButton.setMinWidth(100);
            withdrawCancelButton.setMinWidth(100);
            HBox confirmCancelWithdrawHBox;
            confirmCancelWithdrawHBox = new HBox(10);
            confirmCancelWithdrawHBox.getChildren().addAll(confirmWithdrawButton, withdrawCancelButton);
            confirmCancelWithdrawHBox.setAlignment(Pos.CENTER);
            withdrawCancelButton.setOnAction(e -> withdrawPopUpStage.close());

            // Layout
            GridPane withdrawGridPane = new GridPane();
                VBox withdrawVBox = new VBox (20);
                    HBox withdrawHBox = new HBox(10);
                    withdrawHBox.getChildren().addAll(withdrawAmountLabel, withdrawAmountTextField);
                    withdrawHBox.setAlignment(Pos.CENTER);
                withdrawVBox.getChildren().addAll(withdrawLabel, withdrawHBox, confirmCancelWithdrawHBox);
                withdrawVBox.setAlignment(Pos.CENTER);
            withdrawGridPane.getChildren().add(withdrawVBox);
            withdrawGridPane.setAlignment(Pos.CENTER);
                
            // Confirm Deposit Button
            confirmWithdrawButton.setOnAction(confirmWithdraw -> {
                
                try{
                    double amt = Double.parseDouble(withdrawAmountTextField.getText());
                    ((Customer) getCustomer(tempCustomerUsername)).getBankAccount().withdrawMoney(amt);
                    customerSceneLabel.setText("Withdraw Successful, New Balance: $" + getBalance(tempCustomerUsername));
                    ((Customer) getCustomer(tempCustomerUsername)).updateLevel();
                    withdrawPopUpStage.close();
                }
                catch (Exception e){
                    withdrawLabel.setText("Invalid Input / Insufficent Funds: Try again.");
                }
            });
            
            Scene withdrawScene = new Scene(withdrawGridPane);
            withdrawPopUpStage.setScene(withdrawScene);
            withdrawPopUpStage.showAndWait();
        
        });
        
        // Customer Online Purchase Pop-up   
        onlinePurchaseButton.setOnAction(onlinePurchase -> {
            
            // Pop-up Contents
            Stage onlinePurchasePopUpStage = new Stage();
            onlinePurchasePopUpStage.initModality(Modality.APPLICATION_MODAL);
            onlinePurchasePopUpStage.setTitle("Online Purchase  |  BankApp");
            onlinePurchasePopUpStage.getIcons().add(new Image("File:resources/images/BankApp.png"));
            Label onlinePurchaseLabel = new Label("Enter the amount of the online purchase.");
            
            
            Label onlinePurchaseLevelLabel = new Label("Account Rank: " 
                                                       + ((getCustomer(tempCustomerUsername) instanceof Customer) ? (((Customer) getCustomer(tempCustomerUsername)).getLevel().toString()) : "ERROR @ 1")
                                                       + "   |   " 
                                                       + "Fee: $" 
                                                       + ((getCustomer(tempCustomerUsername) instanceof Customer) ? (((Customer) getCustomer(tempCustomerUsername)).getLevel().getFee()) : "ERROR @ 2"));
            
            Label onlinePurchaseAmountLabel = new Label("Amount: $");
            TextField onlinePurchaseAmountTextField = new TextField();
            onlinePurchaseAmountTextField.setPromptText("100.00");
            
            // Pop-up Size
            onlinePurchasePopUpStage.setMinWidth(400);
            onlinePurchasePopUpStage.setMinHeight(225);
            
            // Confirm / Cancel Buttons
            Button confirmOnlinePurchaseButton = new Button("Confirm");
            Button onlinePurchaseCancelButton = new Button("Cancel");
            confirmOnlinePurchaseButton.setMinWidth(100);
            onlinePurchaseCancelButton.setMinWidth(100);
            HBox confirmCancelOnlinePurchaseHBox;
            confirmCancelOnlinePurchaseHBox = new HBox(10);
            confirmCancelOnlinePurchaseHBox.getChildren().addAll(confirmOnlinePurchaseButton, onlinePurchaseCancelButton);
            confirmCancelOnlinePurchaseHBox.setAlignment(Pos.CENTER);
            onlinePurchaseCancelButton.setOnAction(e -> onlinePurchasePopUpStage.close());

            // Layout
            GridPane onlinePurchaseGridPane = new GridPane();
                VBox onlinePurchaseTextVBox = new VBox(2);
                onlinePurchaseTextVBox.getChildren().addAll(onlinePurchaseLevelLabel, onlinePurchaseLabel);
                onlinePurchaseTextVBox.setAlignment(Pos.CENTER);
                VBox onlinePurchaseVBox = new VBox (20);
                    HBox onlinePurchaseHBox = new HBox(10);
                    onlinePurchaseHBox.getChildren().addAll(onlinePurchaseAmountLabel, onlinePurchaseAmountTextField);
                    onlinePurchaseHBox.setAlignment(Pos.CENTER);
                onlinePurchaseVBox.getChildren().addAll(onlinePurchaseHBox, confirmCancelOnlinePurchaseHBox);
                VBox onlinePurchaseCombinedVBox = new VBox(20);
                onlinePurchaseCombinedVBox.getChildren().addAll(onlinePurchaseTextVBox, onlinePurchaseVBox);
                onlinePurchaseCombinedVBox.setAlignment(Pos.CENTER);

            onlinePurchaseGridPane.getChildren().add(onlinePurchaseCombinedVBox);
            onlinePurchaseGridPane.setAlignment(Pos.CENTER);
                
            // Confirm Deposit Button
            confirmOnlinePurchaseButton.setOnAction(confirmOnlinePurchase -> {
                              
                try{           
                    double amt = Double.parseDouble(onlinePurchaseAmountTextField.getText());
                    double fee = (((Customer) getCustomer(tempCustomerUsername)).getLevel().getFee());
                    double total = amt + fee;
                    
                    if(amt < 50){
                        onlinePurchaseLabel.setText("Invalid Input: Online purchases must be at least $50.");
                    }
                    else{
                        ((Customer) getCustomer(tempCustomerUsername)).getBankAccount().withdrawMoney(total);
                        customerSceneLabel.setText("Online Purchase Successful, New Balance: $" + getBalance(tempCustomerUsername));
                        ((Customer) getCustomer(tempCustomerUsername)).updateLevel();
                        onlinePurchasePopUpStage.close();
                    }
                    
                }
                catch (Exception e){
                    onlinePurchaseLabel.setText("Invalid Input / Insufficent Funds: Try again.");
                }
            });
            
            Scene onlinePurchaseScene = new Scene(onlinePurchaseGridPane);
            onlinePurchasePopUpStage.setScene(onlinePurchaseScene);
            onlinePurchasePopUpStage.showAndWait();
        
        });
        
        // Default Scene
        window.setScene(logInScene);
        window.getIcons().add(new Image("File:resources/images/BankApp.png"));
        window.setTitle("Log In  |  BankApp");
        window.show();
        
    }  
    
    public boolean logIn(String username, String password){
        
        String filename = username;
        boolean valid = false;
        
        try {
        
            Scanner read = new Scanner(new File("credentials/" + filename + ".txt"));
            
            if(read.nextLine().equals(password) && read.nextLine().equals("customer")){
                
                valid = true;
                read.close();
                return valid;
                
            }       
        }
        catch (IOException e) {
            System.out.println("Username not found.");  
        }
        
        return valid;   
    }
    
    public User getCustomer(String username){
        
        for(User u : users){
            
            if((u.getUsername().equals(username))){
                return u;
            }
        }
        
        return null;
    }
    
    public String getBalance(String username){
        
        User temp = getCustomer(username);
        
        if(temp instanceof Customer){
            return String.format("%.2f", ((Customer) temp).getBalance());
        }
        
        return ("Error: Account does not exist.");
    }

    public static void main(String[] args) {
           
        launch(args);
   
    }
    
}
