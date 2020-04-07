/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolegame;





/**
 *
 * @author Utilizator
 */
public class Erou extends Personaj {
    int mana=100;
    
    public Erou(String nume, int damage){
        super(nume,damage);    
    }
    
    public Erou(){
        super();
    }
    @Override
    
    public int heal(int health){
        this.hp+=health;
        if (this.hp>100){
            int rest=this.hp-100;
            this.hp=100;
            return health-rest;
            
        }
        else
            return health;                        
    }
    
    @Override
    public int calculateDamage(){
        int consumedMana=this.mana>=2?2:this.mana;
        this.mana-=consumedMana;
        return super.calculateDamage()+consumedMana;                                
    }   
}
