package com.daryGym.gui.back.exercice;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Exercicecategorie;
import com.daryGym.services.ExercicecategorieService;
import com.daryGym.utils.Statics;

import java.util.ArrayList;

public class ChooseExercicecategorie extends Form {

    Form previousForm;
    Resources theme = UIManager.initFirstTheme("/theme");
    Label libelleLabel, imageLabel;
    ImageViewer imageIV;
    Button chooseBtn;
    Container btnsContainer;

    public ChooseExercicecategorie(Form previous) {
        super("Choisir un exercicecategorie", new BoxLayout(BoxLayout.Y_AXIS));

        previousForm = previous;
        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.revalidate();
    }

    private void addGUIs() {
        ArrayList<Exercicecategorie> listExercicecategories = ExercicecategorieService.getInstance().getAll();
        if (listExercicecategories.size() > 0) {
            for (Exercicecategorie exercicecategories : listExercicecategories) {
                this.add(makeExercicecategorieModel(exercicecategories));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeExercicecategorieModel(Exercicecategorie exercicecategorie) {
        Container exercicecategorieModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        exercicecategorieModel.setUIID("containerRounded");

        libelleLabel = new Label("Libelle : " + exercicecategorie.getLibelle());
        libelleLabel.setUIID("labelDefault");

        imageLabel = new Label("Image : " + exercicecategorie.getImage());
        imageLabel.setUIID("labelDefault");


        if (exercicecategorie.getImage() != null) {
            String url = Statics.EXERCICECATEGORIE_IMAGE_URL + exercicecategorie.getImage();
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


        chooseBtn = new Button("Choisir");
        chooseBtn.addActionListener(l -> {
            Manage.selectedExercicecategorie = exercicecategorie;
            ((Manage) previousForm).refreshExercicecategorie();
            previousForm.showBack();
        });

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        btnsContainer.add(BorderLayout.CENTER, chooseBtn);

        exercicecategorieModel.addAll(
                libelleLabel, imageLabel,

                imageIV,

                btnsContainer
        );

        return exercicecategorieModel;
    }
}