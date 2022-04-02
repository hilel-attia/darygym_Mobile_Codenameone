package com.daryGym.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.entities.User;
import com.daryGym.services.UserService;

public class RegisterForm extends Form {

    public RegisterForm() {
        super("Inscription", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        addActions();
    }

    Label fullnameLabel, emailLabel, usernameLabel, passwordLabel, passwordConfirmationLabel;
    TextField fullnameTF, emailTF, usernameTF, passwordTF, passwordConfirmationTF;
    Label loginLabel;
    CheckBox isAdminTF;
    Button registerButton, loginButton;

    private void addGUIs() {

        fullnameLabel = new Label("Nom complet : ");
        fullnameTF = new TextField();
        fullnameTF.setHint("Tapez le nom complet");

        emailLabel = new Label("Email : ");
        emailTF = new TextField();
        emailTF.setHint("Tapez l'email");

        usernameLabel = new Label("Username : ");
        usernameLabel.setUIID("labelDefault");
        usernameTF = new TextField();
        usernameTF.setHint("Tapez le username");

        passwordLabel = new Label("Mot de passe : ");
        passwordLabel.setUIID("labelDefault");
        passwordTF = new TextField("", "Tapez votre mot de passe", 20, TextField.PASSWORD);

        passwordConfirmationLabel = new Label("Confirmation du mot de passe : ");
        passwordConfirmationLabel.setUIID("labelDefault");
        passwordConfirmationTF = new TextField("", "Retapez votre mot de passe", 20, TextField.PASSWORD);

        isAdminTF = new CheckBox("Admin : ");

        registerButton = new Button("S'inscrire");
        registerButton.setUIID("buttonMain");

        loginLabel = new Label("Vous avez deja un compte ?");
        loginLabel.setUIID("labelCenterSmall");
        loginButton = new Button("Connexion");

        this.addAll(
                fullnameLabel, fullnameTF,
                emailLabel, emailTF,
                usernameLabel, usernameTF,
                passwordLabel, passwordTF,
                passwordConfirmationLabel, passwordConfirmationTF,
                isAdminTF,
                registerButton,
                loginLabel, loginButton
        );
    }

    private void addActions() {
        registerButton.addActionListener(l -> {
            if (controleDeSaisie()) {
                int responseCode = UserService.getInstance().add(
                        new User(
                                usernameTF.getText(),
                                fullnameTF.getText(),
                                emailTF.getText(),
                                true,
                                false,
                                passwordTF.getText(),
                                isAdminTF.isSelected()
                        )
                );
                if (responseCode == 200) {
                    Dialog.show("Succés", "Inscription effectué avec succes", new Command("Ok"));
                    LoginForm.loginForm.showBack();
                } else if (responseCode == 203) {
                    Dialog.show("Erreur", "Cet email existe deja ", new Command("Ok"));
                } else {
                    Dialog.show("Erreur", "Erreur d'ajout de user. Code d'erreur : " + responseCode, new Command("Ok"));
                    LoginForm.loginForm.showBack();
                }
            }
        });

        loginButton.addActionListener(l -> LoginForm.loginForm.showBack());
    }

    private boolean controleDeSaisie() {

        if (fullnameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le nom complet", new Command("Ok"));
            return false;
        }

        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir l'email", new Command("Ok"));
            return false;
        }

        if (usernameTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le username", new Command("Ok"));
            return false;
        }

        if (passwordTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le mot de passe", new Command("Ok"));
            return false;
        }

        if (passwordConfirmationLabel.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le role", new Command("Ok"));
            return false;
        }

        if (!passwordConfirmationTF.getText().equals(passwordTF.getText())) {
            Dialog.show("Avertissement", "Mot de passe et confirmation doivent etre identiques", new Command("Ok"));
            return false;
        }

        return true;
    }
}
