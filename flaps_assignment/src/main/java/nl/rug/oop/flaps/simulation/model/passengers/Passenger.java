package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import java.awt.*;

@Getter
@Setter
public class Passenger extends TravelMember {
    protected Image passportPhoto;
    protected double weight;
    public final static int MIN_WEIGHT = 1, MAX_WEIGHT = 200;
    public final static int MIN_AGE = 10, MAX_AGE = 100;

    public static class Builder extends Passenger {

        public Passenger passId(String id) {
            this.passportId = id;
            return this;
        }
        public Passenger id(String id) {
            this.travellerId = id;
            return this;
        }
        public Passenger name(String sur, String name){
            this.surname = sur;
            this.name = name;
            return this;
        }
        public Passenger nation(String nation){
            this.nationality = nation;
            return this;
        }
        public Passenger age(int age){
            this.age = age;
            return this;
        }
        public Passenger weight(double weight) {
            this.weight = weight;
            return this;
        }
        public Passenger photo(Image photo) {
            this.passportPhoto = photo;
            return this;
        }
        public Passenger build(){
            Passenger passenger = new Passenger();
            FileUtils.cloneFields(passenger, this, FileUtils.getAllFields(Passenger.class));
            return this;
        }
        public Passenger buildThief(){
            Passenger passenger = new Passenger(){
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                }
            };
            FileUtils.cloneFields(passenger, this, FileUtils.getAllFields(Passenger.class));
            return this;
        }
        public Passenger buildKaren(){
            Passenger passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                }
            };
            FileUtils.cloneFields(passenger, this, FileUtils.getAllFields(Passenger.class));
            return this;
        }
    }

    public Passenger() {
    }

    protected void signatureAction(){};
}
