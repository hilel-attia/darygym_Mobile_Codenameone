/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daryGym.gui;



import com.daryGym.entities.Event;
import com.daryGym.entities.Reservation;

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
import com.daryGym.services.reservationService;


import java.util.ArrayList;
//import javafx.scene.layout.Background;

/**
 *
 * @author LENOVO
 */
public class JoinEventPage extends Form {
    Label bienvenue;
    Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container checks = new Container(new BoxLayout(BoxLayout.X_AXIS));
      Button confirm; 
     int w = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
       
     
  
     
   public JoinEventPage(Resources res){
       setTitle("Reservez votre place");
           setLayout(BoxLayout.y());
      //    setPreferredSize(new Dimension(300, 1500));
          Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            TextField namepartField = new TextField("", "Namepart"); // <1>
        namepartField.getHintLabel().setUIID("Namepart");
        
         TextField UsernamepartField = new TextField("", "Usernamepart"); // <1>
         UsernamepartField.getHintLabel().setUIID("Usernamepart");
     
        
        TextField VilleField = new TextField("", "Ville"); // <1>
         VilleField.getHintLabel().setUIID("Ville");
       
        
         TextField NumField = new TextField("", "Numero de telephone",20, TextArea.PHONENUMBER); // <1>
         NumField.getHintLabel().setUIID("Num tel");
        
           TextField emailField = new TextField("", "Email"); // <1>
         emailField.getHintLabel().setUIID("Email");
        
       
        
        
        confirm = new Button("Confirmer");
         Style adminStyle = confirm.getAllStyles();
            adminStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            adminStyle.setMargin(Component.TOP, 10);
              adminStyle.setFont(mediumBoldSystemFont);
      
      //  searchField.setPreferredSize(new Dimension(0,20));
       applyStyle(namepartField);
       applyStyle(UsernamepartField);
       applyStyle(VilleField);
       applyStyle(NumField);
       applyStyle(emailField);
     //  applyStyle(bg);
     // Style loginStyle = descField.getAllStyles();
    
       add(namepartField);
       add(UsernamepartField);
       add(VilleField);
       add(NumField);
       add(emailField);
    
       add(confirm);
       
       
       confirm.addActionListener((e) -> {
           
           reservationService rs = new reservationService();
           Reservation r = new Reservation();
           r.setNamepart(namepartField.getText().toString());
           r.setUsernamepart(UsernamepartField.getText().toString());
           r.setVille(VilleField.getText().toString());
           r.setNumtelephonepart(NumField.getText().toString());
           r.setEmail_participent(emailField.getText().toString());
          
           rs.addReservation(r);
               ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("réponse ajoutée avec succés!");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
             eventsPage rp = new eventsPage(res, false);
             rp.show();
          
       });
       
       Toolbar tb = getToolbar();
                    tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        
                        new eventsPage(res,false).showBack();
                      
                  
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
