package utility;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import java.io.IOException;

/**
 *
 * @author Gaurab Pradhan
 */
public class ParsePdf {
    public static String readPdf(String URL) {
        String pdftext = "";
        try {
            PdfReader reader = new PdfReader(URL);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                pdftext = pdftext + strategy.getResultantText();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdftext;
    }
}