package project;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

class Solver {
    ShapeManager shapeManager;

    Solver(ShapeManager shapeManager) {
        this.shapeManager = shapeManager;
    }

    void Solve(double horizontalForce) {
        if (horizontalForce < 0) {
            shapeManager.gui.showMessageBox("The system will move left.", "Solve");
        } else if (horizontalForce > 0) {
            shapeManager.gui.showMessageBox("The system will move right.", "Solve");
        } else {
            shapeManager.gui.showMessageBox("The system will not move.", "Solve");
        }
    }

    double resolveHorizontal() {
        double totalForce = 0;
        for (Rope rope : shapeManager.ropes) {
            if (rope.getObject1().getRightForce() != null) totalForce += rope.getObject1().getRightForce();
            if (rope.getObject1().getLeftForce() != null) totalForce -= rope.getObject1().getLeftForce();
            if (shapeManager.ropes.indexOf(rope) == shapeManager.ropes.size() - 1) {
                if (rope.getObject2().getRightForce() != null) totalForce += rope.getObject2().getRightForce();
                if (rope.getObject2().getLeftForce() != null) totalForce -= rope.getObject2().getLeftForce();
            }
        }
        return totalForce;
    }

    void solveHorizontally() {
        for (Rope rope : shapeManager.ropes) {
            if (rope.getTension() == null) {
                if (shapeManager.hasFriction) {

                } else {
                    double magnitude = 0;
                    if (rope.getObject1().getRightForce() != null) magnitude += rope.getObject1().getRightForce();
                    if (rope.getObject1().getLeftForce() != null) magnitude -= rope.getObject1().getLeftForce();
                    if (magnitude < 0) {
                        rope.setTension((rope.getObject1().getMass() * shapeManager.acceleration) - magnitude);
                    } else {
                        rope.setTension(magnitude - (rope.getObject1().getMass() * shapeManager.acceleration));
                    }
                }
            }
        }
    }

    double massSum() {
        double totalMass = 0;
        for (Entity object : shapeManager.entities) {
            if (object.getMass() == null) {
                totalMass = -1;
                break;
            }
            totalMass += object.getMass();
        }
        return totalMass;
    }

    void resolveVertical() {

    }

