package coe528.project;

public class Silver extends Level {
    
    private final double fee = 20;

    @Override
    public void levelSilver(Customer c){
        c.setLevel(new Silver());
    }
    
    @Override
    public void levelGold(Customer c){
        c.setLevel(new Gold());
    }
    
    @Override
    public void levelPlatinum(Customer c){
        c.setLevel(new Platinum());
    }
    
    @Override
    public void updateLevel(Customer c){
        
        double balance = c.getBalance();
        
        if(balance < 10000){
            c.setLevel(new Silver());
        }
        else if(balance >= 10000 && balance < 20000){
            c.setLevel(new Gold());
        }
        else if(balance >= 20000){
            c.setLevel(new Platinum());
        }
        else{
            System.out.println("Level Error");
        }
    }
    
    
    @Override
    public double getFee(){
        return fee;
    }
    
    @Override
    public String toString(){
        return "Silver";
    }
    
}
