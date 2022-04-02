package com.daryGym.gui.back.exercicecategorie;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Exercicecategorie;
import com.daryGym.services.ExercicecategorieService;
import com.daryGym.utils.Statics;

import java.io.IOException;

public class Manage extends Form {


    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;


    Exercicecategorie currentExercicecategorie;


    Label libelleLabel, imageLabel;
    TextField
            libelleTF,
            imageTF, elemTF;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(DisplayAll.currentExercicecategorie == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        currentExercicecategorie = DisplayAll.currentExercicecategorie;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    private void addGUIs() {


        libelleLabel = new Label("Libelle : ");
        libelleLabel.setUIID("labelDefault");
        libelleTF = new TextField();
        libelleTF.setHint("Tapez le libelle");


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentExercicecategorie == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {


            libelleTF.setText(currentExercicecategorie.getLibelle());


            if (currentExercicecategorie.getImage() != null) {
                selectedImage = currentExercicecategorie.getImage();
                String url = Statics.EXERCICECATEGORIE_IMAGE_URL + currentExercicecategorie.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                imageLabel, imageIV,
                selectImageButton,
                libelleLabel, libelleTF,


                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });

        if (currentExercicecategorie == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ExercicecategorieService.getInstance().add(
                            new Exercicecategorie(


                                    libelleTF.getText(),
                                    selectedImage
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "", new Command("Ok"));
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        ToastBar.getInstance().setPosition(TOP);
                                        ToastBar.Status status = ToastBar.getInstance().createStatus();
                                        status.setShowProgressIndicator(false);
                                        status.setMessage("Categorie exercice ajouté avec succes");
                                        status.setExpires(5000);
                                        status.show();
                                    }
                                },
                                2000
                        );
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de exercicecategorie. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ExercicecategorieService.getInstance().edit(
                            new Exercicecategorie(
                                    currentExercicecategorie.getId(),


                                    libelleTF.getText(),
                                    selectedImage

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Exercicecategorie modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de exercicecategorie. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((DisplayAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (libelleTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le libelle", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}