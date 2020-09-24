package com.mobilap.turnKey.services;


import com.mobilap.turnKey.models.PasswordModel;

//import org.apache.poi.hssf.model.Sheet;
//import org.apache.poi.hssf.model.Workbook;
//import org.apache.poi.ss.usermodel.Row;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CreateXLSFile
{
    String filename = "TurnKeyFile.xls";
    String textToWrite = "This is some text!";

    String [] headerCol = {"Dossier","Titre","Utilisateur", "Mot de passe", "Site Web", "Informations Complémentaires", "Date de création","Date de modification"};


    public String createContentFile(List<PasswordModel> listePasswords){
//       XSSFWorkbook workbook = new XSSFWorkbook();
//       XSSFSheet sheet = workbook.createSheet("Users");
  //     Row row = sheet.createRow(0);
 //      row.createCell(0).setCellValue("tTest");

        String text = "Dossier \t Titre\tUtilisateur\t Mot de passe\t Site Web\t Informations Complémentaires\t Date de création\t Date de modification" + "\n";
//        for(int i=0; i< listePasswords.size(); i++) {
//            text += listePasswords.get(i).getFolder()+ "\t";
//            text += listePasswords.get(i).getTitle()+ "\t";
//            text += listePasswords.get(i).getUser()+ "\t";
//            text += listePasswords.get(i).getPassword()+ "\t";
//            text += listePasswords.get(i).getUrl()+ "\t";
//            text += listePasswords.get(i).getInformations()+ "\t";
//            text += listePasswords.get(i).getDateCreation().toString()+ "\t";
//            text += listePasswords.get(i).getDateModification().toString()+ "\n";
//
//        }
        return text;

    }

}
