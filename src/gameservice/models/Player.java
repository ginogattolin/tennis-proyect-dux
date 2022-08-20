package gameservice.models;

public class Player {
    private String name;
    private Integer probability;


    public Player() {
    }
    public Player(String name) {
        this.name = name;
    }
    public Player(String name, Integer probability) {
        this(name);
        this.probability = probability;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getProbability() {
        return probability;
    }
    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "Player " + name + ". Probabilities: " + probability + "%";
    }



}
