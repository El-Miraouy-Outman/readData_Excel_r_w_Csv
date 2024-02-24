package com.example.pfeextractdata;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class PfeUploadService {
    public static int  nbrUser=1000;
    public static String userTotal="C:\\Users\\E L I T E B O O K\\Desktop\\file git\\PfeExtractData\\src\\main\\java\\com\\example\\pfeextractdata\\user\\AuserData.csv";

    public String urlfile = "C:\\Users\\E L I T E B O O K\\Desktop\\file git\\PfeExtractData\\src\\main\\java\\com\\example\\pfeextractdata\\user\\user_3000\\user_";
    public static String cheminFichierExcel="C:\\Users\\E L I T E B O O K\\Desktop\\file git\\PfeExtractData\\src\\main\\java\\com\\example\\pfeextractdata\\dataaaaaaa.xlsx";
    public static Sheet sheet_1 ;
    public static Sheet sheet_2 ;
    public static Workbook workbook;

    static {
        System.out.println("code static ");
        try {
            workbook = WorkbookFactory.create(new FileInputStream(new File(cheminFichierExcel)));

            System.out.println("nbr sheet "+workbook.getNumberOfSheets());
            sheet_1=workbook.getSheetAt(0);
            sheet_2=workbook.getSheetAt(1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void persistFromFile() throws IOException, CsvException {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(userTotal));

        List<String[]> lignes = csvReader.readAll();
        int compteur=0;
        // Vous pouvez maintenant traiter chaque ligne
        for (String[] ligne : lignes) {
            for (String userId : ligne) {
                System.out.print(userId);

                //find trajectoire of user_id =ligne
                finTrajectoireAndEnregister(userId);

            }
            System.out.println(); // Saut de ligne entre chaque ligne du CSV
            compteur++;
        }
            System.out.println("nbr ligne : "+compteur);
    } catch (IOException | CsvException e) {
        e.printStackTrace();
    }

    }

    private void finTrajectoireAndEnregister(String userIdChercher) throws IOException {
        String local;
        ++nbrUser;
        String userfile = urlfile+nbrUser+".csv";
        File newFileUser = new File(userfile);
        try (CSVWriter writer = new CSVWriter(new FileWriter(userfile), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

                String[] entetes = {"Latitude", "Longitude"};
                writer.writeNext(entetes);

                System.out.println("==== Construire user ===== : "+nbrUser);
                // Supposons que le fichier Excel a une seule feuille de calcul, si ce n'est pas le cas, ajustez en conséquence.
                Sheet sheetTmp = null;
                int verified=0;
                for (int i=0;i<2;i++){
                    if(i==1){
                        sheetTmp=sheet_1;
                    }

                    else{
                        sheetTmp=sheet_2;
                    }
                    System.out.println("sheet :"+i);
                    // Parcourir toutes les lignes de la feuille de calcul
                    for (Row row : sheetTmp) {
                        try {
                            if(row.getCell(0).getStringCellValue() !=null
                                    && Objects.equals(row.getCell(1).getStringCellValue(), userIdChercher)){

                                local=row.getCell(0).getStringCellValue();
                                String[] keys = local.split("/");
                                // Écrire les en-têtes si nécessaire
                                if(keys.length==2){
                                    // System.out.println("key : "+keys.length);
                                    //System.out.println(keys[0]+"-----"+keys[1] );
                                    verified++;
                                    String[] ligne = {keys[0], keys[1]};
                                    writer.writeNext(ligne);
                                }

                            }
                        }
                        catch ( NullPointerException e){
                            //System.out.println(e);
                        }


                    }

                }
            if (verified==0){
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+nbrUser);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
