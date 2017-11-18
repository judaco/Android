package com.example.juda.exercise3.util;

import java.util.ArrayList;

/**
 * Created by Juda on 18/11/2017.
 */

public class Club {
    private String mClubName, mImageUrl;
    private boolean isChampionsLeagueWinner;

    public Club(String mClubName, String mImageUrl, boolean isChampionsLeagueWinner) {
        this.mClubName = mClubName;
        this.mImageUrl = mImageUrl;
        this.isChampionsLeagueWinner = isChampionsLeagueWinner;
    }

    public String getClubName() {
        return mClubName;
    }

    public void setClubName(String mClubName) {
        this.mClubName = mClubName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public boolean isChampionsLeagueWinner(){
        return isChampionsLeagueWinner;
    }

    public void setChampionsLeagueWinner(boolean championsLeagueWinner) {
        isChampionsLeagueWinner = championsLeagueWinner;
    }

    public static ArrayList<Club> createClubList() {
        ArrayList<Club> clubs = new ArrayList<>();
        clubs.add(new Club("AC Milan", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50058.png", true));
        clubs.add(new Club("Ajax AFC", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50143.png", true));
        clubs.add(new Club("AS Roma", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50137.png", false));
        clubs.add(new Club("BV Borussia Dortmund", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52758.png", true));
        clubs.add(new Club("Chelsea FC", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52914.png", true));
        clubs.add(new Club("Club Atlético de Madrid", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50124.png", false));
        clubs.add(new Club("FC Barcelona", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50080.png", true));
        clubs.add(new Club("FC Bayern München", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50037.png", true));
        clubs.add(new Club("FC Porto", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50064.png", true));
        clubs.add(new Club("Feyenoord Rotterdam", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52749.png", true));
        clubs.add(new Club("Juventus FC", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50139.png", true));
        clubs.add(new Club("Manchester City FC", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52919.png", false));
        clubs.add(new Club("Manchester United FC", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52682.png", true));
        clubs.add(new Club("Olympique de Marseille", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52748.png", true));
        clubs.add(new Club("Olympique Lyon", "https://img.uefa.com/imgml/TP/teams/logos/140x140/5312.png", false));
        clubs.add(new Club("Paris Saint-Germain FC", "https://img.uefa.com/imgml/TP/teams/logos/140x140/52747.png", false));
        clubs.add(new Club("PSV Eindhoven", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50062.png", true));
        clubs.add(new Club("Real Madrid CF", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50051.png", true));
        clubs.add(new Club("SL Benfica", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50147.png", true));
        clubs.add(new Club("Sporting CP", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50149.png", false));
        clubs.add(new Club("SSC Napoli", "https://img.uefa.com/imgml/TP/teams/logos/140x140/50136.png", false));
        return clubs;
    }
}
