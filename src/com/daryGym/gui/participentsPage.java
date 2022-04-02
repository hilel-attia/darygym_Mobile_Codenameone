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
import com.codename1.l10n.ParseException;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Painter;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Stroke;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.ComponentAnimation;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.GeneralPath;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.daryGym.gui.back.AccueilBack;
import com.daryGym.services.reservationService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.util.concurrent.ThreadLocalRandom.current;


/**
 *
 * @author ASUS
 */
public class participentsPage extends Form {

    /*Form current;
    List<Formation> formations = new ArrayList<>();
    public HomeForm() {
        current=this;
        formations = ServiceFormation.getInstance().getAllformations();
        setTitle("Liste des formations");
        setLayout(BoxLayout.y());
  
    }*/
    TextArea eventname, eventdescription, eventadress;
    Button details;
    reservationService ser;
    int w = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

   // int iduser = 1;

    public participentsPage(Resources res) {
        
        setTitle("Nos participents");
        setLayout(BoxLayout.y());
        
         ser = new reservationService();
        ArrayList<Reservation> list = ser.getAllReservations();
        TextField searchField = new TextField("", "Rechercher"); // <1>
        searchField.getHintLabel().setUIID("Titre");
        searchField.setUIID("Titre");
      //  searchField.setPreferredSize(new Dimension(0,20));
        Style loginStyle = searchField.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
loginStyle.setBorder(RoundRectBorder.create().
        strokeColor(0).
        strokeOpacity(120).
        stroke(borderStroke));
loginStyle.setBgColor(0xffffff);
loginStyle.setBgTransparency(255);
loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
loginStyle.setMargin(Component.BOTTOM, 3);

        
        
        
        Container spaces = new Container(new BoxLayout(BoxLayout.X_AXIS));
        spaces.add("                                                    ");
        spaces.add(searchField);
        add(spaces);
        Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        add(together);
        showReponses(list,res,together);
        
        searchField.addDataChangeListener((i1, i2) -> { // <2>
    String t = searchField.getText();
    if(t.length() < 1) {
        showReponses(list,res,together);
    } else {
   //   showFormations(ser.searchFormations(t),res,together);
    }
    getContentPane().animateLayout(250);
});
    searchField.startEditingAsync(); 
    
    Toolbar tb = getToolbar();
                    tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        
                         new com.daryGym.gui.back.AccueilBack().show();
                      
                  
                    });
 
    }

    public void showReponses(List<Reservation> list, Resources res, Container together){
      together.removeAll();
      revalidate();
        for (Reservation e : list) {
           
            Container names = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container usernameparts = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container villes = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container numtelephoneparts = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container email_participents = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container buttons = new Container(new BoxLayout(BoxLayout.X_AXIS));
            SpanLabel usernamepart = new SpanLabel();
            SpanLabel ville = new SpanLabel();
            SpanLabel name = new SpanLabel();
            SpanLabel numtelephonepart = new SpanLabel();
            SpanLabel email_participent = new SpanLabel();
            Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            Font small = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
            name.setText(e.getNamepart());
            name.getTextAllStyles().setFont(mediumBoldSystemFont);
            name.getTextAllStyles().setFgColor(0x408948);
            
            usernamepart.setText(e.getUsernamepart());
            usernamepart.getTextAllStyles().setFont(mediumBoldSystemFont);
            usernamepart.getTextAllStyles().setFgColor(0x2866C7);
            
            ville.setText(e.getVille());
            ville.getTextAllStyles().setFont(mediumBoldSystemFont);
            ville.getTextAllStyles().setFgColor(0x2866C7);
            
            numtelephonepart.setText(e.getNumtelephonepart());
            numtelephonepart.getTextAllStyles().setFont(mediumBoldSystemFont);
            numtelephonepart.getTextAllStyles().setFgColor(0x2866C7);
            
             email_participent.setText(e.getEmail_participent());
            email_participent.getTextAllStyles().setFont(mediumBoldSystemFont);
            email_participent.getTextAllStyles().setFgColor(0x2866C7);
            
            
           
       
            names.add(FlowLayout.encloseCenter(name)).animateLayout(1000);
            Label aaa = new Label("usernamepart: ");
            aaa.setUIID("3onwen");
            usernameparts.add(FlowLayout.encloseLeftMiddle(aaa));
            usernameparts.add(FlowLayout.encloseCenter(usernamepart));
            
            Label type = new Label("Ville: ");
             type.setUIID("3onwenn");
            villes.add(FlowLayout.encloseLeftMiddle(type));
            villes.add(FlowLayout.encloseCenter(ville));
         

            Label fin = new Label("Numero: ");
             type.setUIID("3onwenn");
            numtelephoneparts.add(FlowLayout.encloseLeftMiddle(fin));
            numtelephoneparts.add(FlowLayout.encloseCenter(numtelephonepart));
            
             Label mail = new Label("Email: ");
             type.setUIID("3onwenn");
            email_participents.add(FlowLayout.encloseLeftMiddle(mail));
            email_participents.add(FlowLayout.encloseCenter(email_participent));
            
            FloatingActionButton delete = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
            delete.getAllStyles().setFgColor(0x408948);
            delete.setText("Supprimer");
            
            Label i = new Label();
            Image img;
            try {
                img = Image.createImage("/event.jpg");
                i.setHeight(1);
                i.setWidth(1);
                i.setIcon(img);
            } catch (IOException ex) {
                //  Logger.getLogger(HomeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            together.add(FlowLayout.encloseCenter(i));
            together.add(FlowLayout.encloseCenter(names));
            
            together.add(usernameparts);
            together.add(villes);
            together.add(numtelephoneparts);
            together.add(email_participents);
        
           
            
            buttons.add(delete);
          //  buttons.add(view);
            together.add(FlowLayout.encloseCenter(buttons));

           
            together.setUIID("tangacontainer");
            
            delete.addActionListener(l ->{
              ser.supprimerReservation(e.getId());
             ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("suppression effectuée");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
             AccueilBack al = new AccueilBack(res);
            al.show();
            });

      
          /*  delete.addActionListener((e1) -> {
                reservationService ser = new reservationService();
                ser.supprimerReclamation(e.getId());
                ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("suppression effectuée");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
            adminHome al = new adminHome(res);
            al.show();
              
            });*/
            
           
        }
    }
}
