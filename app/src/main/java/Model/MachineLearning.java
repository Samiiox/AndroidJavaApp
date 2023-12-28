package Model;

// MachineLearning.java
public class MachineLearning {
    private long id; // primary key
    private int mpg;
    private int displacement;
    private int horsePower;
    private int weight;
    private int acceleration;
    private String origin;

    // Constructors, getters, and setters

    public MachineLearning() {
    }

    public MachineLearning(int mpg, int displacement, int horsePower, int weight, int acceleration, String origin) {
        this.mpg = mpg;
        this.displacement = displacement;
        this.horsePower = horsePower;
        this.weight = weight;
        this.acceleration = acceleration;
        this.origin = origin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMpg() {
        return mpg;
    }

    public void setMpg(int mpg) {
        this.mpg = mpg;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}

