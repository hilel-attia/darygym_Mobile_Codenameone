package com.daryGym.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.daryGym.entities.Abonnement;
import com.daryGym.entities.Offre;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbonnementService {

    public static AbonnementService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Abonnement> listAbonnements;

    private AbonnementService() {
        cr = new ConnectionRequest();
    }

    public static AbonnementService getInstance() {
        if (instance == null) {
            instance = new AbonnementService();
        }
        return instance;
    }

    public ArrayList<Abonnement> getAll() {
        listAbonnements = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/abonnement");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listAbonnements = getList();
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

        return listAbonnements;
    }

    private ArrayList<Abonnement> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Abonnement abonnement = new Abonnement(
                         (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("duree"),
                        makeOffre((Map<String, Object>) obj.get("offre"))
                );

                listAbonnements.add(abonnement);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listAbonnements;
    }

    public Offre makeOffre(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }

        return new Offre(
                (int) Float.parseFloat(obj.get("id").toString()),
                (String) obj.get("titre"),
                (String) obj.get("description"),
                Float.parseFloat(obj.get("prix").toString()),
                (String) obj.get("image")
        );
    }

    public int add(Abonnement abonnement) {
        return manage(abonnement, false);
    }

    public int edit(Abonnement abonnement) {
        return manage(abonnement, true);
    }

    public int manage(Abonnement abonnement, boolean isEdit) {
        cr = new ConnectionRequest();
                
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/abonnement/edit");
            cr.addArgument("id", String.valueOf(abonnement.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/abonnement/add");
        }
        
        cr.setHttpMethod("POST");
        cr.setPost(true);
        
        System.out.println(abonnement.getOffre());
        cr.addArgument("duree", abonnement.getDuree());
        cr.addArgument("offre", String.valueOf(abonnement.getOffre().getId()));

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

    public int delete(int abonnementId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/abonnement/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(abonnementId));

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
