package at.fhooe.ald.pokemon.client.dto;

public class CaughtPokemonDto {

    private int id;
    private int pokemonNumber;
    private String nickname;

    public CaughtPokemonDto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPokemonNumber() { return pokemonNumber; }
    public void setPokemonNumber(int pokemonNumber) { this.pokemonNumber = pokemonNumber; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    @Override
    public String toString() {
        return String.format("  #%03d  '%s'", pokemonNumber, nickname);
    }
}
