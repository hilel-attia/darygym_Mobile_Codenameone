package com.daryGym.gui.back.exercice;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Exercice;
import com.daryGym.entities.Exercicecategorie;
import com.daryGym.services.ExerciceService;
import com.daryGym.utils.Statics;

import java.io.IOException;

public class Manage extends Form {


    public static Exercicecategorie selectedExercicecategorie;
    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;
    Exercice currentExercice;
    Label imageLabel, nomLabel, descriptionLabel, videoLabel, docsLabel, exercicecategorieLabel;
    TextField
            imageTF,
            nomTF,
            descriptionTF,
            videoTF,
            docsTF,
            exercicecategorieTF, elemTF;


    Label selectedExercicecategorieLabel;
    Button selectExercicecategorieButton;


    ImageViewer imageIV;
    Button selectImageButton;

    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(DisplayAll.currentExercice == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;


        selectedExercicecategorie = null;

        currentExercice = DisplayAll.currentExercice;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }


    public void refreshExercicecategorie() {
        selectedExercicecategorieLabel.setText(selectedExercicecategorie.getLibelle());
        selectExercicecategorieButton.setText("Modifier le exercicecategorie");
        this.revalidate();
    }


    private void addGUIs() {


        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");

        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");

        videoLabel = new Label("Video : ");
        videoLabel.setUIID("labelDefault");
        videoTF = new TextField();
        videoTF.setHint("Tapez le video");

        docsLabel = new Label("Docs : ");
        docsLabel.setUIID("labelDefault");
        docsTF = new TextField();
        docsTF.setHint("Tapez le docs");

        exercicecategorieLabel = new Label("Exercicecategorie : ");
        exercicecategorieLabel.setUIID("labelDefault");
        exercicecategorieTF = new TextField();
        exercicecategorieTF.setHint("Tapez le exercicecategorie");


        exercicecategorieLabel = new Label("exercicecategorie : ");
        exercicecategorieLabel.setUIID("labelDefault");
        selectedExercicecategorieLabel = new Label("null");
        selectExercicecategorieButton = new Button("Choisir exercicecategorie");
        selectExercicecategorieButton.addActionListener(l -> new ChooseExercicecategorie(this).show());


        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentExercice == null) {

            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));


            manageButton = new Button("Ajouter");
        } else {


            nomTF.setText(currentExercice.getNom());
            descriptionTF.setText(currentExercice.getDescription());
            videoTF.setText(currentExercice.getVideo());
            docsTF.setText(currentExercice.getDocs());

            selectedExercicecategorie = currentExercice.getExercicecategorie();

            exercicecategorieLabel = new Label("exercicecategorie : ");
            exercicecategorieLabel.setUIID("labelDefault");
            selectedExercicecategorieLabel = new Label("null");
            selectedExercicecategorieLabel.setText(selectedExercicecategorie.getLibelle());
            selectExercicecategorieButton.setText("Modifier le exercicecategorie");


            if (currentExercice.getImage() != null) {
                selectedImage = currentExercice.getImage();
                String url = Statics.EXERCICE_IMAGE_URL + currentExercice.getImage();
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

                nomLabel, nomTF,
                descriptionLabel, descriptionTF,
                videoLabel, videoTF,
                docsLabel, docsTF,
                exercicecategorieLabel,
                selectedExercicecategorieLabel, selectExercicecategorieButton,
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

        if (currentExercice == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ExerciceService.getInstance().add(
                            new Exercice(


                                    selectedImage,
                                    nomTF.getText(),
                                    descriptionTF.getText(),
                                    videoTF.getText(),
                                    docsTF.getText(),
                                    selectedExercicecategorie
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
                                        status.setMessage("Exercice ajouté avec succes");
                                        status.setExpires(5000);
                                        status.show();
                                    }
                                },
                                2000
                        );
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de exercice. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ExerciceService.getInstance().edit(
                            new Exercice(
                                    currentExercice.getId(),


                                    selectedImage,
                                    nomTF.getText(),
                                    descriptionTF.getText(),
                                    videoTF.getText(),
                                    docsTF.getText(),
                                    selectedExercicecategorie

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Exercice modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de exercice. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le nom", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le description", new Command("Ok"));
            return false;
        }


        if (videoTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le video", new Command("Ok"));
            return false;
        }


        if (docsTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le docs", new Command("Ok"));
            return false;
        }


        if (selectedExercicecategorie == null) {
            Dialog.show("Avertissement", "Veuillez choisir un exercicecategorie", new Command("Ok"));
            return false;
        }


        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }


        return true;
    }
}