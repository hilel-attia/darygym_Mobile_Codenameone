package com.daryGym.gui.back.abonnement;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Offre;
import com.daryGym.services.OffreService;
import com.daryGym.utils.Statics;

import java.util.ArrayList;

public class ChooseOffre extends Form {

    Form previousForm;
    Button addBtn;
    Resources theme = UIManager.initFirstTheme("/theme");
    Label titreLabel, descriptionLabel, prixLabel, imageLabel;
    ImageViewer imageIV;
    Button chooseBtn;
    Container btnsContainer;

    public ChooseOffre(Form previous) {
        super("Choisir un offre", new BoxLayout(BoxLayout.Y_AXIS));

        previousForm = previous;
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
        addBtn = new Button("Ajouter offre");
        this.add(addBtn);

        ArrayList<Offre> listOffres = OffreService.getInstance().getAll();
        if (listOffres.size() > 0) {
            for (Offre offres : listOffres) {
                this.add(makeOffreModel(offres));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> new com.daryGym.gui.back.offre.Manage(this, true).show());
    }

    private Component makeOffreModel(Offre offre) {
        Container offreModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        offreModel.setUIID("containerRounded");

        titreLabel = new Label(offre.getTitre());
        titreLabel.setUIID("labelCenter");

        prixLabel = new Label("Prix : " + offre.getPrix());
        prixLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + offre.getDescription());
        descriptionLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + offre.getDescription());
        imageLabel.setUIID("labelDefault");

        if (offre.getImage() != null) {
            String url = Statics.IMAGE_URL + offre.getImage();
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

        chooseBtn = new Button("Choisir");
        chooseBtn.addActionListener(l -> {
            Manage.selectedOffre = offre;
            ((Manage) previousForm).refreshOffre();
            previousForm.showBack();
        });

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        btnsContainer.add(BorderLayout.CENTER, chooseBtn);

        offreModel.addAll(
                titreLabel, prixLabel, descriptionLabel, imageLabel,
                imageIV,
                btnsContainer
        );

        return offreModel;
    }
}