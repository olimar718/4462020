package edu.montana.AI;

public class Connection {
    public Boolean status;
    public Region connectedRegion1;
    public Region connectedRegion2;

    protected Connection(Boolean status, Region connectedRegion1, Region connectedRegion2) {
        this.status = status;
        this.connectedRegion1 = connectedRegion1;
        this.connectedRegion2 = connectedRegion2;
    }

    protected Boolean connectionCorrect() {// returns Boolean.TRUE if the connection is correct
        if (this.connectedRegion1.color.equals(this.connectedRegion2.color) || this.connectedRegion1.color.equals("")
                || this.connectedRegion2.color.equals("")) {// if the color are the same or if any of the colors are
                                                            // empty
            this.status = Boolean.FALSE;
            return this.status;
        }
        this.status = Boolean.TRUE;
        return this.status;

    }

    @Override
    public Object clone() {// is needed for the genetic algorithms. Is used by the map class. It allows to
        // do a deep copy instead of a copy by reference (which would overwrite the same
        // memory location).
        Connection connection = null;
        try {
            connection = (Connection) super.clone();
        } catch (CloneNotSupportedException e) {//it always goes here and we have to clone manually
            connection = new Connection(Boolean.FALSE, (Region) connectedRegion1.clone(),
                    (Region) connectedRegion2.clone());
        }
        return connection;
    }
}
