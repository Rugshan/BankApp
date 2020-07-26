# BankApp
Simple GUI based bank account application.
![BankApp Welcome Scene](https://github.com/Rugshan/BankApp/blob/master/Images/Welcome.jpg)

BankApp was created as a final culminating project for my object oriented analysis and design course.

The main requirements were to implement a design pattern and a graphical user interface. The most intimidating task was creating the GUI. We were asked to learn how to create one on our own.

UML diagrams were created but will not be included in this repository.

How BankApp works:
1. Compile and Run BankApp (was created in NetBeans)
2. There are two types of users
  - Log into the "manager" user with the credentials -> Username: admin , Password: admin
    - A manager can create a "customer" by providing a new username and a password
    - A manager can also delete a "customer" by submitting the desired username to be deleted
    - A manager can also log out of their account
    ![Manager Scene](https://github.com/Rugshan/BankApp/blob/master/Images/Manager.jpg)
    ![Add Customer](https://github.com/Rugshan/BankApp/blob/master/Images/ManagerAdd.jpg)
    ![Delete Customer](https://github.com/Rugshan/BankApp/blob/master/Images/ManagerDelete.jpg)
    
 - A "customer" can log in with their newly created credentials
    - They can deposit a specified amount into their account
    - They can withdraw a specified amount from their account
    - They can make online purchases with their account
      • A customer's account is ranked based on their account balance
      • Depending on their account rank, they will have a certain fee charged for an online purchase
    - They can also log out of their account
    ![Customer Scene](https://github.com/Rugshan/BankApp/blob/master/Images/Customer.jpg)
    ![Deposit](https://github.com/Rugshan/BankApp/blob/master/Images/CustomerDeposit.jpg)
    ![Withdraw](https://github.com/Rugshan/BankApp/blob/master/Images/CustomerWithdraw.jpg)
    ![Online Purchase](https://github.com/Rugshan/BankApp/blob/master/Images/CustomerOnlinePurchase.jpg)
    
3. BankApp was designed to delete all its user data, other than the manager's login details, after each restart.
  - Login credentials were asked to be stored as unique text documents for each user. They can be found in BankApp/credentials/
  
Note: I do not consent to work being taken from this repository to aid in other people's assignments or etc.
Disclaimer: I do not own the rights to the artwork used in this application.
