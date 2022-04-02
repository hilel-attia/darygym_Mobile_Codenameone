/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daryGym.services;

import com.daryGym.entities.Reservation;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;

import com.codename1.ui.events.ActionListener;
import com.daryGym.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
;

/**
 *
 * @author ASUS
 */
public class reservationService {
public ArrayList<Reservation> reclamations;
        public static reservationService instance;
    private ConnectionRequest req;
    public boolean resultOk;
     public static final String ACCOUNT_SID = "AC33b39c5caaff92544ba4ff7f9e53c7d9";
    public static final String AUTH_TOKEN = "6ff460dee94c2cf1941a247d4b0a3233";
    private String Body;
    
    public reservationService(){
    
               req = new ConnectionRequest();
               
    }

    public static reservationService getInstance() {
        if (instance == null) {
            instance = new reservationService();
        }
        return instance;
    }
        
        public boolean addReservation(Reservation r) {
        String url = Statics.BASE_URL1 + "/fr/ws/reserver?" + "namepart=" + r.getNamepart()+ "&usernamepart=" + r.getUsernamepart()+ "&ville=" + r.getVille()+ "&numtelephonepart=" + r.getNumtelephonepart()+ "&email_participent=" + r.getEmail_participent(); //création de l'URL
             req=new ConnectionRequest(url);
             req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
               req.removeResponseListener(this);  
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
    
        public ArrayList<Reservation> parseReclamations(String jsonText){
            System.out.println("el jsontext: " + jsonText);
        try {
            reclamations=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> formationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              
            List<Map<String,Object>> list = (List<Map<String,Object>>)formationsListJson.get("root");
                
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Reservation f = new Reservation();
                float id = Float.parseFloat(obj.get("id").toString());
                f.setId((int)id);
                f.setNamepart(obj.get("namepart").toString());
                f.setUsernamepart(obj.get("usernamepart").toString());
                f.setVille(obj.get("ville").toString());
                f.setNumtelephonepart(obj.get("numtelephonepart").toString());
                f.setEmail_participent(obj.get("EmailParticipent").toString());
               
                //Ajouter la tâche extraite de la réponse Json à la liste
                reclamations.add(f);
            }
               
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return reclamations;
        
        }
    public ArrayList<Reservation> getAllReservations(){
        String url = Statics.BASE_URL1+"/fr/ws/listparticipants";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }
    
   /* public ArrayList<Reclamation> searchFormations(String titre){
        String url = Statics.BASE_URL+"/formationf/getFormations?titre="+ titre;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                formations = parseFormations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formations;
    }*/
    
   
//    public boolean DeleteFormation(Formation f) {
//        
//       String url = Statics.BASE_URL +"/supp?id="+f.getId();               
//        System.out.println(url);               
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOk;
//    }
//    public boolean EditCour(Formation f , int id ) {
//        
//        
//       String url = Statics.BASE_URL +"/mod?id="+id+ "&titre=" + f.getTitre()+ "&description=" + f.getDescription()+ "&datedebut=" + f.getDateDebut()
//               + "&datefin=" + f.getDateFin()+ "&prix=" + f.getPrix();
//        System.out.println(url);
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOk;
//    }
//      public String mesFormation() {
//        StringBuilder stmsg = new StringBuilder();
//        for (Formation ff : formations) {
//            stmsg.append("\n titre: " + ff.getTitre()+ "\n dsec : " + ff.getDescription()+ "\n dATED : " + ff.getDateDebut()+ "\n dATEF : " + ff.getDateFin()+ "\n dATED : " + ff.getDateDebut()+ "\n prix : " + ff.getPrix()+"\n");
//        }
//        return stmsg.toString();
//    }
   /* public boolean message() {

        Body = "<h1>Hello " + "!</h1>";
        
        String url = Statics.BASE_URL + "/formationf/all";
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                        Formation f = new Formation();

                String response = new String(req.getResponseData());
                System.out.println(response);

                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
//             
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                Message message = Message.creator(new PhoneNumber("+216 50 032 402"),
                        new PhoneNumber("+12143076376"),
                        "Les details de la formations sont : " + f).create();
                System.out.println(message.getSid());

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;

    }*/
    
    public void supprimerReservation(int id) {
        String Url = Statics.BASE_URL1 +"/fr/ws/supprimerReservation/"
               +id;
        req.setUrl(Url);
        req.setPost(true);

        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println(str);
            System.out.println("supr ok");


        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    }