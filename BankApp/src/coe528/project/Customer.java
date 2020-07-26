package coe528.project;

/**
 * An object representing a bank customer. <p>
 * 
 * Overview: The Customer class represents a MUTABLE bank customer which has a bank 
 * account and a level (based on account balance). <p>
 * 
 * The abstraction function is: <p>
 *  
 *   AF(c) = a Customer c
 * 
 *  <ul>
 *      <li> c.updateLevel = updates the customer's level based on the account
 *                        balance.
 *          <ul>
 *              <li> where c.updateLevel() when the balance is less than 
 *                        $10000 = Silver
 *
 *              <li> where c.updateLevel() when the balance is greater
 *                        than or equal to $10000, but less than $20000
 *                        = Gold
 * 
 *              <li> where c.updateLevel() when the balance is greater 
 *                        than or equal to $20000 = Platinum
 *          </ul> <p>
 *      <li> c.setLevel = sets the customer's level
 *          <ul>
 *              <li> where c.setLevel({Gold Level Object}) = Gold level...
 *          </ul> <p>
 *      <li> c.getLevel = gets the customer's level <p>
 * 
 *      <li> c.getBalance = gets the balance of the customer's bank account. <p>
 * 
 *      <li> c.getBankAccount = gets the customer's bank account. <p>
 * 
 *      <li> c.onlinePurchase = makes an online purchase of $50 minimum
 *          <ul>
 *              <li> where c.onlinePurchase has a fee of $20 for customers
 *                        of rank Silver
 * 
 *              <li> where c.onlinePurchase has a fee of $10 for customers
 *                        of rank Gold 
 * 
 *              <li> where c.onlinePurchase has a fee of $0 for customers
 *                        of rank Platinum
 *          </ul> <p>
 *  </ul> <p>
 * 
 * The rep invariant is: <p>
 * 
 *   RI(c) = a Customer c <p>
 *  <ul>
 *      <li> = true if username != null
 *      <li> = false if otherwise
 *  </ul> <p>
 * 
 * @author Rugshan Gnanandram
 */
public class Customer extends User {
     
    private BankAccount account;
    private Level level;

    /**
     * Constructor for Customer <p>
     * 
     * REQUIRES: username != null, password != null <p>
     * MODIFIES: account, level <p>
     * EFFECTS: Passes strings for username, password, and role to the parent 
     *          class User. Creates a new bank account for the customer and 
     *          initializes the customer's level to silver. <p>
     * 
     * @param username Username of customer's account. 
     * @param password Password of customer's account.
     * @param role Role of customer's account: "customer".
     */
    public Customer(String username, String password, String role) {
        super(username, password, "customer");
        this.account = new BankAccount();
        level = new Silver();
        repOk();
    }

    /**
     * Update the customer's level. <p>
     * 
     * MODIFIES: level <p>
     * EFFECTS: Updates the customer's level based on the customer's bank account 
     *          balance.
     * 
     */
    public void updateLevel(){
        level.updateLevel(this);
    }

    /**
     * Set the customer's level. <p>
     * 
     * MODIFIES: level <p>
     * EFFECTS: Set the customer's level to a specified level.
     * 
     * @param l The customer's new level.
     */
    public void setLevel(Level l){
        level = l;
    }

    /**
     * Get the customer's level. <p>
     * 
     * EFFECTS: Returns the customer's level. 
     * 
     * @return The customer's level.
     */
    public Level getLevel() {
        return level;
    }
    
    /**
     * Get the customer's bank account balance. <p>
     * 
     * EFFECTS: Returns the amount in the customer's bank balance.
     * 
     * @return Customer's balance.
     */
    public double getBalance(){
        return account.getBalance();
    }
    
    /**
     * Get the customer's bank account. <p>
     * 
     * EFFECTS: Returns the customer's bank account.
     * 
     * @return Customer's bank account.
     */
    public BankAccount getBankAccount(){
        return account;
    }
    
    /**
     * Customer can make an online purchase. <p>
     * 
     * REQUIRES: amt greater than or equal to 50 <p>
     * MODIFIES: account <p>
     * EFFECTS: The customer can make an online purchase of $50 or more. The
     *          customer is charged a fee of $20, $10, $0 if their rank is
     *          Silver, Gold, or Platinum, respectively. This withdraws
     *          directly from the customer's bank account balance.
     * 
     * @param amt Cost of online purchase.
     */
    public void onlinePurchase(double amt){
        
        if(amt <= 50){
        
                if(level instanceof Silver){
                    account.withdrawMoney(amt + 20);
                }

                if(level instanceof Gold){
                    account.withdrawMoney(amt + 10);
                }
                
                if(level instanceof Platinum){
                    account.withdrawMoney(amt);
                }   
   
        }
        else{
           throw new IllegalArgumentException("Online purchases must be $50 or more.");
        }
    }
    
    /**
     * repOk of Customer Class. <p>
     * 
     * Returns true if the username is not null. Returns false if username is
     * null.
     * 
     * @return repOk boolean of customer.
     */
    public boolean repOk(){
        return (username != null);
    }
    
    
    /**
     * toString representation of Customer. <p>
     * 
     * Returns a string containing the customer's username, role,
     * account-balance, and level.
     * 
     * @return The customer's toString representation.
     */
    @Override
    public String toString(){
        
        String c = "";
        
        c += ("A customer with the username: " + username + ", "
          + "role: customer, " 
          + "account-balance: " + getBalance() + ", "
          + "and level: " + level.toString());
        
        return c;
    }
     
}
