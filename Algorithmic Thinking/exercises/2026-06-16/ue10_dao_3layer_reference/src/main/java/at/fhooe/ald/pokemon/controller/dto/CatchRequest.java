package at.fhooe.ald.pokemon.controller.dto;

public class CatchRequest {

    private int pokemonNumber;
    private String nickname;

    public CatchRequest() {}

    public int getPokemonNumber() { return pokemonNumber; }
    public void setPokemonNumber(int pokemonNumber) { this.pokemonNumber = pokemonNumber; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
