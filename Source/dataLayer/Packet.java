package dataLayer;

public class Packet implements java.io.Serializable {
// defines a packet 
    private int id;
    private Object object;

    public Packet(){

    }
    public Packet(int id, Object object) {
        this.id = id;
        this.object = object;
    }

    public int getId() {
        return this.id;
    }

    public Object getObject() {
        return this.object;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
