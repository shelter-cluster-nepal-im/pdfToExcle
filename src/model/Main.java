package model;

import bean.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import utility.*;

/**
 *
 * @author Gaurab Pradhan
 */
public class Main {

    static String district;

    public static void main(String[] args) {
        PropertiesUtil.loadPropertiesFile();
        Document document = getDocument();
        String url = null;
        if (document != null) {
            System.out.println("CONNECTION ESTABLISHED");
            Elements urlList = document.select(PropertiesUtil.getTableTag()).select(PropertiesUtil.getAnchorTag());
            ParsePdf pPdf = new ParsePdf();
            System.out.println("PDF TO EXCLE FOR CENSUS DATA FROM CENTRAL BUREAU OF STATISTICS 2011");
            for (int i = 0; i < urlList.size(); i++) {
                url = urlList.get(i).select("a").first().absUrl("href");
                district = urlList.get(i).text().trim();
                System.out.println("PREPARING TO CRAWL DATA FROM "+district.toUpperCase()+" DISTRICT");
                System.out.println("PDF URL FOR "+district.toUpperCase());
                String pdfText = pPdf.readPdf(url);
                List<CensusBean> list = new ArrayList<CensusBean>();
                list = filterPdfText(pdfText.replaceAll(",", ""));
                WriteToExcle wTe = new WriteToExcle();
                wTe.writeIntoExcle(list);
            }
        }
        System.out.println("PDF TO EXCLE FOR CENSUS DATA FROM CENTRAL BUREAU OF STATISTICS 2011");
    }

    private static List<CensusBean> filterPdfText(String pdfText) {
        List<CensusBean> mainList = new ArrayList<CensusBean>();
        pdfText = pdfText.replaceAll("V.D.C./MUNICIPALITY :", "SPLIT ME V.D.C./MUNICIPALITY :");
        String[] division = pdfText.split("SPLIT ME");
        for (int i = 1; i < division.length - 1; i++) {
            String[] temp = division[i].split("\n");
            String vdc = temp[0];
            vdc = vdc.replaceAll("V.D.C./MUNICIPALITY :", "").trim();
            vdc = vdc.replaceAll("\\d+.*", "").replaceAll("\\W", " ").trim();
            for (int j = 1; j < temp.length; j++) {
                String check = temp[j];
                boolean flag = check.matches(".*[a-zA-Z].*");
                if (!flag) {
                    char[] chars = check.toCharArray();
                    List<Integer> data = new ArrayList<Integer>();
                    String value = "";
                    for (int k = 0; k < chars.length; k++) {
                        if (data.size() < 3) {
                            char c = chars[k];
                            if (Character.isDigit(c)) {
                                int a = Character.getNumericValue(c);
                                value = value + a;
                            } else {
                                data.add(Integer.parseInt(value));
                                value = "";
                            }
                        } else {
                            break;
                        }
                    }
                    // adding in bean
                    if (data.size() > 2) {
                        CensusBean bean = new CensusBean();
                        bean.setDistrict(district.trim());
                        bean.setVdc(vdc.trim());
                        bean.setWardNum(data.get(0));
                        bean.setHouseHold(data.get(1).toString());
                        bean.setPopu(data.get(2).toString());
                        mainList.add(bean);
                        System.out.println(bean.toString());
                    }
                }
            }
        }
        return mainList;
    }

    public static Document getDocument() {
        Document document = null;
        try {
            System.out.println("CONNECTING TO THE CENTRAL BUREAU OF STATISTICS Url : "+PropertiesUtil.getWebUrl() );
            document = Jsoup.connect(PropertiesUtil.getWebUrl()).get();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;
    }
}
