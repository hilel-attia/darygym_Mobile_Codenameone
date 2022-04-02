package com.daryGym.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Entities.Reclamation;
import Entities.Reponse;


import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.daryGym.gui.back.AccueilBack;
import com.daryGym.services.reclamationService;


import java.util.ArrayList;
//import javafx.scene.layout.Background;

/**
 *
 * @author LENOVO
 */
public class reclamationAddPage extends Form {
    Label bienvenue;
    Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container checks = new Container(new BoxLayout(BoxLayout.X_AXIS));
      Button confirm; 
     int w = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
       
     
  
     
   public reclamationAddPage(Resources res){
       setTitle("Votre reclamation");
           setLayout(BoxLayout.y());
      //    setPreferredSize(new Dimension(300, 1500));
          Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            TextField descField = new TextField("", "Description"); // <1>
        descField.getHintLabel().setUIID("Description");
        descField.setUIID("Description");
         TextField titreField = new TextField("", "Titre"); // <1>
         titreField.getHintLabel().setUIID("Titre");
        titreField.getHintLabel().setUIID("Titre");
        
        TextField nomField = new TextField("", "Nom"); // <1>
         nomField.getHintLabel().setUIID("Nom");
        nomField.getHintLabel().setUIID("Nom");
        
         TextField prenomField = new TextField("", "Prenom"); // <1>
         prenomField.getHintLabel().setUIID("Prenom");
        prenomField.getHintLabel().setUIID("Prenom");
        
        
        RadioButton rb1 = new RadioButton("elevee");
RadioButton rb2 = new RadioButton("moyenne");
RadioButton rb3 = new RadioButton("faible");
ButtonGroup bg = new ButtonGroup(rb1, rb2, rb3);
checks.add(rb1).add(rb2).add(rb3);
        
        
        confirm = new Button("Confirmer");
         Style adminStyle = confirm.getAllStyles();
            adminStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            adminStyle.setMargin(Component.TOP, 10);
              adminStyle.setFont(mediumBoldSystemFont);
      
      //  searchField.setPreferredSize(new Dimension(0,20));
       applyStyle(descField);
       applyStyle(titreField);
       applyStyle(nomField);
       applyStyle(prenomField);
     //  applyStyle(bg);
     // Style loginStyle = descField.getAllStyles();
    
       add(descField);
       add(titreField);
       add(nomField);
       add(prenomField);
       add(checks);
       add(confirm);
       
       
       confirm.addActionListener((e) -> {
           reclamationService rs = new reclamationService();
           Reclamation r = new Reclamation();
           r.setDescription(descField.getText().toString());
           r.setNom(nomField.getText().toString());
           r.setPrenom(prenomField.getText().toString());
           r.setTitre(titreField.getText().toString());
           r.setType(bg.getSelected().getText());
          
           rs.addReclamation(r);
               ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("réponse ajoutée avec succés!");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
                           AccueilBack rp = new AccueilBack(res);

             rp.show();
          
       });
       
       Toolbar tb = getToolbar();
                    tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        
                                   new com.daryGym.gui.back.AccueilBack().show();

                      
                  
                    });
   }
   
   private void applyStyle(TextField tf){
    Style loginStyle = tf.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
loginStyle.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
loginStyle.setBgColor(0xffffff);
loginStyle.setBgTransparency(255);
  loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
loginStyle.setMargin(Component.BOTTOM, 3);
loginStyle.setMargin(Component.TOP, 3);

   }
    
   
}
