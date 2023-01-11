package parser.cell;

public class Cell {
    private String address;
    private String value;
    private Integer sharedAddress;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSharedAddress() {
        return sharedAddress;
    }

    public void setSharedAddress(Integer sharedAddress) {
        this.sharedAddress = sharedAddress;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "address='" + address + '\'' +
                ", value='" + value + '\'' +
                ", sharedAddress=" + sharedAddress +
                '}';
    }
}
