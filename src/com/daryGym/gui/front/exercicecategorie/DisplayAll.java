package com.daryGym.gui.front.exercicecategorie;

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

public class DisplayAll extends Form {


    public static Exercicecategorie currentExercicecategorie = null;
    Resources theme = UIManager.initFirstTheme("/theme");
    TextField searchTF;
    ArrayList<Component> componentModels;
    Label libelleLabel, imageLabel;
    ImageViewer imageIV;
    Container btnsContainer;

    public DisplayAll(Form previous) {
        super("Exercicecategories", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        ArrayList<Exercicecategorie> listExercicecategories = ExercicecategorieService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher un exercicecategorie");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Exercicecategorie exercicecategorie : listExercicecategories) {
                if (exercicecategorie.getLibelle().startsWith(searchTF.getText())) {
                    Component model = makeExercicecategorieModel(exercicecategorie);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);

        if (listExercicecategories.size() > 0) {
            for (Exercicecategorie exercicecategorie : listExercicecategories) {
                Component model = makeExercicecategorieModel(exercicecategorie);
                this.add(model);
                componentModels.add(model);
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


        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        exercicecategorieModel.addAll(
                libelleLabel, imageLabel,

                imageIV,

                btnsContainer
        );

        return exercicecategorieModel;
    }
}