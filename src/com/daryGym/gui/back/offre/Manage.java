package com.daryGym.gui.back.offre;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Offre;
import com.daryGym.gui.back.abonnement.ChooseOffre;
import com.daryGym.services.OffreService;
import com.daryGym.utils.Statics;

import java.io.IOException;

public class Manage extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");

    boolean imageEdited = false;
    boolean isChoose;

    Offre currentOffre;
    String selectedImage;

    Label titreLabel, descriptionLabel, prixLabel, imageLabel;
    TextField titreTF, prixTF;
    TextArea descriptionTF;
    ImageViewer imageIV;
    Button selectImageButton, manageButton;

    Form previous;

    public Manage(Form previous, boolean isChoose) {
        super(DisplayAll.currentOffre == null ? "Ajouter" : /**/ "Modifier l' offre", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;
        this.isChoose = isChoose;

        currentOffre = DisplayAll.currentOffre;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        titreLabel = new Label("Titre : ");
        titreLabel.setUIID("labelDefault");
        titreTF = new TextField();
        titreTF.setHint("Tapez le titre de l'offre");

        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextArea();
        descriptionTF.setHint("Tapez la description de l'offre");

        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix de l'offre");

        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentOffre == null) {
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            manageButton = new Button("Ajouter");
        } else {
            titreTF.setText(currentOffre.getTitre());
            prixTF.setText(String.valueOf(currentOffre.getPrix()));
            descriptionTF.setText(currentOffre.getDescription());

            if (currentOffre.getImage() != null) {
                String url = Statics.IMAGE_URL + currentOffre.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("loading.gif").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);

            selectImageButton.setText("Modifier l'image");
            selectedImage = currentOffre.getImage();

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                titreLabel, titreTF,
                descriptionLabel, descriptionTF,
                prixLabel, prixTF,
                imageLabel, imageIV, selectImageButton,
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

        if (currentOffre == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = OffreService.getInstance().add(
                            new Offre(
                                    titreTF.getText(),
                                    descriptionTF.getText(),
                                    Float.parseFloat(prixTF.getText()),
                                    selectedImage
                            )
                    );
                    if (responseCode == 200) {

                        Dialog.show("Succés", "Offre ajouté avec succes", new Command("Ok"));
                        new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                        displayWelcomeMessage();
                                    }
                                },
                                3000
                        );
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de offre. Code d'erreur : " + responseCode, new Command("Ok"));
                    }

                    showBackAndRefresh();
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = OffreService.getInstance().edit(
                            new Offre(
                                    currentOffre.getId(),
                                    titreTF.getText(),
                                    descriptionTF.getText(),
                                    Float.parseFloat(prixTF.getText()),
                                    selectedImage
                            ),
                            imageEdited
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Offre modifié avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de offre. Code d'erreur : " + responseCode, new Command("Ok"));
                    }

                    showBackAndRefresh();
                }
            });
        }
    }

    private void displayWelcomeMessage() {
        ToastBar.getInstance().setPosition(TOP);
        ToastBar.Status status = ToastBar.getInstance().createStatus();
        status.setShowProgressIndicator(false);
        status.setMessage("Offre ajouté avec succes ");
        status.setExpires(5000);
        status.show();
    }

    private void showBackAndRefresh() {
        if (isChoose) {
            ((ChooseOffre) previous).refresh();
            previous.showBack();
        } else {
            ((DisplayAll) previous).refresh();
            previous.showBack();
        }
    }

    private boolean controleDeSaisie() {

        if (titreTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le titre", new Command("Ok"));
            return false;
        }

        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir la description", new Command("Ok"));
            return false;
        }

        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le prix", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTF.getText() + " n'est pas un prix valide", new Command("Ok"));
            return false;
        }

        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }
        return true;
    }
}
