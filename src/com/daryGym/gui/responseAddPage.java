package com.daryGym.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import Entities.Reponse;




import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
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
import com.daryGym.services.reponseService;


import java.util.ArrayList;
//import javafx.scene.layout.Background;

/**
 *
 * @author LENOVO
 */
public class responseAddPage extends Form {
    Label bienvenue;
    Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      Button confirm; 
     int w = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
       
     
  
     
   public responseAddPage(Resources res, int idReclamation){
       setTitle("Votre réponse");
           setLayout(BoxLayout.y());
      //    setPreferredSize(new Dimension(300, 1500));
          Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            TextField loginField = new TextField("", "Description"); // <1>
        loginField.getHintLabel().setUIID("Titre");
        loginField.setUIID("Titre");
         TextField passField = new TextField("", "Email"); // <1>
         passField.getHintLabel().setUIID("email");
        passField.getHintLabel().setUIID("email");
        
        confirm = new Button("Confirmer");
         Style adminStyle = confirm.getAllStyles();
            adminStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            adminStyle.setMargin(Component.TOP, 10);
              adminStyle.setFont(mediumBoldSystemFont);
      
      //  searchField.setPreferredSize(new Dimension(0,20));
       applyStyle(loginField);
       applyStyle(passField);
       Style loginStyle = loginField.getAllStyles();
      loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
loginStyle.setMargin(Component.BOTTOM, 3);
loginStyle.setMargin(Component.TOP, 30);
       add(loginField);
       add(passField);
       add(confirm);
       
       
       confirm.addActionListener((e) -> {
           reponseService rs = new reponseService();
           Reponse r = new Reponse();
           r.setDescription(loginField.getText().toString());
           r.setEmail(passField.getText().toString());
           rs.addReponses(r, idReclamation);
               ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("réponse ajoutée avec succés!");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
             reclamationsPage rp = new reclamationsPage(res);
             rp.show();
          
       });
       
       Toolbar tb = getToolbar();
                    tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        
                        new reponsesPage(res).showBack();
                      
                  
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

   }
   
   
}