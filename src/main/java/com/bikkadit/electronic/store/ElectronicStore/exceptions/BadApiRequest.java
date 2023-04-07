package com.bikkadit.electronic.store.ElectronicStore.exceptions;

public class BadApiRequest extends RuntimeException{


    public BadApiRequest(String message){
        super(message);

    }

    public BadApiRequest(){
        super("Bad Api request");
    }

}
