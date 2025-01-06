package application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    @JsonProperty("packageid")
    private String packageid;

    @JsonProperty("id")
    private String id;

    @JsonProperty("cardName")
    public String cardName;

    @JsonProperty("damage")
    public  int damage;

    @JsonProperty("cardType")
    public String cardType;

    public Card(){

    }

    public String getPackageid() {
        return this.packageid;
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

    public String getCardType() {
        return this.cardType;
    }



    public void setPackageid(String packageid) {
        this.packageid = packageid;
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

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }





}
