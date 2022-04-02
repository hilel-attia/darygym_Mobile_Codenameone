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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.daryGym.services.eventService;


import java.util.ArrayList;
import java.util.Date;
//import javafx.scene.layout.Background;

/**
 *
 * @author LENOVO
 */
public class EditEventPage extends Form {
    Label bienvenue;
    Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    Container checks = new Container(new BoxLayout(BoxLayout.X_AXIS));
      Button confirm; 
     int w = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
       
     
  
     
   public EditEventPage(Resources res, Event e){
       setTitle("Reservez votre place");
           setLayout(BoxLayout.y());
      //    setPreferredSize(new Dimension(300, 1500));
          Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            TextField nomField = new TextField("", "Nom"); // <1>
        nomField.getHintLabel().setUIID("Nom");
        nomField.setText(e.getNom());
        
         TextField LieuField = new TextField("", "Lieu"); // <1>
         LieuField.getHintLabel().setUIID("Lieu");
         LieuField.setText(e.getLieu());
     
         Picker datePicker = new Picker();
datePicker.setType(Display.PICKER_TYPE_DATE);

Picker datefinPicker = new Picker();
datePicker.setType(Display.PICKER_TYPE_DATE);
        
        TextField TypeField = new TextField("", "Type"); // <1>
         TypeField.getHintLabel().setUIID("Type");
         TypeField.setText(e.getType());
       
        
         TextField PrixField = new TextField("", "Prix"); // <1>
         PrixField.getHintLabel().setUIID("Prix");
         PrixField.setText(e.getPrix());
        
           TextArea DescriptionField = new TextField("", "Description"); // <1>
         DescriptionField.getHintLabel().setUIID("Description");
         DescriptionField.setText(e.getDescription());
        
       
        
        
        confirm = new Button("Confirmer");
         Style adminStyle = confirm.getAllStyles();
            adminStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            adminStyle.setMargin(Component.TOP, 10);
              adminStyle.setFont(mediumBoldSystemFont);
      
      //  searchField.setPreferredSize(new Dimension(0,20));
       applyStyle(nomField);
       applyStyle(LieuField);
       applyStyle(TypeField);
       applyStyle(PrixField);
       applyStyle(DescriptionField);
     //  applyStyle(bg);
     // Style loginStyle = descField.getAllStyles();
    
       add(nomField);
       add(LieuField);
       add(TypeField);
       add(PrixField);
       add(DescriptionField);
       add(datePicker);
       add(datefinPicker);
    
       add(confirm);
       
       
       confirm.addActionListener((e1) -> {
           
          // System.err.println("selected date: " + datePicker.getDate().toString() + " w selected findate: " + datefinPicker.getDate().toString());
           eventService rs = new eventService();
           Event r = new Event();
           r.setImage("cc173ac9d79342b261db693cb220a953.jpg");
           r.setNom(nomField.getText().toString());
           r.setLieu(LieuField.getText().toString());
           r.setType(TypeField.getText().toString());
           r.setPrix(PrixField.getText().toString());
           r.setDescription(DescriptionField.getText().toString());
           r.setDate(getDateString(datePicker.getDate()));
           r.setDatefin(getDateString(datefinPicker.getDate()));
          
           rs.modifEvent(r, e.getId());
               ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("evennement modifié avec succés!");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
             eventsPage rp = new eventsPage(res, true);
             rp.show();
          
       });
       
       Toolbar tb = getToolbar();
                    tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        
                        new eventsPage(res,true).showBack();
                      
                  
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
   
   private void applyStyle(TextArea tf){
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
   
   private String getDateString(Date date){
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
 return dateFormat.format(date);  
   }
   
   
   
}
