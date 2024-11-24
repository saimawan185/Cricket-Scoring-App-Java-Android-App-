package com.example.cricketscoringapp;

class PlayerModel {
    private String name;
    private boolean isBaller;

    public PlayerModel(String name, boolean isBaller) {
        this.name = name;
        this.isBaller = isBaller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBaller() {
        return isBaller;
    }

    public void setBaller(boolean baller) {
        isBaller = baller;
    }
}
