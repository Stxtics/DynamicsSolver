package project;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Pulley {
    Ellipse2D circle;
    Line2D bRope;
    Line2D tRope;

    Pulley(Ellipse2D circle, Line2D bRope, Line2D tRope) {
        this.circle = circle;
        this.bRope = bRope;
        this.tRope = tRope;
    }
}
