package com.example.max.eindopdrachtprog4app.service;

/**
 * Bevat o.a. URL definities.
 */

public class Config {

    public static final String URL_HELLO = "https://einopdrachtprog4.herokuapp.com/api/v1/hello";
    public static final String URL_LOGIN = "https://einopdrachtprog4.herokuapp.com/api/v1/login";
    public static final String URL_ALL_FILMS = "https://einopdrachtprog4.herokuapp.com/api/v1/films";

    public static final String URL_FILMS(int offsetid){
        String URL = "https://einopdrachtprog4.herokuapp.com/api/v1/films?offset=" + offsetid + "&count=" + 10;
        return URL;
    }

    public static final String URL_FILM(String filmid) {
        String URL =  "https://einopdrachtprog4.herokuapp.com/api/v1/films/" + filmid;
        return URL;
    };

    public static final String URL_REGISTER = "https://einopdrachtprog4.herokuapp.com/api/v1/register";

    public static final String URL_LEENFILM(String customer_id){
        String Url = "https://einopdrachtprog4.herokuapp.com/api/v1/rentals/" + customer_id;
        return Url;
    }
    public static final String URL_LENING(String customer_id,String inventory_id){
        String URL = "https://einopdrachtprog4.herokuapp.com/api/v1/rentals/" + customer_id + "/" + inventory_id;
        return URL;
    }


}
