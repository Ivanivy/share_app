package fileexplorer;

import java.util.ArrayList;

public class SharedFiles {
    //this will be a sort of data base of autorized shared files
    public static  ArrayList<String> sharedFiles = new ArrayList<>();

    public void addSharedFile(String path){
        sharedFiles.add(path);
    }

    public ArrayList<String> getSharedFilesList(){
        return sharedFiles;
    }
}
