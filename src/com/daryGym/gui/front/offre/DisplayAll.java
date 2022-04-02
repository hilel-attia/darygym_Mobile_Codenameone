package com.daryGym.gui.front.offre;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Offre;
import com.daryGym.gui.front.AccueilFront;
import com.daryGym.gui.front.abonnement.Manage;
import com.daryGym.services.OffreService;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class DisplayAll extends Form {

    public static Offre currentOffre = null;
    Resources theme = UIManager.initFirstTheme("/theme");
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label titreLabel, descriptionLabel, prixLabel, imageLabel;
    ImageViewer imageIV;
    Button subscribeBtn;
    Container btnsContainer;
    public DisplayAll() {
        super("Offres", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();

        super.getToolbar().hideToolbar();
    }

    private void addGUIs() {
        ArrayList<Offre> listOffres = OffreService.getInstance().getAll();

        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher une Offre");
        searchTF.addDataChangedListener((d, t) -> {
            int size = componentModels.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    this.removeComponent(componentModels.get(i));
                    System.out.println(i);
                }
            }
            componentModels = new ArrayList<>();
            for (int i = 0; i < listOffres.size(); i++) {
                if (listOffres.get(i).getTitre().startsWith(searchTF.getText())) {
                    System.out.println(listOffres.get(i).getTitre());
                    Component model = makeOffreModel(listOffres.get(i));
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);

        if (listOffres.size() > 0) {
            for (int i = 0; i < listOffres.size(); i++) {
                Component model = makeOffreModel(listOffres.get(i));
                componentModels.add(model);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeOffreModel(Offre offre) {
        Container offreModel = makeModelWithoutButtons(offre);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        subscribeBtn = new Button("S'abonner");
        subscribeBtn.setUIID("buttonMain");
        subscribeBtn.addActionListener(action -> {
            currentOffre = offre;
            new Manage(AccueilFront.accueilFrontForm).show();
        });

        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(offre));

        btnsContainer.add(BorderLayout.WEST, subscribeBtn);
        btnsContainer.add(BorderLayout.EAST, btnAfficherScreenshot);

        offreModel.add(btnsContainer);
        return offreModel;
    }

    private Container makeModelWithoutButtons(Offre offre) {
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

        offreModel.addAll(
                titreLabel, prixLabel, descriptionLabel, imageLabel,
                imageIV
        );

        return offreModel;
    }

    private void share(Offre offre) {
        Form form = new Form();
        form.add(new Label("Offre " + offre.getTitre()));
        form.add(makeModelWithoutButtons(offre));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                Display.getInstance().getDisplayWidth(),
                Display.getInstance().getDisplayHeight()
        );
        form.revalidate();
        form.setVisible(true);
        form.paintComponent(screenshot.getGraphics(), true);
        form.removeAll();
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        Form screenShotForm = new Form("Partager le offre", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(offre.getDescription());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> AccueilFront.accueilFrontForm.showBack());
        screenShotForm.show();
        // FIN API PARTAGE
    }
}
