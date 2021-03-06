package bean;

/**
 *
 * @author Gaurab Pradhan
 */
public class CensusBean {

    private String vdc = "";
    private String district = "";
    private int wardNum;
    private String houseHold;
    private String popu;

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

    public String toString() {
        StringBuilder str = new StringBuilder("\n------------> Data <------------");
        str.append("\n--------> District ----------------------> " + this.district);
        str.append("\n--------> V.D.C./MUNICIPALITY -----------> " + this.vdc);
        str.append("\n--------> Ward Number -------------------> " + this.wardNum);
        str.append("\n--------> HouseHold ---------------------> " + this.houseHold);
        str.append("\n--------> Total Population------ --------> " + this.popu);
        str.append("\n-----------------------><------------------------");
        return str.toString();
    }
}
