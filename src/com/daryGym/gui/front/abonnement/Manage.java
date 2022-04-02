package com.daryGym.gui.front.abonnement;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.entities.Abonnement;
import com.daryGym.entities.Offre;
import com.daryGym.gui.front.offre.DisplayAll;
import com.daryGym.services.AbonnementService;

public class Manage extends Form {

    Offre selectedOffre;

    Label dureeLabel, offreLabel, selectedOffreLabel;
    TextField dureeTF;
    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super("S'abonner");
        this.previous = previous;

        selectedOffre = DisplayAll.currentOffre;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        dureeLabel = new Label("Duree par mois : ");
        dureeLabel.setUIID("labelDefault");
        dureeTF = new TextField();
        dureeTF.setHint("Tapez la duree de l'abonnement");

        manageButton = new Button("Ajouter");
        manageButton.setUIID("buttonMain");

        offreLabel = new Label("Offre choisi : ");
        offreLabel.setUIID("labelDefault");

        selectedOffreLabel = new Label(selectedOffre.getTitre());

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                dureeLabel, dureeTF,
                offreLabel, selectedOffreLabel,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
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
    }

    private void showBackAndRefresh() {
        DisplayMy.instance.refresh();
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