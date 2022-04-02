package com.daryGym.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.daryGym.entities.Exercicecategorie;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExercicecategorieService {

    public static ExercicecategorieService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Exercicecategorie> listExercicecategories;

    private ExercicecategorieService() {
        cr = new ConnectionRequest();
    }

    public static ExercicecategorieService getInstance() {
        if (instance == null) {
            instance = new ExercicecategorieService();
        }
        return instance;
    }

    public ArrayList<Exercicecategorie> getAll() {
        listExercicecategories = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/exercicecategorie");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listExercicecategories = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listExercicecategories;
    }

    private ArrayList<Exercicecategorie> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Exercicecategorie exercicecategorie = new Exercicecategorie(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        (String) obj.get("libelle"),
                        (String) obj.get("image")
                        
                );

                listExercicecategories.add(exercicecategorie);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listExercicecategories;
    }

    

    public int add(Exercicecategorie exercicecategorie) {
        return manage(exercicecategorie, false, true);
    }

    public int edit(Exercicecategorie exercicecategorie, boolean imageEdited) {
        return manage(exercicecategorie, true , imageEdited);
    }

    public int manage(Exercicecategorie exercicecategorie, boolean isEdit, boolean imageEdited) {
        
        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Exercicecategorie.jpg");

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/exercicecategorie/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(exercicecategorie.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/exercicecategorie/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", exercicecategorie.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", exercicecategorie.getImage());
        }

        cr.addArgumentNoEncoding("libelle", exercicecategorie.getLibelle());cr.addArgumentNoEncoding("image", exercicecategorie.getImage());

        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int exercicecategorieId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/exercicecategorie/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(exercicecategorieId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
