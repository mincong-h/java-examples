package io.mincongh.zoo;

/**
 * @author Mincong Huang
 */
public class Zoo {

    private Animal king;
    private Animal queen;

    public boolean hasKing() {
        if (king == null) {
            System.out.println("We don't have king yet.");
            return false;
        } else {
            System.out.println("We have king !! He is " + king.getName());
            return true;
        }
    }

    public boolean hasQueen() {
        if (queen == null) {
            System.out.println("We don't have queen yet.");
            return false;
        } else {
            System.out.println("We have queen !! She is " + queen.getName());
            return true;
        }
    }
}
