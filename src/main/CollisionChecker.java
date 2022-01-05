package main;

import entity.Entity;

public class CollisionChecker {
    private GamePanel gp;
    private Entity entity;

    private int entityTop, entityBottom, entityRight, entityLeft;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        this.entity = entity;
        var solidArea = entity.getSolidArea();

        int entityLeftWorldX = entity.getWorldX() + solidArea.x;
        int entityRightWorldX = entityLeftWorldX + solidArea.width;
        int entityTopWorldY = entity.getWorldY() + solidArea.y;
        int entityBottomWorldY = entityTopWorldY + solidArea.height;

        entityLeft = entityLeftWorldX / gp.tileSize;
        entityRight = entityRightWorldX / gp.tileSize;
        entityTop = entityTopWorldY / gp.tileSize;
        entityBottom = entityBottomWorldY / gp.tileSize;

        switch (entity.getDirection()) {
            case UP -> checkCollisionTop(entityTopWorldY);
            case DOWN -> checkCollisionBottom(entityBottomWorldY);
            case LEFT -> checkCollisionLeft(entityLeftWorldX);
            case RIGHT -> checkCollisionRight(entityRightWorldX);
        }
    }

    private void checkCollisionTop(int entityTopWorldY) {
        entityTop = (entityTopWorldY - entity.getSpeed()) / gp.tileSize;
        int tileNumFirst = gp.getTileManager().getMapArr()[entityTop][entityLeft];
        int tileNumSecond = gp.getTileManager().getMapArr()[entityTop][entityRight];
        if (gp.getTileManager().getTile()[tileNumFirst].getCollision() == true ||
                gp.getTileManager().getTile()[tileNumSecond].getCollision() == true) {
            entity.setCollision(true);
        }
    }

    private void checkCollisionBottom(int entityBottomWorldY) {
        entityBottom = (entityBottomWorldY + entity.getSpeed()) / gp.tileSize;
        int tileNumFirst = gp.getTileManager().getMapArr()[entityBottom][entityLeft];
        int tileNumSecond = gp.getTileManager().getMapArr()[entityBottom][entityRight];
        if (gp.getTileManager().getTile()[tileNumFirst].getCollision() == true ||
                gp.getTileManager().getTile()[tileNumSecond].getCollision() == true) {
            entity.setCollision(true);
        }
    }

    private void checkCollisionRight(int entityRightWorldX) {
        entityRight = (entityRightWorldX - entity.getSpeed()) / gp.tileSize;
        int tileNumFirst = gp.getTileManager().getMapArr()[entityTop][entityRight];
        int tileNumSecond = gp.getTileManager().getMapArr()[entityBottom][entityRight];
        if (gp.getTileManager().getTile()[tileNumFirst].getCollision() == true ||
                gp.getTileManager().getTile()[tileNumSecond].getCollision() == true) {
            entity.setCollision(true);
        }
    }

    private void checkCollisionLeft(int entityLeftWorldX) {
        entityLeft = (entityLeftWorldX + entity.getSpeed()) / gp.tileSize;
        int tileNumFirst = gp.getTileManager().getMapArr()[entityTop][entityLeft];
        int tileNumSecond = gp.getTileManager().getMapArr()[entityBottom][entityLeft];
        if (gp.getTileManager().getTile()[tileNumFirst].getCollision() == true ||
                gp.getTileManager().getTile()[tileNumSecond].getCollision() == true) {
            entity.setCollision(true);
        }
    }
}