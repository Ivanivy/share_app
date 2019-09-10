package fileexplorer;

import java.io.File;
import java.util.Scanner;

public class FileExplorer {
     File[] files;
     File file ;

     //i'am defining action types on files that will be used in the method explore
    // 0-is to enter into a folder 1-is to return absolute path even it is a path
    //this absolute path can be even be compressed


    //the explore method is for cli we have to use changeDirectory and getCurrentFiles

    public  String explore(){
        int i = 0;
        int choice ;
        int type;
        Scanner clavier = new Scanner(System.in);
        files = File.listRoots();
        while(i<files.length){
            System.out.println(files[i]);
            i++;
        }

        while(true){
            //this will take the choice but is has to be managed by the ui
            System.out.println("enter the number of your choice");
            choice = clavier.nextInt();
            type = clavier.nextInt();

            if(choice<0 || type<0){
                return "";
            }
            file = files[choice];
            if(file.isFile() || (file.isDirectory() && type==1)){
                return file.getAbsolutePath();
            }
            else if(file.isDirectory() && type==0){
                files = file.listFiles();
                i = 0;
                while(i<files.length){
                    System.out.println(i+"- "+files[i].getName());
                    i++;
                }
            }
        }
    }

// this method permit to the object to move into his current directory or to return a file
    public String changeDirectory(int choice,int type){
            if(choice<0 || type<0){
                return "";
            }
            file = files[choice];
            if(file.isFile() || (file.isDirectory() && type==1)){
                return file.getAbsolutePath();
            }
            else if(file.isDirectory() && type==0){
                files = file.listFiles();
            }
            return "";
        }

//this will permit to get the list of all the files in the current directory 
    public File[] getcurrentListFiles(){
        return files;
    }

    public String getFileName(){
        return file.getName();
    }
}
