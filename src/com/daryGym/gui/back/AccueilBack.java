package com.daryGym.gui.back;


import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.MainApp;
import com.daryGym.utils.StatForm;
import com.daryGym.gui.LoginForm;
import com.daryGym.gui.MapForm;

import com.daryGym.gui.eventsPage;
import com.daryGym.gui.archivePage;
import com.daryGym.gui.participentsPage;
public class AccueilBack extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;
    private Resources res;

    public AccueilBack() {
        super("Admin", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
    }

    public AccueilBack(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addGUIs() {
        ImageViewer userImage = new ImageViewer(theme.getImage("person.jpg").fill(200, 200));
        userImage.setUIID("candidatImage");

        label = new Label(MainApp.getSession().getEmail());
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setUIID("buttonLogout");
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> {
            LoginForm.loginForm.showBack();
        });

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");

        userContainer.add(BorderLayout.WEST, userImage);
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeButton(
                        "    Users",
                        FontImage.MATERIAL_PERSON,
                        new com.daryGym.gui.back.user.DisplayAll(this)
                ),
                makeButton(
                        "    Abonnements",
                        FontImage.MATERIAL_SUBSCRIPTIONS,
                        new com.daryGym.gui.back.abonnement.DisplayAll(this)
                ),
                makeButton(
                        "    Offres",
                        FontImage.MATERIAL_LOCAL_OFFER,
                        new com.daryGym.gui.back.offre.DisplayAll(this)
                ), makeButton(
                        "    Exercices",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.back.exercice.DisplayAll(this)
                ), makeButton(
                        "    ExercicesCategorie",
                        FontImage.MATERIAL_CATEGORY,
                        new com.daryGym.gui.back.exercicecategorie.DisplayAll(this)
                )
                 , makeButton(
                        "  Gérer les évennements  ",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.eventsPage(res, true)
                ) 
                
                , makeButton(
                        "  Gérer les participants  ",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.participentsPage(res)
                )
                , makeButton(
                        "  Gérer les archives  ",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.archivePage(res,true)
                )
               
             , makeButton(
                        "  Gérer les recmamations  ",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.reclamationsPage(res)
                )
                 , makeButton(
                        "  Gérer les reponses  ",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.reponsesPage(res)
                )
                , makeButton(
                        "    reclamation",
                        FontImage.MATERIAL_HIKING,   
                        new com.daryGym.utils.StatForm().createPieChartForm()
                )
                   
                
                 
        
                  
        );

        this.add(menuContainer);
    }
 
    private Button makeButton(String nomBouton, char icon, Form localisation) {
        Button button = new Button(nomBouton);
        button.setUIID("buttonMenu");
        button.setMaterialIcon(icon);
        button.addActionListener(action -> {
            localisation.show();
        });
        return button;
    }
}
