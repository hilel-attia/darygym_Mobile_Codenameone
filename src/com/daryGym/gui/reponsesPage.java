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
import com.daryGym.services.reponseService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 *
 * @author ASUS
 */
public class reponsesPage extends Form {

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
    int w = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

   // int iduser = 1;

    public reponsesPage(Resources res) {
        
        setTitle("Vos réponses");
        setLayout(BoxLayout.y());
        
        reponseService ser = new reponseService();
        ArrayList<Reponse> list = ser.getAllReponses();
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

    public void showReponses(List<Reponse> list, Resources res, Container together){
      together.removeAll();
      revalidate();
        for (Reponse e : list) {
           
            Container names = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container descriptions = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container types = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container datesfin = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container buttons = new Container(new BoxLayout(BoxLayout.X_AXIS));
            SpanLabel desc = new SpanLabel();
            SpanLabel add = new SpanLabel();
            SpanLabel name = new SpanLabel();
            SpanLabel datefin = new SpanLabel();
            Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            Font small = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
            name.setText(e.getEmail());
            name.getTextAllStyles().setFont(mediumBoldSystemFont);
            name.getTextAllStyles().setFgColor(0x408948);
            
            desc.setText(e.getDescription());
            desc.getTextAllStyles().setFont(mediumBoldSystemFont);
            desc.getTextAllStyles().setFgColor(0x2866C7);
            
            add.setText(e.getDatedebut());
            add.getTextAllStyles().setFont(mediumBoldSystemFont);
            add.getTextAllStyles().setFgColor(0x2866C7);
            
            datefin.setText(e.getDatefin());
            datefin.getTextAllStyles().setFont(mediumBoldSystemFont);
            datefin.getTextAllStyles().setFgColor(0x2866C7);
            
            
           
       
            names.add(FlowLayout.encloseCenter(name)).animateLayout(1000);
            Label aaa = new Label("Description: ");
            aaa.setUIID("3onwen");
            descriptions.add(FlowLayout.encloseLeftMiddle(aaa));
            descriptions.add(FlowLayout.encloseCenter(desc));
            
            Label type = new Label("Date début: ");
             type.setUIID("3onwenn");
            types.add(FlowLayout.encloseLeftMiddle(type));
            types.add(FlowLayout.encloseCenter(add));
         

            Label fin = new Label("Date fin: ");
             type.setUIID("3onwenn");
            datesfin.add(FlowLayout.encloseLeftMiddle(fin));
            datesfin.add(FlowLayout.encloseCenter(datefin));
            
            FloatingActionButton delete = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
            delete.getAllStyles().setFgColor(0x408948);
            delete.setText("Supprimer");
            
            Label i = new Label();
            Image img;
            try {
                img = Image.createImage("/reply.jpg");
                i.setHeight(1);
                i.setWidth(1);
                i.setIcon(img);
            } catch (IOException ex) {
                //  Logger.getLogger(HomeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            together.add(FlowLayout.encloseCenter(i));
            together.add(FlowLayout.encloseCenter(names));
            
            together.add(descriptions);
            together.add(types);
            together.add(datesfin);
        
           
            
            buttons.add(delete);
          //  buttons.add(view);
            together.add(FlowLayout.encloseCenter(buttons));

           
            together.setUIID("tangacontainer");

      
            delete.addActionListener((e1) -> {
                reponseService ser = new reponseService();
                ser.supprimerReponse(e.getId());
                ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("suppression effectuée");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
              AccueilBack al = new AccueilBack(res);
            al.show();
              
            });
            
           
        }
    }
}
