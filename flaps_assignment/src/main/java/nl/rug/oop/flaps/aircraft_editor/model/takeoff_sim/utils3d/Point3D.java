package nl.rug.oop.flaps.aircraft_editor.model.takeoff_sim.utils3d;

import lombok.Getter;


public abstract class Point3D {
    @Getter
    public static class Double extends Point3D {

        /**
         * The X coordinate of this {@code Point2D}.
         */
        public double x, y, z;

        /**
         * Constructs and initializes a {@code Point2D} with
         * coordinates (0,&nbsp;0).
         */
        public Double() {
        }

        /**
         * Constructs and initializes a {@code Point2D} with the
         * specified coordinates.
         *
         * @param x the X coordinate
         * @param y the Y coordinate
         * @param z the Z coordinate
         */
        public Double(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }


        /**
         * {@inheritDoc}
         *
         * @since 1.2
         */
        public void setLocation(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Returns a {@code String} that represents the value
         * of this {@code Point2D}.
         *
         * @return a string representation of this {@code Point2D}.
         * @since 1.2
         */
        public String toString() {
            return "Point2D.Double[" + x + ", " + y + " , " + z + "]";
        }
    }

    protected Point3D() {
    }

    public abstract double getX();

    public abstract double getY();

    public abstract double getZ();


    public abstract void setLocation(double x, double y, double z);

    public static double distance(double x1, double y1, double z1,
                                  double x2, double y2, double z2) {
        x2 -= x1;
        y2 -= y1;
        z2 -= z1;
        return Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Point3D) {
            Point3D p2d = (Point3D) obj;
            return (getX() == p2d.getX()) && (getY() == p2d.getY() && (getZ() == p2d.getZ()));
        }
        return super.equals(obj);
    }
}
