package com.example.werewolf;

//player object uses to store data about player
public class player {
    String pseudo;
    String role;

    public player() {
    }

    public player(String pseudo) {
        this.pseudo = pseudo;
    }

    public player( String pseudo, String role) {
        this.pseudo = pseudo;
        this.role = role;
    }
}
