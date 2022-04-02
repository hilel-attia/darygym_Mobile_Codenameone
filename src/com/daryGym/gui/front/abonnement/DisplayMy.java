package com.daryGym.gui.front.abonnement;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.daryGym.entities.Abonnement;
import com.daryGym.services.AbonnementService;

import java.util.ArrayList;

public class DisplayMy extends Form {

    public static DisplayMy instance = null;
    Label dureeLabel, offreLabel;
    Container btnsContainer;

    public DisplayMy(Form previous) {
        super("Abonnements", new BoxLayout(BoxLayout.Y_AXIS));

        instance = this;
        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.revalidate();
    }

    private void addGUIs() {
        ArrayList<Abonnement> listAbonnements = AbonnementService.getInstance().getAll();

        if (listAbonnements.size() > 0) {
            for (Abonnement listAbonnement : listAbonnements) {
                Component model = makeAbonnementModel(listAbonnement);

                this.add(model);

            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeAbonnementModel(Abonnement abonnement) {
        Container abonnementModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        abonnementModel.setUIID("containerRounded");

        dureeLabel = new Label("Durée par mois : " + abonnement.getDuree());
        dureeLabel.setUIID("labelDefault");

        offreLabel = new Label("Offre : " + abonnement.getOffre().getTitre());
        offreLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        abonnementModel.addAll(
                offreLabel,
                dureeLabel
        );

        return abonnementModel;
    }
}