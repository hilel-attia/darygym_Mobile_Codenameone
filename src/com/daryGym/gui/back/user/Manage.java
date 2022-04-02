package com.daryGym.gui.back.user;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.entities.User;
import com.daryGym.services.UserService;

public class Manage extends Form {

    User currentUser;

    Label fullnameLabel, emailLabel, usernameLabel, passwordLabel;
    TextField fullnameTF, emailTF, usernameTF, passwordTF;
    CheckBox isAdminTF;
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(DisplayAll.currentUser == null ? "Ajouter utilisateur" : "Modifier utilisateur", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentUser = DisplayAll.currentUser;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

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
        passwordTF = new TextField("", "Tapez le mot de passe", 20, TextField.PASSWORD);

        isAdminTF = new CheckBox("Admin : ");

        if (currentUser == null) {
            manageButton = new Button("Ajouter");
        } else {

            emailTF.setText(currentUser.getEmail());
            usernameTF.setText(currentUser.getUsername());
            passwordTF.setText(currentUser.getPassword());

            manageButton = new Button("Modifier");
        }

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                fullnameLabel, fullnameTF,
                emailLabel, emailTF,
                usernameLabel, usernameTF,
                passwordLabel, passwordTF,
                isAdminTF,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        if (currentUser == null) {
            manageButton.addActionListener(action -> {
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
                        Dialog.show("Succés", "User ajouté avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de user. Code d'erreur : " + responseCode, new Command("Ok"));
                    }

                    showBackAndRefresh();
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = UserService.getInstance().edit(
                            new User(
                                    currentUser.getId(),
                                    usernameTF.getText(),
                                    fullnameTF.getText(),
                                    emailTF.getText(),
                                    true,
                                    false,
                                    passwordTF.getText(),
                                    false
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "User modifié avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de user. Code d'erreur : " + responseCode, new Command("Ok"));
                    }

                    showBackAndRefresh();
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((DisplayAll) previous).refresh();
        previous.showBack();
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

        return true;
    }
}