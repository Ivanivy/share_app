package services;

import chat.Chat;

public class BackgroundChat extends Thread {
    public String client;
    public BackgroundChat(String client){
        this.client = client ;
    }

    @Override
    public void run(){
        try{
            Chat.listen(this.client);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

}
