package com.daryGym.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.daryGym.entities.User;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {

    public static UserService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<User> listUsers;

    private UserService() {
        cr = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    User user;
    public User verifierMotDePasse(String email, String password) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/user/verif");
        cr.setHttpMethod("POST");
        cr.addArgument("email", email);
        cr.addArgument("password", password);

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    user = getOne();
                } else {
                    user = null;
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

        return user;
    }

    public ArrayList<User> getAll() {
        listUsers = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/user");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listUsers = getList();
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

        return listUsers;
    }

    private ArrayList<User> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                User user = new User(
                        (String) obj.get("username"),
                        (String) obj.get("nomComplet"),
                        (String) obj.get("email"),
                        Boolean.parseBoolean(obj.get("valid").toString()),
                        Boolean.parseBoolean(obj.get("deleted").toString()),
                        (String) obj.get("password"),
                        Boolean.parseBoolean(obj.get("admin").toString())
                );

                listUsers.add(user);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listUsers;
    }

    private User getOne() {
        try {
            Map<String, Object> obj = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));

            return new User(
                    (String) obj.get("username"),
                    (String) obj.get("nomComplet"),
                    (String) obj.get("email"),
                    Boolean.parseBoolean(obj.get("valid").toString()),
                    Boolean.parseBoolean(obj.get("deleted").toString()),
                    (String) obj.get("password"),
                    Boolean.parseBoolean(obj.get("admin").toString())
            );

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int add(User user) {
        return manage(user, false);
    }

    public int edit(User user) {
        return manage(user, true);
    }

    public int manage(User user, boolean isEdit) {
        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/user/edit");
            cr.addArgument("id", String.valueOf(user.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/user/add");
        }

        cr.addArgument("username", user.getUsername());
        cr.addArgument("nomComplet", user.getNomComplet());
        cr.addArgument("email", user.getEmail());
        cr.addArgument("valid", String.valueOf(user.isValid()));
        cr.addArgument("deleted", String.valueOf(user.isDeleted()));
        cr.addArgument("password", user.getPassword());
        cr.addArgument("admin", String.valueOf(user.isAdmin()));

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

    public int delete(int userId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/user/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(userId));

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
