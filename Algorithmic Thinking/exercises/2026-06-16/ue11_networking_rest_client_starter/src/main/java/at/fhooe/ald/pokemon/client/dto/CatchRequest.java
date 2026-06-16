package at.fhooe.ald.pokemon.client.dto;

public class CatchRequest {

    private final int pokemonNumber;
    private final String nickname;

    public CatchRequest(int pokemonNumber, String nickname) {
        this.pokemonNumber = pokemonNumber;
        this.nickname = nickname;
    }

    public int getPokemonNumber() { return pokemonNumber; }
    public String getNickname() { return nickname; }
}
