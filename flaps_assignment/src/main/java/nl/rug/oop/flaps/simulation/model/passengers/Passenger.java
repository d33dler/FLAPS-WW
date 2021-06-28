package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.FlapsDatabases;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.BlankField;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@FlapsDatabases
public class Passenger extends TravelMember {
    @BlankField(id = "photo")
    protected Image passportPhoto;
    protected PassengerType type;
    @BlankField(id = "weight")
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
        public Passenger readBlanks(List<JTextField> blanks, String id, PassengerType type) {//TODO here JComboBox extracted selection id
            List<Field> fieldList = FileUtils.getAllFieldsFiltered(Passenger.class);
            for (JTextField blank : blanks) {
                String blank_id = blank.getName();
                fieldList.forEach(field -> {
                    BlankField a = field.getAnnotation(BlankField.class);
                    if (a != null && a.id().equals(blank_id)) try {
                        System.out.println("ANN: " + a.id() + " Fname:" + field.getName());
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
                    System.out.println("PG");
                }
            };
        }

        @PassengerSignature(id = "RB001")
        public void buildRowdy() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("RB");
                }
            };
        }

        @PassengerSignature(id = "TER001")
        public void buildTerror() {
            passenger = new Passenger() {
                @Override
                protected void signatureAction() {
                    super.signatureAction();
                    System.out.println("TERROR");
                }
            };
        }

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
