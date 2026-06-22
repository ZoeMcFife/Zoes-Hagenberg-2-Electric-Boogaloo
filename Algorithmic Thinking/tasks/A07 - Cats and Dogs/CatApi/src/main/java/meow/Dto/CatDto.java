package meow.Dto;

import java.util.List;

public class CatDto
{
    private String id;
    private String name;
    private List<String> temperament;
    private String origin;
    private String description;
    private String lifeSpan;
    private WeightDto weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTemperament() {
        return temperament;
    }

    public void setTemperament(List<String> temperament) {
        this.temperament = temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public WeightDto getWeight() {
        return weight;
    }

    public void setWeight(WeightDto weight) {
        this.weight = weight;
    }
}
