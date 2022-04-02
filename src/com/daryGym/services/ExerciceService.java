package com.daryGym.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.daryGym.entities.Exercice;
import com.daryGym.entities.Exercicecategorie;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciceService {

    public static ExerciceService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Exercice> listExercices;

    private ExerciceService() {
        cr = new ConnectionRequest();
    }

    public static ExerciceService getInstance() {
        if (instance == null) {
            instance = new ExerciceService();
        }
        return instance;
    }

    public ArrayList<Exercice> getAll() {
        listExercices = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/exercice");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listExercices = getList();
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

        return listExercices;
    }

    private ArrayList<Exercice> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Exercice exercice = new Exercice(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        (String) obj.get("image"),
                        (String) obj.get("nom"),
                        (String) obj.get("description"),
                        (String) obj.get("video"),
                        (String) obj.get("docs"),
                        makeExercicecategorie((Map<String, Object>) obj.get("exercicecategorie"))
                        
                );

                listExercices.add(exercice);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listExercices;
    }

    
    public Exercicecategorie makeExercicecategorie(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        return new Exercicecategorie(
                (int) Float.parseFloat(obj.get("id").toString()),

                (String) obj.get("libelle"),
                (String) obj.get("image")

        );
    }
    

    public int add(Exercice exercice) {
        return manage(exercice, false, true);
    }

    public int edit(Exercice exercice, boolean imageEdited) {
        return manage(exercice, true , imageEdited);
    }

    public int manage(Exercice exercice, boolean isEdit, boolean imageEdited) {
        
        MultipartRequest cr = new MultipartRequest();
        cr.setFilename("file", "Exercice.jpg");

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/exercice/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(exercice.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/exercice/add");
        }

        if (imageEdited) {
            try {
                cr.addData("file", exercice.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgumentNoEncoding("image", exercice.getImage());
        }

        cr.addArgumentNoEncoding("image", exercice.getImage());cr.addArgumentNoEncoding("nom", exercice.getNom());cr.addArgumentNoEncoding("description", exercice.getDescription());cr.addArgumentNoEncoding("video", exercice.getVideo());cr.addArgumentNoEncoding("docs", exercice.getDocs());cr.addArgumentNoEncoding("exercicecategorie", String.valueOf(exercice.getExercicecategorie().getId()));

        
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

    public int delete(int exerciceId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/exercice/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(exerciceId));

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
