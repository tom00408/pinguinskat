package skat;

import java.io.*;
import java.util.ArrayList;

public class ScoreboardManager {

    public final String FILE_PATH = "data/scoreboard.csv";

    public void addScore(String[] data){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH,true))){
            StringBuilder newLine = new StringBuilder();
            for(int i = 0; i < data.length; i++){
                newLine.append(data[i]);
                if(i < data.length-1){
                    newLine.append(",");
                }
            }
            writer.newLine();
            writer.write(newLine.toString());
            System.out.println("Zeile hinzugefügt");
        }catch (IOException e){
            System.out.println("Fehler beim Schreiben in die Datei "+e.getMessage());
        }
        sortScoreboard();
    }

    public String[][] getScoreBoard(){
        ArrayList<String[]> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] values = line.split(",");
                if(values.length == 3)
                    lines.add(values);
            }
        } catch (IOException e){
            System.out.println("Fehler beim lesen der Datei "+e.getMessage());
        }

        return lines.toArray(new String[0][0]);
    }


    public void sortScoreboard(){

        String tempFilePath = FILE_PATH+".tmp";

        String sortCommand = "sort -t',' -k 3 -n -r "+FILE_PATH + " -o "+tempFilePath;

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", sortCommand);

        try{
            Process p = processBuilder.start();

            int exitCode = p.waitFor();
            if(exitCode == 0){
                File originalFile = new File(FILE_PATH);
                File sortedFile = new File(tempFilePath);

                if(originalFile.delete() && sortedFile.renameTo(originalFile)){
                    System.out.println("Datei erfolgreich sorted und überschrieben");
                }else{
                    System.out.println("Fehler beim Überschreiben der Datei");
                }
            }else{
                System.out.println("Fehler beim Sortieren der Datei");
            }
        }catch (IOException | InterruptedException e){
            System.out.println("Fehler beim Ausführen des Bash befehls "+ e.getMessage());
        }
    }


}
