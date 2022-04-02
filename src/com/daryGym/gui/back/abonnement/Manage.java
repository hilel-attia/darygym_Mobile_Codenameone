package com.daryGym.gui.back.abonnement;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.entities.Abonnement;
import com.daryGym.entities.Offre;
import com.daryGym.services.AbonnementService;

public class Manage extends Form {

    public static Offre selectedOffre;
    Abonnement currentAbonnement;

    Label dureeLabel, offreLabel, selectedOffreLabel;
    TextField dureeTF;
    Button selectOffreButton, manageButton;

    Form previous;

    public Manage(Form previous) {
        super(DisplayAll.currentAbonnement == null ? /**/"Ajouter" :  /**/"Modifier l' abonnement", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        selectedOffre = null;
        currentAbonnement = DisplayAll.currentAbonnement;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refreshOffre() {
        selectedOffreLabel.setText(selectedOffre.getTitre());
        selectOffreButton.setText("Modifier le offre");
        this.revalidate();
    }

    private void addGUIs() {

        dureeLabel = new Label("Duree par mois  : ");
        dureeLabel.setUIID("labelDefault");
        dureeTF = new TextField();
        dureeTF.setHint("Tapez la duree de l'abonnement");

        if (currentAbonnement == null) {
            manageButton = new Button("Ajouter");
        } else {

            dureeTF.setText(currentAbonnement.getDuree());
            selectedOffre = currentAbonnement.getOffre();

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonMain");

        offreLabel = new Label("Offre : ");
        offreLabel.setUIID("labelDefault");
        if (selectedOffre != null) {
            selectedOffreLabel = new Label(selectedOffre.getTitre());
            selectOffreButton = new Button("Modifier le offre");
        } else {
            selectedOffreLabel = new Label("Aucune offre selectionné");
            selectOffreButton = new Button("Choisir une offre");
        }

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                dureeLabel, dureeTF,
                offreLabel, selectedOffreLabel, selectOffreButton,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        selectOffreButton.addActionListener(l -> new ChooseOffre(this).show());

        if (currentAbonnement == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = AbonnementService.getInstance().add(
                            new Abonnement(
                                    dureeTF.getText(),
                                    selectedOffre
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Abonnement ajouté avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de abonnement. Code d'erreur : " + responseCode, new Command("Ok"));
                    }

                    showBackAndRefresh();
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = AbonnementService.getInstance().edit(
                            new Abonnement(
                                    currentAbonnement.getId(),
                                    dureeTF.getText(),
                                    selectedOffre
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Abonnement modifié avec succes", new Command("Ok"));
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de abonnement. Code d'erreur : " + responseCode, new Command("Ok"));
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

        if (dureeTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir le titleAttributebre", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(dureeTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", dureeTF.getText() + " n'est pas un nombre valide", new Command("Ok"));
            return false;
        }

        if (selectedOffre == null) {
            Dialog.show("Avertissement", "Veuillez choisir une offre", new Command("Ok"));
            return false;
        }

        return true;
    }
}