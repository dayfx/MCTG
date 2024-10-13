public class Card {

    public  int damage;

    public String cardName;
    public String cardType;



    public int getDamage(){
        return this.damage;
    }

    public void setDamage(int damage){
       this.damage = damage;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String s) {
        this.cardName = s;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String s) {
        this.cardType = s;
    }
}
