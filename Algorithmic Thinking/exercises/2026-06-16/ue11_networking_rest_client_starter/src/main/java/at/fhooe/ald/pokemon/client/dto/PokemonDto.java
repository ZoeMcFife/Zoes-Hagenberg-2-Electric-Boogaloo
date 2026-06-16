package at.fhooe.ald.pokemon.client.dto;

public class PokemonDto {

    private int pokedexNumber;
    private String name;
    private String type1;
    private String type2;
    private int hp;
    private int attack;
    private int defense;
    private int speed;

    public PokemonDto() {}

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
        String t2 = type2 != null ? "/" + type2 : "";
        return String.format("#%03d %-12s  %s%s  HP:%d ATK:%d DEF:%d SPD:%d",
                pokedexNumber, name, type1, t2, hp, attack, defense, speed);
    }
}
