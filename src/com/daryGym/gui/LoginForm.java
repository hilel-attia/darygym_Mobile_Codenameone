package com.daryGym.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.MainApp;
import com.daryGym.entities.User;
import com.daryGym.services.UserService;

public class LoginForm extends Form {

    public static Form loginForm;

    public LoginForm() {
        super("Connexion", new BoxLayout(BoxLayout.Y_AXIS));
        loginForm = this;
        addGUIs();
        addActions();
    }

    Label emailLabel, passwordLabel, inscriptionLabel;
    TextField tfEmail, tfPassword;
    Button btnConnexion, btnInscription;
    Button backendBtn;

    private void addGUIs() {

        emailLabel = new Label("Email :");
        tfEmail = new TextField("", "Entrez votre email");

        passwordLabel = new Label("Mot de passe :");
        tfPassword = new TextField("", "Tapez votre mot de passe", 20, TextField.PASSWORD);

        btnConnexion = new Button("Connexion");
        btnConnexion.setUIID("buttonMain");

        inscriptionLabel = new Label("Besoin d'un compte ?");
        btnInscription = new Button("Inscription");
        backendBtn = new Button("Espace admin");

        this.addAll(
                emailLabel, tfEmail,
                passwordLabel, tfPassword,
                btnConnexion, inscriptionLabel, btnInscription,
                backendBtn
        );
    }

    private void addActions() {
        btnConnexion.addActionListener(action -> {
            User user = UserService.getInstance().verifierMotDePasse(tfEmail.getText(), tfPassword.getText());
            if (user != null) {
                login(user);
            } else {
                Dialog.show("Erreur", "Identifiants invalides", new Command("Ok"));
            }
        });

        btnInscription.addActionListener(action -> new RegisterForm().show());
        backendBtn.addActionListener(action -> {
            login(new User(
                    0,
                    "Default admin",
                    "Admin",
                    "Default admin",
                    true,
                    false,
                    "admin",
                    true
            ));
        });
    }

    private void login(User user) {
        MainApp.setSession(user);

        if (user.isAdmin()) {
            new com.daryGym.gui.back.AccueilBack().show();
        } else {
            new com.daryGym.gui.front.AccueilFront().show();
        }
    }
}
