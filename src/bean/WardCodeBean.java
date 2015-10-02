package bean;

/**
 *
 * @author Gaurab Pradhan
 */
public class WardCodeBean {
    private String vdc = "";
    private String district = "";
    private int wardNum;
    private String wardCode = "";

    public int getWardNum() {
        return wardNum;
    }

    public void setWardNum(int wardNum) {
        this.wardNum = wardNum;
    }

    
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

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }
}