package coe528.project;

public abstract class Level {

    public abstract void levelSilver(Customer c);
    public abstract void levelGold(Customer c);
    public abstract void levelPlatinum(Customer c);
    public abstract void updateLevel(Customer c);

    public String toString(String s){
        return s;
    }
    
    public double getFee(){
        return 0;
    }
    
}
