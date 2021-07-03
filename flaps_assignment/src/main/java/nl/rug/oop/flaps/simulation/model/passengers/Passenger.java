package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.FlapsDatabases;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.BlankField;
import nl.rug.oop.flaps.simulation.model.loaders.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Passenger class : defines Passenger specific member fields and extends from abstract class
 * TravelMember. A Builder static class is used to produce different Passenger subtypes. Only the signature behaviour
 * varies among different types - thus, we build the Passenger through a specific method which overrides the default
 * signature behaviour.
 */
@Getter
@Setter
@FlapsDatabases
public class Passenger extends TravelMember {
    protected String database_id = Controller.freightIdGen.generateId();
    @BlankField(id = "photo", pattern = "")
    protected Image passportPhoto;
    protected PassengerType type;
    @BlankField(id = "weight", pattern = "[0-9]+", min = 10, max = 200)
    protected String weight;
    public final static int MIN_WEIGHT = 1, MAX_WEIGHT = 200;
    public final static int MIN_AGE = 10, MAX_AGE = 100;

    @PassengerFactory
    @FlapsDatabases
    public static class Builder extends Passenger {

        private final PassengerMediator mediator;
        @Getter
        private Passenger passenger = new Passenger();

        public Builder(PassengerMediator mediator) {
            this.mediator = mediator;
        }

        public Passenger readBlanks(String id, PassengerType type) {//TODO here JComboBox extracted selection id
            List<Field> fieldList = FileUtils.getAllFieldsFiltered(Passenger.class);
            for (JTextField blank : mediator.getBlankSet()) {
                String blank_id = blank.getName();
                fieldList.forEach(field -> {
                    BlankField a = field.getAnnotation(BlankField.class);
                    if (a != null && a.id().equals(blank_id)) try {
                        field.set(this, blank.getText());
                    } catch (IllegalAccessException e) {
                        System.out.println("Failed to set passenger data");
                    }
                });
            }

            idBehaviour(id);
            produce(type);
            return this.passenger;
        }

        /**
         * Using Factory pattern mixed with Method Introspection through Annotations params
         */
        @PassengerSignature(id = "GEN001")
        public void build() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                }
            };
        }

        @PassengerSignature(id = "TH001")
        public void buildThief() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("THIEF");
                }
            };
        }

        @PassengerSignature(id = "KAR001")
        public void buildKaren() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("KAR");
                }
            };
        }

        @PassengerSignature(id = "PG001")
        public void buildPrego() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("PG");//anything goes
                }
            };
        }

        @PassengerSignature(id = "RB001")
        public void buildRowdy() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("RB"); //TODO define specific behaviour, output to PassengerMediator
                }
            };
        }

        @PassengerSignature(id = "TER001")
        public void buildTerror() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("TERROR");//anything goes
                }
            };
        }

        /**
         *
         * @param id - of the Passenger type
         *        Identifies the correct method for the passenger build
         */
        @SneakyThrows
        private void idBehaviour(String id) {
            List<Method> methods = Arrays.asList(this.getClass().getDeclaredMethods());
            Optional<Method> opM = methods.stream().filter(method -> {
                if (method.getAnnotations().length > 0) {
                    PassengerSignature signature = method.getAnnotation(PassengerSignature.class);
                    return signature != null && signature.id().equals(id);
                }
                return false;
            }).findFirst();
            if (opM.isPresent()) {
                opM.get().invoke(this);
            }
        }

        private void produce(PassengerType type) {
            passportPhoto = mediator.getPass_photo();
            passenger.setType(type);
            FileUtils.cloneFields(passenger, this, FileUtils.getAllFieldsFiltered(Passenger.class));
        }
    }

    public Passenger() {
    }

    protected void signatureAction() {
        System.out.println("GENERIC BEHAVIOUR");
    }
}
