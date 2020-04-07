/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolegame;


interface BoardPiece{
    public int getX();
    public int getY();    
    public String getSymbol();
    public void setX(int x);
    public void setY(int y); 
    public boolean isVisible();
}


public class Personaj implements BoardPiece {
    
    protected String nume;
    protected int hp=100;
    protected int damage;
    private int x;
    private int y;
    
    public boolean isVisible(){
        return this.isAlive();    
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
      
    public String getSymbol(){
        return this.nume.substring(0,1);
    }
    
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    
    public Personaj(String nume, int damage){
        this.nume=nume;
        this.damage=damage;        
    }
    
    public int heal(int health){
        return 0;
    }
    
    public Personaj(){
        this("Anonim",10);        
    }
    
    public boolean isAlive(){
        return (this.hp>0);
    }
    
    public void afisare(){
        System.out.println(this.getNume()+" has "+this.getHp()+" hp and damage "+this.getDamage());    
    }
    
    public int calculateDamage(){
        return (int)((Math.random()*0.5+0.5)*(this.damage+1));
    }
    
    public void attack(Personaj atacat){
        if (!(this.isAlive()&&atacat.isAlive())){
            System.out.println("Cannot attack, not both heroes are alive!");
            return;
        }        
        int damage=calculateDamage();
        System.out.println(this.getNume()+" is attacking "+atacat.getNume()+" with damage "+damage);
        atacat.hp-=damage;
        if (!atacat.isAlive()){
            System.out.println(atacat.getNume()+" has been killed by "+this.getNume());
            atacat.hp=0;        
        }        
        
    }
    
    public Personaj battle(Personaj adversar){
        
        while (this.isAlive()&&adversar.isAlive()){
            try{
                Thread.sleep(200);
                this.attack(adversar);
                Thread.sleep(200);
                adversar.attack(this);        
            }
            catch (Exception e){
            
            }
        }        
        return this.isAlive()?this:adversar;            
    }
            
    /**
     * @return the nume
     */
    public String getNume() {
        return nume;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }                      
}
