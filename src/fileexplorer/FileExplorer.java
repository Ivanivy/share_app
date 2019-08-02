package fileexplorer;

import java.io.File;
import java.util.Scanner;

public class DisplayAvailableFiles {
      File[] files;
     File file ;

    public  String explore(){
        int i = 0;
        int choice ;
        Scanner clavier = new Scanner(System.in);
        files = File.listRoots();
        while(i<files.length){
            System.out.println(files[i]);
            i++;
        }

        while(true){
            choice = clavier.nextInt();
            if(choice<0){
                return "";
            }
            file = files[choice];
            if(file.isFile()){
                return file.getAbsolutePath();
            }
            files = file.listFiles();
            i = 0;
            while(i<files.length){
                System.out.println(i+"- "+files[i].getName());
                i++;
            }
        }
    }

    public File[] getFiles(){
        return files;
    }
}
