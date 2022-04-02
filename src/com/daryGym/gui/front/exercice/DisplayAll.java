package com.daryGym.gui.front.exercice;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Exercice;
import com.daryGym.services.ExerciceService;
import com.daryGym.utils.Statics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class DisplayAll extends Form {


    public static String exerciceCompareVar;
    public static Exercice currentExercice = null;
    Resources theme = UIManager.initFirstTheme("/theme");
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label imageLabel, nomLabel, descriptionLabel, videoLabel, docsLabel, exercicecategorieLabel;
    ImageViewer imageIV;
    PickerComponent sortPicker;
    Container btnsContainer;

    public DisplayAll(Form previous) {
        super("Exercices", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Exercice> listExercices = ExerciceService.getInstance().getAll();
        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("nom", "description").label("Trier par");
        Button sortButton = new Button("Trier");
        sortButton.addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            ArrayList<Exercice> sortedList = listExercices;
            exerciceCompareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(sortedList);
            for (Exercice exercice : sortedList) {
                Component model = makeExerciceModel(exercice);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();

            ToastBar.getInstance().setPosition(BOTTOM);
            ToastBar.Status status = ToastBar.getInstance().createStatus();
            status.setShowProgressIndicator(false);
            status.setMessage("Trié par " + sortPicker.getPicker().getSelectedString());
            status.setExpires(5000);
            status.show();
        });
        Container sortContainer = new Container(new BorderLayout());

        sortContainer.add(BorderLayout.WEST, sortPicker);
        sortContainer.add(BorderLayout.EAST, sortButton);


        if (listExercices.size() > 0) {
            for (Exercice exercice : listExercices) {
                Component model = makeExerciceModel(exercice);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }


    private Component makeExerciceModel(Exercice exercice) {
        Container exerciceModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceModel.setUIID("containerRounded");


        imageLabel = new Label("Image : " + exercice.getImage());
        imageLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + exercice.getNom());
        nomLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + exercice.getDescription());
        descriptionLabel.setUIID("labelDefault");

        videoLabel = new Label("Video : " + exercice.getVideo());
        videoLabel.setUIID("labelDefault");

        docsLabel = new Label("Docs : " + exercice.getDocs());
        docsLabel.setUIID("labelDefault");


        exercicecategorieLabel = new Label("Exercicecategorie : " + exercice.getExercicecategorie().getLibelle());
        exercicecategorieLabel.setUIID("labelDefault");


        if (exercice.getImage() != null) {
            String url = Statics.EXERCICE_IMAGE_URL + exercice.getImage();
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

        exerciceModel.addAll(
                imageLabel, nomLabel, descriptionLabel, videoLabel, docsLabel, exercicecategorieLabel,

                imageIV
        );

        return exerciceModel;
    }

    private Container makeModelWithoutButtons(Exercice exercice) {
        Container exerciceModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exerciceModel.setUIID("containerRounded");

        imageLabel = new Label("Image : " + exercice.getImage());
        imageLabel.setUIID("labelDefault");

        nomLabel = new Label("Nom : " + exercice.getNom());
        nomLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + exercice.getDescription());
        descriptionLabel.setUIID("labelDefault");

        videoLabel = new Label("Video : " + exercice.getVideo());
        videoLabel.setUIID("labelDefault");

        docsLabel = new Label("Docs : " + exercice.getDocs());
        docsLabel.setUIID("labelDefault");


        exercicecategorieLabel = new Label("Exercicecategorie : " + exercice.getExercicecategorie().getLibelle());
        exercicecategorieLabel.setUIID("labelDefault");


        if (exercice.getImage() != null) {
            String url = Statics.EXERCICE_IMAGE_URL + exercice.getImage();
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


        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        Button btnAfficherScreenshot = new Button("Partager");
        btnAfficherScreenshot.addActionListener(listener -> share(exercice));

        btnsContainer.add(BorderLayout.CENTER, btnAfficherScreenshot);

        exerciceModel.addAll(
                btnsContainer
        );

        return exerciceModel;
    }

    private void share(Exercice exercice) {
        Form form = new Form();
        form.add(new Label("Exercice " + exercice.getNom()));
        form.add(makeModelWithoutButtons(exercice));
        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        Image screenshot = Image.createImage(
                com.codename1.ui.Display.getInstance().getDisplayWidth(),
                com.codename1.ui.Display.getInstance().getDisplayHeight()
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
        Form screenShotForm = new Form("Partager le exercice", new BoxLayout(BoxLayout.Y_AXIS));
        ImageViewer screenshotViewer = new ImageViewer(screenshot.fill(1000, 2000));
        screenshotViewer.setFocusable(false);
        screenshotViewer.setUIID("screenshot");
        ShareButton btnPartager = new ShareButton();
        btnPartager.setText("Partager ");
        btnPartager.setTextPosition(LEFT);
        btnPartager.setImageToShare(imageFile, "image/png");
        btnPartager.setTextToShare(exercice.getDescription());
        screenShotForm.addAll(screenshotViewer, btnPartager);
        screenShotForm.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> this.showBack());
        screenShotForm.show();
        // FIN API PARTAGE
    }
}