    void calculateSelections(List<String> selections) {
        for (int i = 0; i < 5; i++) {
            if (selections.contains("Distance")) {
                if (shapeManager.fVelocity != null && shapeManager.time != null && shapeManager.acceleration != null) {
                    shapeManager.distance = (shapeManager.fVelocity * shapeManager.time) - 0.5 * (shapeManager.acceleration * Math.pow(shapeManager.time, 2));
                    selections.remove("Distance");
                } else if (shapeManager.iVelocity != null && shapeManager.time != null && shapeManager.acceleration != null) {
                    shapeManager.distance = (shapeManager.iVelocity * shapeManager.time) - 0.5 * (shapeManager.acceleration * Math.pow(shapeManager.time, 2));
                    selections.remove("Distance");
                } else if (shapeManager.time != null && shapeManager.iVelocity != null && shapeManager.fVelocity != null) {
                    shapeManager.distance = (shapeManager.time / 2) * (shapeManager.iVelocity + shapeManager.fVelocity);
                    selections.remove("Distance");
                } else if (shapeManager.iVelocity != null && shapeManager.fVelocity != null && shapeManager.acceleration != null) {
                    shapeManager.distance = (Math.pow(shapeManager.iVelocity, 2) + Math.pow(shapeManager.fVelocity, 2)) / (2 * shapeManager.acceleration);
                    selections.remove("Distance");
                }
            }
            if (selections.contains("Initial Velocity")) {
                if (shapeManager.fVelocity != null && shapeManager.acceleration != null && shapeManager.time != null) {
                    shapeManager.iVelocity = shapeManager.fVelocity - (shapeManager.acceleration * shapeManager.time);
                    selections.remove("Initial Velocity");
                } else if (shapeManager.distance != null && shapeManager.acceleration != null && shapeManager.time != null) {
                    shapeManager.iVelocity = (shapeManager.distance - (shapeManager.acceleration * Math.pow(shapeManager.time, 2))) / (2 * shapeManager.time);
                    selections.remove("Initial Velocity");
                } else if (shapeManager.distance != null && shapeManager.time != null && shapeManager.fVelocity != null) {
                    shapeManager.iVelocity = ((2 * shapeManager.distance) / shapeManager.time) + shapeManager.fVelocity;
                    selections.remove("Initial Velocity");
                } else if (shapeManager.acceleration != null && shapeManager.distance != null && shapeManager.fVelocity != null) {
                    shapeManager.iVelocity = Math.pow((2 * shapeManager.acceleration * shapeManager.distance - Math.pow(shapeManager.fVelocity, 2)), 0.5);
                    selections.remove("Initial Velocity");
                }
            }
            if (selections.contains("Final Velocity")) {
                if (shapeManager.iVelocity != null && shapeManager.acceleration != null && shapeManager.time != null) {
                    shapeManager.fVelocity = shapeManager.iVelocity + (shapeManager.acceleration * shapeManager.time);
                    selections.remove("Initial Velocity");
                } else if (shapeManager.distance != null && shapeManager.acceleration != null && shapeManager.time != null) {
                    shapeManager.fVelocity = (shapeManager.distance + (shapeManager.acceleration * Math.pow(shapeManager.time, 2))) / (2 * shapeManager.time);
                    selections.remove("Initial Velocity");
                } else if (shapeManager.distance != null && shapeManager.time != null && shapeManager.iVelocity != null) {
                    shapeManager.fVelocity = ((2 * shapeManager.distance) / shapeManager.time) - shapeManager.iVelocity;
                    selections.remove("Initial Velocity");
                } else if (shapeManager.acceleration != null && shapeManager.distance != null && shapeManager.iVelocity != null) {
                    shapeManager.fVelocity = Math.pow((Math.pow(shapeManager.iVelocity, 2) + 2 * shapeManager.acceleration * shapeManager.distance), 0.5);
                    selections.remove("Initial Velocity");
                }
            }
            if (selections.contains("Acceleration")) {
                if (shapeManager.iVelocity != null && shapeManager.fVelocity != null && shapeManager.time != null) {
                    shapeManager.acceleration = (shapeManager.fVelocity - shapeManager.iVelocity) / shapeManager.time;
                    selections.remove("Acceleration");
                } else if (shapeManager.fVelocity != null && shapeManager.time != null && shapeManager.distance != null) {
                    shapeManager.acceleration = ((2 * (shapeManager.fVelocity * shapeManager.time - shapeManager.distance)) / (Math.pow(shapeManager.time, 2)));
                    selections.remove("Acceleration");
                } else if (shapeManager.distance != null && shapeManager.iVelocity != null && shapeManager.time != null) {
                    shapeManager.acceleration = ((2 * (shapeManager.distance - (shapeManager.iVelocity * shapeManager.time))) / (Math.pow(shapeManager.time, 2)));
                    selections.remove("Acceleration");
                } else if (shapeManager.fVelocity != null && shapeManager.iVelocity != null && shapeManager.distance != null) {
                    shapeManager.acceleration = ((Math.pow(shapeManager.fVelocity, 2) - Math.pow(shapeManager.iVelocity, 2)) / (2 * shapeManager.distance));
                    selections.remove("Acceleration");
                } else if (massSum() != -1) {
                    shapeManager.acceleration = resolveHorizontal() / massSum();
                    selections.remove("Acceleration");
                }
            }
            if (selections.contains("Time")) {
                if (shapeManager.fVelocity != null && shapeManager.iVelocity != null && shapeManager.acceleration != null) {
                    shapeManager.time = (shapeManager.fVelocity - shapeManager.iVelocity) / shapeManager.acceleration;
                    selections.remove("Time");
                } else if (shapeManager.fVelocity != null && shapeManager.acceleration != null && shapeManager.distance != null) {
                    shapeManager.time = (shapeManager.fVelocity - Math.pow(Math.pow(shapeManager.fVelocity, 2) - 2 * shapeManager.acceleration * shapeManager.distance, 0.5)) / shapeManager.acceleration;
                    selections.remove("Time");
                } else if (shapeManager.acceleration != null && shapeManager.distance != null && shapeManager.iVelocity != null) {
                    shapeManager.time = (Math.pow(2 * shapeManager.acceleration * shapeManager.distance + Math.pow(shapeManager.iVelocity, 2), 0.5) - shapeManager.iVelocity) / shapeManager.acceleration;
                    selections.remove("Time");
                } else if (shapeManager.distance != null && shapeManager.iVelocity != null && shapeManager.fVelocity != null) {
                    shapeManager.time = (2 * shapeManager.distance) / (shapeManager.iVelocity + shapeManager.fVelocity);
                    selections.remove("Time");
                }
            }
            if (selections.contains("μ")) {
                if (shapeManager.acceleration != null && massSum() != -1) {
                    shapeManager.coFriction = (resolveHorizontal() - (massSum() * shapeManager.acceleration)) / (massSum() * shapeManager.gravity);
                    selections.remove("μ");
                }
            }
        }
        if (shapeManager.acceleration != null) {
            shapeManager.gui.showMessageBox("Acceleration = " + shapeManager.acceleration + "m/s/s", "Acceleration");
        }
        if (shapeManager.coFriction != null) {
            shapeManager.gui.showMessageBox("μ = " + shapeManager.coFriction, "μ");
            solveHorizontally();
        }
    }

    boolean allRopesConnected(ArrayList<Rope> ropes) {
        for (Rope rope : ropes) {
            if (rope.getObject1() == null || rope.getObject2() == null) {
                return false;
            }
        }
        return true;
    }

    void LinkRopesToEntities(ArrayList<Rope> ropes, ArrayList<Entity> entities) {
        ArrayList<Shape> lines = new ArrayList<>();
        ArrayList<Shape> rectangles = new ArrayList<>();
        for (Shape shape : shapeManager.content.shapes) {
            if (shape.getClass() == Line2D.Double.class) {
                lines.add(shape);
            } else if (shape.getClass() == Rectangle2D.Double.class) {
                rectangles.add(shape);
            }
        }

        for (Shape rope : lines) {
            for (Shape entity : rectangles) {
                Point ropeL = new Point(((int) rope.getBounds2D().getMinX() - 5), ((int) rope.getBounds2D().getY()));
                Point ropeR = new Point(((int) rope.getBounds2D().getMaxX() - 5), ((int) rope.getBounds2D().getY()));
                if (entity.contains(ropeL)) {
                    ropes.get(lines.indexOf(rope)).setObject1(entities.get(rectangles.indexOf(entity)));
                }
                if (entity.contains(ropeR)) {
                    ropes.get(lines.indexOf(rope)).setObject2(entities.get(rectangles.indexOf(entity)));
                }
            }
        }
    }
}
