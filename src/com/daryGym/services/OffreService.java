package com.daryGym.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.daryGym.entities.Offre;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OffreService {

    public static OffreService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Offre> listOffres;

    private OffreService() {
        cr = new ConnectionRequest();
    }

    public static OffreService getInstance() {
        if (instance == null) {
            instance = new OffreService();
        }
        return instance;
    }

    public ArrayList<Offre> getAll() {
        listOffres = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/offre");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listOffres = getList();
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

        return listOffres;
    }

    private ArrayList<Offre> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Offre offre = new Offre(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("titre"),
                        (String) obj.get("description"),
                        Float.parseFloat(obj.get("prix").toString()),
                        (String) obj.get("image")
                );

                listOffres.add(offre);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listOffres;
    }

    public int add(Offre offre) {
        return manage(offre, false, true);
    }

    public int edit(Offre offre, boolean imageEdited) {
        return manage(offre, true, imageEdited);
    }

    public int manage(Offre offre, boolean isEdit, boolean imageEdited) {
        MultipartRequest cr = new MultipartRequest();
        cr.setHttpMethod("POST");
        cr.setFilename("file", "Offre.jpg");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/offre/edit");
            cr.addArgumentNoEncoding("id", String.valueOf(offre.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/offre/add");
        }

        cr.addArgumentNoEncoding("titre", offre.getTitre());
        cr.addArgumentNoEncoding("description", offre.getDescription());
        cr.addArgumentNoEncoding("prix", String.valueOf(offre.getPrix()));

        if (imageEdited) {
            try {
                cr.addData("file", offre.getImage(), "image/jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cr.addArgument("image", offre.getImage());
        }

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

    public int delete(int offreId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/offre/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(offreId));

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
