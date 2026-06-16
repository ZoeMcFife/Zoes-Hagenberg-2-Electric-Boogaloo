package at.fhooe.ald.pokemon.model;

public class Pokemon {
    private int pokedexNumber;
    private String name;
    private String type1;
    private String type2;
    private int hp;
    private int attack;
    private int defense;
    private int speed;

    public Pokemon() {}

    public Pokemon(int pokedexNumber, String name, String type1, String type2,
                   int hp, int attack, int defense, int speed) {
        this.pokedexNumber = pokedexNumber;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getPokedexNumber() { return pokedexNumber; }
    public void setPokedexNumber(int pokedexNumber) { this.pokedexNumber = pokedexNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType1() { return type1; }
    public void setType1(String type1) { this.type1 = type1; }
    public String getType2() { return type2; }
    public void setType2(String type2) { this.type2 = type2; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    @Override
    public String toString() {
        String typeStr = (type2 != null && !type2.isEmpty()) ? type1 + "/" + type2 : type1;
        return String.format("#%03d %-14s %-15s HP:%3d ATK:%3d DEF:%3d SPD:%3d",
                pokedexNumber, name, typeStr, hp, attack, defense, speed);
    }
}
