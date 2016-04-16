package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.Entity;
public class MyClass {

    public static void main(String [] args){
        Schema schema = new Schema(1,"com.my.mirror.greendao");
        Entity classiFied = schema.addEntity("ClassiFied");
        classiFied.addIdProperty().primaryKey().autoincrement();
        classiFied.addStringProperty("title");
        classiFied.addStringProperty("categoryId");

        Entity reUse = schema.addEntity("ReUse");
        reUse.addIdProperty().primaryKey().autoincrement();
        reUse.addStringProperty("title");
        reUse.addStringProperty("img");
        reUse.addStringProperty("name");
        reUse.addStringProperty("price");
        reUse.addStringProperty("area");
        reUse.addStringProperty("brand");
        reUse.addStringProperty("typeId");

        Entity specialShare = schema.addEntity("SpecialShare");
        specialShare.addIdProperty().primaryKey().autoincrement();
        specialShare.addStringProperty("title");
        specialShare.addStringProperty("img");

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
