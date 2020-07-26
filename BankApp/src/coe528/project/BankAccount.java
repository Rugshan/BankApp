package coe528.project;

public class BankAccount {

    private double balance;

    public BankAccount(){
        this.balance = 100;
    }

    public void depositMoney (double amt){

        if(amt > 0){
            balance += amt;
        }
        else{
            throw new IllegalArgumentException("Deposit amount must be at more than $0.");
        }
       
    }

    public void withdrawMoney(double amt){

        if((balance >= amt) && (amt >= 0)){
            balance -= amt;
        }
        else{
                throw new IllegalArgumentException("Insufficient account balance.");
        }

    }

    public double getBalance(){
        return balance;
    }
    
}
