package bean;
/**
 *
 * @author Gaurab Pradhan
 */
public class FinalBean {
    private String vdc = "";
    private String district = "";
    private int wardNum;
    private String houseHold;
    private String popu;
    private String wardCode = "";

    public String getVdc() {
        return vdc;
    }

    public void setVdc(String vdc) {
        this.vdc = vdc;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getWardNum() {
        return wardNum;
    }

    public void setWardNum(int wardNum) {
        this.wardNum = wardNum;
    }

    public String getHouseHold() {
        return houseHold;
    }

    public void setHouseHold(String houseHold) {
        this.houseHold = houseHold;
    }

    public String getPopu() {
        return popu;
    }

    public void setPopu(String popu) {
        this.popu = popu;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }
}