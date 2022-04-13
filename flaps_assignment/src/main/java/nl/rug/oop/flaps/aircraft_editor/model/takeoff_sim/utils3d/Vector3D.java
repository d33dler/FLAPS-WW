package nl.rug.oop.flaps.aircraft_editor.model.takeoff_sim.utils3d;
import lombok.Getter;


public abstract class Vector3D {

    @Getter
    public static class Double extends Vector3D {
        public double x1, x2, x3;

        public Double() {
        }

        public Double(double x1, double x2, double x3) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
        }

        public void setVec(double x1, double x2, double x3) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
        }
    }

    public abstract void setVec(double x1, double x2, double x3);


}
