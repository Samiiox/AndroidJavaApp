package Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

// MachineLearning.java
public class MachineLearning implements Parcelable {
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(mpg);
        dest.writeInt(displacement);
        dest.writeInt(horsePower);
        dest.writeInt(weight);
        dest.writeInt(acceleration);
        dest.writeString(origin);
    }

    protected MachineLearning(Parcel in) {
        id = in.readLong();
        mpg = in.readInt();
        displacement = in.readInt();
        horsePower = in.readInt();
        weight = in.readInt();
        acceleration = in.readInt();
        origin = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }




    public static final Creator<MachineLearning> CREATOR = new Creator<MachineLearning>() {
        @Override
        public MachineLearning createFromParcel(Parcel in) {
            return new MachineLearning(in);
        }

        @Override
        public MachineLearning[] newArray(int size) {
            return new MachineLearning[size];
        }
    };
}