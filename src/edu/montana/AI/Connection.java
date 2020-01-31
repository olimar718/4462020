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

    protected Boolean connectionCorrect(){
        //System.out.println(this.connectedRegion1.color +" "+this.connectedRegion2.color);//TODO empty color ????
        if (this.connectedRegion1.color.equals(this.connectedRegion2.color)||this.connectedRegion1.color.equals("")||this.connectedRegion2.color.equals("")){
            this.status=Boolean.FALSE;
            return this.status;
        }
        this.status=Boolean.TRUE;
        return this.status;

    }
    @Override
    public Object clone(){
        Connection connection = null;
        try{
            connection = (Connection) super.clone();
        }catch(Exception e){
            connection = new Connection(Boolean.FALSE, (Region) connectedRegion1.clone(), (Region) connectedRegion2.clone());
        }
        return connection;
    }
}
