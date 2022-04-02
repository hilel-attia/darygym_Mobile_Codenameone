package com.daryGym.gui.back.exercice;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.entities.Exercice;
import com.daryGym.services.ExerciceService;
import com.daryGym.utils.Statics;

import java.util.ArrayList;

public class DisplayAll extends Form {


    public static Exercice currentExercice = null;
    Resources theme = UIManager.initFirstTheme("/theme");
    Button addBtn;
    Label imageLabel, nomLabel, descriptionLabel, videoLabel, docsLabel, exercicecategorieLabel;
    ImageViewer imageIV;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public DisplayAll(Form previous) {
        super("Exercices", new BoxLayout(BoxLayout.Y_AXIS));

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

        ArrayList<Exercice> listExercices = ExerciceService.getInstance().getAll();
        if (listExercices.size() > 0) {
            for (Exercice listExercice : listExercices) {
                this.add(makeExerciceModel(listExercice));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentExercice = null;
            new Manage(this).show();
        });
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

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonMain");
        editBtn.addActionListener(action -> {
            currentExercice = exercice;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce exercice ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ExerciceService.getInstance().delete(exercice.getId());

                if (responseCode == 200) {
                    currentExercice = null;
                    dlg.dispose();
                    exerciceModel.remove();
                    this.revalidate();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du exercice. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.CENTER, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        exerciceModel.addAll(
                imageLabel, nomLabel, descriptionLabel, videoLabel, docsLabel, exercicecategorieLabel,

                imageIV,

                btnsContainer
        );

        return exerciceModel;
    }
}