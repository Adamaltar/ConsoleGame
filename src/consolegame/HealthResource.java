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
public class HealthResource extends Resource{
    int health=20;
    
    public HealthResource(){
        super();
    }
    
    public HealthResource(int health){
        super();
        this.health=health;
    }
    @Override
    public void processPersonaj(Personaj p){
        this.setP(p);
        p.setX(x);
        p.setY(y);
        int healthConsumed=p.heal(this.health);
        this.health-=healthConsumed;
        if (this.health<=0)
            consumed=true;
    
    }
    @Override
    public String getSymbol(){
        if (p!=null)
            return p.getSymbol();
        else
            return "\u2774";            
    }
    
    
    
}
