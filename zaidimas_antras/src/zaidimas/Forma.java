package zaidimas;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Forma {
    // aprašom Eager initialization singleton pattern
    private static final Forma instance = new Forma();
    
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    long timeStart, timeEnd;
    float scoreToBeat;
    int textLength, entryStringLength;
    String content, s, result;
    String[] allText;
    private boolean start = false;
    final String randomLine;

    public static Forma getInstace() {
        return instance;
    }
    
    public int getEntryStringLength() {
        return entryStringLength;
    }

    public void setEntryStringLength(int entryStringLength) {
        this.entryStringLength = entryStringLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public String getRandomLine() {
        return randomLine;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
    
    public Forma() {
        //pasiimam tekstus
        InputStream in = getClass().getResourceAsStream("/tekstai/content.txt");
        
        Scanner scanner = new Scanner(in).useDelimiter("\\A");
        //nuskaito visą tekstą iš content.txt
        String contentText = scanner.hasNext() ? scanner.next() : "";
        //suskaldo tekstą į masyvą pagal eilutes
        allText = contentText.split("\\r?\\n");
        Random rand = new Random();
        //sukuriamas naujas random skaičius nuo 0 iki teksto eilučių kiekio
        int randomLineNumber = rand.nextInt(allText.length);
        
        //priskiriama random teksto eilutė
        randomLine = allText[randomLineNumber];
        scoreToBeat = Zaidimas.getScore();
    }

    public String startMessage() {
        if (Zaidimas.getPlayer() > 1) {
            return "Jūs esate " + Zaidimas.getPlayer() + " žaidėjas, sumušti: " + scoreToBeat + " sek. rezultatą.";
        } else {
            return "Jūs esate pirmasis žaidėjas.";
        }
    }
    
    private String endGame(float gotScore) {
        return "Jūsų rezultatas: " + gotScore + " sek. Kitas žaidėjas: paspauskite ESC";
    }
    
    //atnaujina kintamuosius
    public void refreshValues(JTextArea textArea, JTextField entry) {
        content = textArea.getText();
        textLength = content.length();

        s = entry.getText();
        entryStringLength = s.length();
        if(entryStringLength <= textLength) {
            result = content.substring(0, entryStringLength);
        }        
    }
    
    public void timeStart() {
        if (entryStringLength == 1 && !start) {
            //dėl to kad čia uždedam start=true, if'as praeis tik vieną kartą
            start = true;
            //kiek laiko praeėjo milisekundėm nuo 1970-01-01
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            timeStart = timestamp.getTime();
        }
    }
    
    private float getScore() {
        float endScore;
        //nustatom kada jis baigė
        Timestamp time = new Timestamp(System.currentTimeMillis());
        timeEnd = time.getTime();

        //nustatom per kiek jis baigė ir suapvalinam skaičių
        endScore = (float) (timeEnd - timeStart) / 1000;
        endScore = round(endScore, 2);
        
        return endScore;
    }
    
    private boolean isBest(float gotScore) {
        return gotScore < Zaidimas.getScore();
    }
    
    //kai žaidėjas suvedė viską gerai
    public String finishGame() {
        float endScore = getScore();
        boolean isBest =  isBest(endScore);

        //ar žaidžia paskutinis žaidėjas
        if (Zaidimas.getPlayerCount() <= Zaidimas.getPlayer()) {
            if (isBest) {
                return "Jūs nugalėjote, Jūsų rezultatas: " + endScore + " sek.";
            } else {
                return "Nugalėjo " + Zaidimas.getWinningPlayer() + " žaidėjas!";
            }
        } else {
            if (isBest) {
                //jei žaidėjas ne paskutinis ir sumušo rezultatą
                Zaidimas.setScore(endScore);
                Zaidimas.setWinningPlayer(Zaidimas.getPlayer());
            }

            Zaidimas.setPlayer(Zaidimas.getPlayer() + 1);
            return endGame(endScore);
        }
    }
    
    public boolean textsAreEqual() {
        return s.equals(result);
    }
    
    public boolean isFinnished(){
        return entryStringLength == textLength;
    }
    
    //suapvalinam float skaičių (laiką)
    private float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        
        return bd.floatValue();
    }
    
    //suapvalinam float skaičių (laiką) iki 2 skaičių po kablelio
    private float round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        
        return bd.floatValue();
    }
}
