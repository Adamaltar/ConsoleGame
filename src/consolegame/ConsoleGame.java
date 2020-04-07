/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolegame;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Utilizator
 */
enum Direction{
    
    
    UP("u"),
    DOWN("d"),
    LEFT("l"),
    RIGHT("r");
        
    private String alias;
        
    private Direction(String alias){
        this.alias=alias;
    }

    public String getAlias(){
        return alias;
    }

}



public class ConsoleGame {
    
    int m,n,nrHeroes;
    BoardPiece[][] bp;
    Personaj[] personaje;
    
    
    public ConsoleGame(int m, int n, int nrPersonaje, int nrHeroes, int noHealthResources){
        Scanner s=new Scanner(System.in);        
        this.m=m;
        this.n=n;
        this.nrHeroes=nrHeroes;
        bp=new BoardPiece[m][n];
        personaje=new Personaj[nrPersonaje+nrHeroes];
        for (int i = 0; i < personaje.length; i++) {
            System.out.println("Introduceti numele eroului "+(i+1)+": ");
            String nume=s.nextLine();
            System.out.println("Introduceti damage-ul eroului "+(i+1)+": ");            
            int damage=s.nextInt();
            if (i<nrPersonaje)
                personaje[i]=new Personaj(nume,damage);         
            else
                personaje[i]=new Erou(nume,damage);         
            int x=-1;
            int y=-1;
            boolean validPosition=false;
            while (!validPosition)                
                try{
                    System.out.println("x: ");
                    x=s.nextInt();
                    System.out.println("y: ");
                    y=s.nextInt();                      
                    if (this.isValidCoordinate(x-1, y-1))
                        validPosition=true;
                    else
                        System.out.println("Introduceti coordonate valide");
                    if (validPosition)
                        if (!this.isCellAvailable(x-1, y-1)){
                            validPosition=false;
                            System.out.println("Celula este ocupata, introduceti alte coordonate.");
                        }
                }
                catch (Exception e){
                    System.out.println("Introduceti valori numerice pentru x si y");
                    s.skip(".+");                   
                }                                          
            s.skip("\n");
            personaje[i].setX(x-1);
            personaje[i].setY(y-1);
            bp[x-1][y-1]=personaje[i];             
        }
        for (int i=0;i<noHealthResources;i++){
            Resource r=new HealthResource();
            int xPos=0;
            int yPos=0;
            do{
                xPos=(int)(Math.random()*m);
                yPos=(int)(Math.random()*n);
            } while (bp[xPos][yPos]!=null);
            
            r.setX(xPos);
            r.setY(yPos);
            bp[xPos][yPos]=r;                                                
        }
    }
    
    public final boolean isValidCoordinate(int x, int y){
        return (x>=0&&x<m&&y>=0&&y<n);    
    }
    
    public final boolean isCellAvailable(int x, int y){
        return bp[x][y]==null;    
    }
    
    
    
    
    public void printBoard(){
      
        for(int j = 0; j < n; j++)
            System.out.print("----");
        System.out.println("-");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (bp[i][j]==null)
                    System.out.print("|   ");
                else
                    System.out.print("| "+(bp[i][j].isVisible()?bp[i][j].getSymbol():" ")+" ");
            }
            System.out.println("|");
            for(int j = 0; j < n; j++)
                System.out.print("----");
            System.out.println("-");
        }
    }
    
    public boolean isGameOver(){
        int ct=0;
        for (int i = 0; i < personaje.length && ct<2; i++)
            if (personaje[i].isAlive())
                ct++;
        
        return ct<2;                                                                    
    }
           
    
    public void gamePlay(){
        Scanner s=new Scanner(System.in);
        int newX=-1;
        int newY=-1;
        while (!isGameOver()){
            for (Personaj curent:personaje){
                if (curent.isAlive()){
                    printBoard();
                    boolean validMove=false;
                    while (!validMove){
                        //getValidMove(curX,curY)
                        //inputDirection method returns Direction object
                        curent.afisare();
                        System.out.println(curent.getNume()+", introduceti directia: ");
                        String d=s.next();
                        Direction chosenDirection=null;

                        for (Direction dir: Direction.values())
                            if (dir.getAlias().equals(d))
                                chosenDirection=dir;
                        if (chosenDirection!=null)
                            validMove=true;
                        else
                            System.out.println("Directii valide sunt u,r,d,l");
                        //validateMove(curX,curY,Direction) 
                        if (validMove){
                            switch (chosenDirection){
                                case UP: newX=curent.getX()-1; newY=curent.getY(); break;
                                case DOWN: newX=curent.getX()+1; newY=curent.getY(); break;
                                case LEFT: newX=curent.getX(); newY=curent.getY()-1; break;
                                case RIGHT: newX=curent.getX(); newY=curent.getY()+1; break;                            
                            }
                            if (!this.isValidCoordinate(newX, newY)){
                                validMove=false;
                                System.out.println("Nu puteti merge in directia aceea.");
                            }
                        }
                    
                    }
                    int oldX=curent.getX();
                    int oldY=curent.getY();
                    if (!(bp[oldX][oldY] instanceof Resource))
                        bp[oldX][oldY]=null;
                    else
                        ((Resource)bp[oldX][oldY]).setP(null);
                                                            
                    
                    
                    
                    
                    BoardPiece next=(bp[newX][newY]==null)?null:bp[newX][newY];
                    
                    //move method
                    if (next==null){
                        curent.setX(newX);
                        curent.setY(newY);
                        bp[newX][newY]=curent;
                                
                    }
                    else if (next instanceof Resource) {
                        Resource res=(Resource)next;
                        
                        if (res.getP()==null){
                            res.processPersonaj(curent);
                        }
                        else{
                            Personaj toAttack=res.getP();
                            curent.battle(toAttack);
                            if (curent.isAlive()&&!toAttack.isAlive()){                                                                                      
                               res.processPersonaj(curent);                        
                            }                                                
                        }

                    }
                    else if (next instanceof Personaj){
                        Personaj toAttack=(Personaj)next;
                        curent.battle(toAttack);
                        if (curent.isAlive()&&!toAttack.isAlive()){                            
                            curent.setX(newX);
                            curent.setY(newY);                            
                            bp[curent.getX()][curent.getY()]=curent;                        
                        }
                    }
                        
                        
                        
                        
                    
                        
                }
       
            }
        
        }
        
        
        
    
    }
    
    
    
    public static void main(String[] args) {
        
        
        ConsoleGame game=new ConsoleGame(10,20,0,2,50);
        game.gamePlay();
        game.printBoard();
                
    }
    
}
