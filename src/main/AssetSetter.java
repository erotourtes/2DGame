package main;

import object.keyObj;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        var objects = gp.getSuperObject();

        objects[0] = new keyObj();
        objects[0].setWorldX(gp.getWorldWidth() / 2);
        objects[0].setWorldY(gp.getWorldHeight() / 2);
    }

}
