package com.daryGym.gui.back.user;

import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.entities.User;
import com.daryGym.services.UserService;

import java.util.ArrayList;

public class DisplayAll extends Form {

    public static User currentUser = null;
    Button addBtn;
    Label emailLabel, usernameLabel, passwordLabel;
    SpanLabel passwordSpanLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;


    public DisplayAll(Form previous) {
        super("Users", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.revalidate();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter user");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);

        ArrayList<User> listUsers = UserService.getInstance().getAll();
        if (listUsers.size() > 0) {
            for (User listUser : listUsers) {
                this.add(makeUserModel(listUser));
            }
        } else {
            this.add(new Label("Aucun utilisateur"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentUser = null;
            new Manage(this).show();
        });
    }

    private Component makeUserModel(User user) {
        Container userModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        userModel.setUIID("containerRounded");

        emailLabel = new Label("Email : " + user.getEmail());
        emailLabel.setUIID("labelDefault");

        usernameLabel = new Label("Username : " + user.getUsername());
        usernameLabel.setUIID("labelDefault");

        passwordLabel = new Label("Mot de passe : ");
        passwordLabel.setUIID("labelDefault");
        passwordSpanLabel = new SpanLabel(user.getPassword());
        passwordSpanLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.addActionListener(action -> {
            currentUser = user;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce user ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = UserService.getInstance().delete(user.getId());

                if (responseCode == 200) {
                    currentUser = null;
                    dlg.dispose();
                    userModel.remove();
                    this.revalidate();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du user. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        userModel.addAll(
                emailLabel, usernameLabel, passwordLabel, passwordSpanLabel,
                btnsContainer
        );

        return userModel;
    }
}