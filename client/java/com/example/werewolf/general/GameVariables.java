package com.example.werewolf.general;

import com.example.werewolf.types.player;

import java.util.ArrayList;

public class GameVariables {
    private static String pseudo;
    private static String room;
    private static int nbPlayer;
    private static int nbActivePlayer;
    private static ArrayList<player> playerList = new ArrayList<player>();
    private static String gameStatus = "sleep"; //Playing or not
    private static String[] role = {"cupidon", "seer", "werewolf", "witch"};
    private static int advancement = 0;

    public static String getPseudo() {
        return pseudo;
    }

    public static void setPseudo(String pseudo) {
        GameVariables.pseudo = pseudo;
    }

    public static String getRoom() {
        return room;
    }

    public static void setRoom(String room) {
        GameVariables.room = room;
    }

    public static int getNbPlayer() {
        return nbPlayer;
    }

    public static void setNbPlayer(int nbPlayer) {
        GameVariables.nbPlayer = nbPlayer;
    }

    public static int getNbActivePlayer() {
        return nbActivePlayer;
    }

    public static void setNbActivePlayer(int nbActivePlayer) {
        GameVariables.nbActivePlayer = nbActivePlayer;
    }

    public static ArrayList<player> getPlayerList() {
        return playerList;
    }

    public static void setPlayerList(player player) {
        playerList.add(player);
    }

    public static void clearPlayerList(){
        playerList.clear();
    }

    public static String getGameStatus() {
        return gameStatus;
    }

    public static void setGameStatus(String gameStatus) {
        GameVariables.gameStatus = gameStatus;
    }

    public static String[] getRole() {
        return role;
    }

    public static int getAdvancement() {
        return advancement;
    }

    public static void setAdvancement(int advancement) {
        GameVariables.advancement = advancement;
    }
}
