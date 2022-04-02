package com.daryGym.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.daryGym.MainApp;
import com.daryGym.gui.DetailsEvent;

import com.daryGym.gui.LoginForm;
import com.daryGym.gui.MapForm;
import com.daryGym.gui.archivePage;



public class AccueilFront extends Form {

    Resources theme = UIManager.initFirstTheme("/theme");
    Label label;
 private Resources res;
    public static Form accueilFrontForm;

    public AccueilFront() {
        super(new BorderLayout());
        accueilFrontForm = this;
        addGUIs();
    }

    private void addGUIs() {
        Tabs tabs = new Tabs();

        tabs.addTab("Offres", FontImage.MATERIAL_LOCAL_OFFER, 5,
                new com.daryGym.gui.front.offre.DisplayAll()
        );
        tabs.addTab("Plus", FontImage.MATERIAL_MENU, 5, moreGUI());

        this.add(BorderLayout.CENTER, tabs);
    }

    private Container moreGUI() {

        ImageViewer userImage = new ImageViewer(theme.getImage("person.jpg").fill(200, 200));
        userImage.setUIID("candidatImage");
        label = new Label(MainApp.getSession().getNomComplet());
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
                        "    Mes abonnements",
                        FontImage.MATERIAL_SUBSCRIPTIONS,
                        new com.daryGym.gui.front.abonnement.DisplayMy(this)
                ), makeButton(
                        "    Exercices",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.front.exercice.DisplayAll(this)
                ), makeButton(
                        "    ExercicesCategorie",
                        FontImage.MATERIAL_CATEGORY,
                        new com.daryGym.gui.front.exercicecategorie.DisplayAll(this)
                        
                ) 
                
                , makeButton(
                        "    archive",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.archivePage(res,true)
                )
                  , makeButton(
                        "    evenment",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.JoinEventPage(res)
                )
                , makeButton(
                        "    reclamation",
                        FontImage.MATERIAL_HIKING,
                        new com.daryGym.gui.reclamationAddPage(res)
                )
                 
        );
        
        
        
       
            
        return (menuContainer);
       
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
