package application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {

    private int packageID;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String cardName;

    @JsonProperty("Damage")
    private double damage;

    public Card(){

    }

    public Card(String id, String name, double damage, int packageId) {
        this.id = id;
        this.cardName = name;
        this.damage = damage;
        this.packageID = packageId;
    }

    public int getPackageid() {
        return this.packageID;
    }

    public String getId(){
        return this.id;
    }

    public double getDamage(){
        return this.damage;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setPackageid(int packageid) {
        this.packageID = packageid;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setCardName(String s) {
        this.cardName = s;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }






}
