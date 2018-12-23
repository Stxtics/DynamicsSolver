package project;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

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
            totalForce += rope.getObject1().getRightForce();
            totalForce -= rope.getObject1().getLeftForce();
            if (shapeManager.ropes.indexOf(rope) == shapeManager.ropes.size() - 1) {
                totalForce += rope.getObject2().getRightForce();
                totalForce -= rope.getObject2().getLeftForce();
            }
        }
        return totalForce;
    }

    private void solveHorizontally() {
        for (Rope rope : shapeManager.ropes) {
            if (rope.getTension() == null) {
                if (shapeManager.hasFriction) {
                    if (rope.getObject1().getRightForce() - rope.getObject1().getLeftForce() == 0) {
                        rope.setTension(((rope.getObject1().getMass() * shapeManager.acceleration) + (shapeManager.coFriction * rope.getObject1().getMass() * shapeManager.gravity)));
                    } else if (rope.getObject2().getRightForce() - rope.getObject2().getLeftForce() == 0) {
                        rope.setTension(((rope.getObject2().getMass() * shapeManager.acceleration) + (shapeManager.coFriction * rope.getObject2().getMass() * shapeManager.gravity)));
                    } else if (rope.getObject1().getRightForce() - rope.getObject1().getLeftForce() > 0) {
                        rope.setTension(((rope.getObject1().getRightForce() - rope.getObject1().getLeftForce()) - (shapeManager.coFriction * rope.getObject1().getMass() * shapeManager.gravity) - (rope.getObject1().getMass() * shapeManager.acceleration)));
                    } else if (rope.getObject2().getRightForce() - rope.getObject2().getLeftForce() > 0) {
                        rope.setTension(((rope.getObject2().getRightForce() - rope.getObject2().getLeftForce()) - (shapeManager.coFriction * rope.getObject2().getMass() * shapeManager.gravity) - (rope.getObject2().getMass() * shapeManager.acceleration)));
                    }
                } else {
                    double magnitude = 0;
                    if (rope.getObject1().getRightForce() != null) magnitude += rope.getObject1().getRightForce();
                    if (rope.getObject1().getLeftForce() != null) magnitude -= rope.getObject1().getLeftForce();
                    if (magnitude <= 0) {
                        rope.setTension(((rope.getObject1().getMass() * shapeManager.acceleration) - magnitude));
                    } else {
                        rope.setTension((magnitude - (rope.getObject1().getMass() * shapeManager.acceleration)));
                    }
                }
            }
        }
        for (Entity entity : shapeManager.entities) {
            if (entity.getMass() == 0 && shapeManager.acceleration != null) {
                double totalForce = entity.getRightForce() - entity.getLeftForce();
                if (totalForce != 0) {
                    entity.setMass(totalForce / shapeManager.acceleration);
                }
            }
        }
    }

    private double massSum() {
        double totalMass = 0;
        for (Entity object : shapeManager.entities) {
            if (object.getMass() == null || object.getMass() == 0) {
                totalMass = -1;
                break;
            }
            totalMass += object.getMass();
        }
        return totalMass;
    }

    void resolveVertical() {
        for (Entity object : shapeManager.entities) {
            if (object.getMass() != null && object.getMass() != 0) {

            }
        }
    }

    void calculateSelections(ArrayList<String> selections) {
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
                if (shapeManager.acceleration != null) {
                    shapeManager.gui.showMessageBox("Acceleration = " + String.format("%." + shapeManager.precision + "f", shapeManager.acceleration) + "m/s/s", "Acceleration");
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
        if (shapeManager.coFriction != null) {
            shapeManager.gui.showMessageBox("μ = " + shapeManager.coFriction, "μ");
            solveHorizontally();
        } else if (!shapeManager.hasFriction) {
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

    void LinkRopesToEntities(ArrayList<Rope> ropes, ArrayList<Entity> entities, ArrayList<Pulley> pulleys) {
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
                if (pulleys.stream().filter(x -> x.bRope == rope || x.tRope == rope).findFirst().orElse(null) == null) {
                    Point ropeL = new Point(((int) rope.getBounds2D().getMinX() - 5), ((int) rope.getBounds2D().getY()));
                    Point ropeR = new Point(((int) rope.getBounds2D().getMaxX() - 5), ((int) rope.getBounds2D().getY()));
                    if (entity.contains(ropeL)) {
                        Rope currentRope = ropes.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(rope)).findFirst().orElse(null);
                        Entity currentEntity = entities.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(entity)).findFirst().orElse(null);
                        if (currentRope != null && currentEntity != null) {
                            currentRope.setObject1(currentEntity);
                        }
                    }
                    if (entity.contains(ropeR)) {
                        Rope currentRope = ropes.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(rope)).findFirst().orElse(null);
                        Entity currentEntity = entities.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(entity)).findFirst().orElse(null);
                        if (currentRope != null && currentEntity != null) {
                            currentRope.setObject2(currentEntity);
                        }
                    }
                } else {
                    Pulley pulley = pulleys.stream().filter(x -> x.bRope == rope || x.tRope == rope).findFirst().orElse(null);
                    if (pulleys.stream().filter(x -> x.bRope == rope).findFirst().orElse(null) != null) {
                        Point ropeEnd = new Point ((int)rope.getBounds().getMaxX(), (int)rope.getBounds().getY());
                        if (entity.contains(ropeEnd)) {
                            Rope currentRope = ropes.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(pulley.circle)).findFirst().orElse(null);
                            Entity currentEntity = entities.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(entity)).findFirst().orElse(null);
                            if (currentRope != null && currentEntity != null) {
                                currentRope.setObject1(currentEntity);
                            }
                        }
                    } else if (pulleys.stream().filter(x -> x.tRope == rope).findFirst().orElse(null) != null) {
                        Point ropeEnd = new Point ((int)rope.getBounds().getMaxX(), (int)rope.getBounds().getY());
                        if (entity.contains(ropeEnd)) {
                            Rope currentRope = ropes.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(pulley.circle)).findFirst().orElse(null);
                            Entity currentEntity = entities.stream().filter(x -> x.getShapeIndex() == shapeManager.content.shapes.indexOf(entity)).findFirst().orElse(null);
                            if (currentRope != null && currentEntity != null) {
                                currentRope.setObject2(currentEntity);
                            }
                        }
                    }
                }
            }
        }
    }
}