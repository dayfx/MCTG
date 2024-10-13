package application;

import java.util.Random;

public class MonsterCard extends Card{

    public int health;

    public String elementType;
    public String monsterType;

    MonsterCard(){

        Random rand = new Random();
        int randomElement = rand.nextInt(3);
        int randomMonster = rand.nextInt(6);
        int randomHealth = rand.nextInt(10);
        int randomDamage = rand.nextInt(10);

        setHealth(randomHealth + 1);
        setDamage(randomDamage + 1);

        switch(randomElement){
            case 0: setElementType("Fire");
                break;
            case 1: setElementType("Water");
                break;
            case 2: setElementType("Grass");
                break;
        }

        switch(randomMonster){
            case 0: setMonsterType("Goblin");
                break;
            case 1: setMonsterType("Wizard");
                break;
            case 2: setMonsterType("Ork");
                break;
            case 3: setMonsterType("Knight");
                break;
            case 4: setMonsterType("Kraken");
                break;
            case 5: setMonsterType("Dragon");
                break;
        }

    }

    public void printCard(){
        System.out.println("Your card: " + this.elementType + "-" + this.monsterType + ": Health: " + this.health + " - Damage: " + this.damage);
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int newHealth){
        this.health = newHealth;
    }

    public String getElementType(){
        return this.elementType;
    }

    public void setElementType(String newElementType){
        this.elementType = newElementType;
    }

    public String getMonsterType(){
        return this.monsterType;
    }

    public void setMonsterType(String newMonsterType){
        this.monsterType = newMonsterType;
    }
}



