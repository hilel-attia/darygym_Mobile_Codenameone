package com.daryGym.gui.back.exercicecategorie;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
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
    Button addBtn;
    Label libelleLabel, imageLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public DisplayAll(Form previous) {
        super("Exercicecategories", new BoxLayout(BoxLayout.Y_AXIS));

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
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");

        this.add(addBtn);

        ArrayList<Exercicecategorie> listExercicecategories = ExercicecategorieService.getInstance().getAll();
        if (listExercicecategories.size() > 0) {
            for (Exercicecategorie listExercicecategorie : listExercicecategories) {
                this.add(makeExercicecategorieModel(listExercicecategorie));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentExercicecategorie = null;
            new Manage(this).show();
        });
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

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonMain");
        editBtn.addActionListener(action -> {
            currentExercicecategorie = exercicecategorie;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce exercicecategorie ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ExercicecategorieService.getInstance().delete(exercicecategorie.getId());

                if (responseCode == 200) {
                    currentExercicecategorie = null;
                    dlg.dispose();
                    exercicecategorieModel.remove();
                    this.revalidate();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du exercicecategorie. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.CENTER, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        exercicecategorieModel.addAll(
                libelleLabel, imageLabel,

                imageIV,

                btnsContainer
        );

        return exercicecategorieModel;
    }
}