package application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    @JsonProperty("id")
    private String id;

    @JsonProperty("cardName")
    public String cardName;

    @JsonProperty("damage")
    public  int damage;

    public Card(){

    }

    public String getId(){
        return this.id;
    }

    public int getDamage(){
        return this.damage;
    }

    public String getCardName() {
        return this.cardName;
    }


    public void setId(String id){
        this.id = id;
    }

    public void setCardName(String s) {
        this.cardName = s;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }



}
