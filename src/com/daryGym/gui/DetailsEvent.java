/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daryGym.gui;

import com.daryGym.entities.Event;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.daryGym.services.eventService;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class DetailsEvent extends Form{
    public DetailsEvent(Resources res, Event f, Boolean isAdmin) throws ParseException {
     /*   reservationService sf = new reservationService();
        
        Event f = sf.DetailReclamation(id);*/
                setLayout(BoxLayout.y());

        setTitle(f.getNom());
        Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
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
            
            Container description = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container prix = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container noms = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container lieux = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container types = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container dates = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container datesfin = new Container(new BoxLayout(BoxLayout.X_AXIS));
            
            SpanLabel desc = new SpanLabel();
            SpanLabel prenom = new SpanLabel();
             SpanLabel nom = new SpanLabel();
            SpanLabel statut = new SpanLabel();
            SpanLabel type = new SpanLabel();
            SpanLabel date = new SpanLabel();
            SpanLabel datefin = new SpanLabel();
            Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            Font small = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
            desc.setText(f.getDescription());
            desc.getTextAllStyles().setFont(mediumBoldSystemFont);
            desc.getTextAllStyles().setFgColor(0x2866C7);
            
            prenom.setText(f.getPrix());
            prenom.getTextAllStyles().setFont(mediumBoldSystemFont);
            prenom.getTextAllStyles().setFgColor(0x2866C7);
            
            nom.setText(f.getNom());
            nom.getTextAllStyles().setFont(mediumBoldSystemFont);
            nom.getTextAllStyles().setFgColor(0x2866C7);
            
            statut.setText(f.getLieu());
            statut.getTextAllStyles().setFont(mediumBoldSystemFont);
            statut.getTextAllStyles().setFgColor(0x2866C7);
            
            type.setText(f.getType());
            type.getTextAllStyles().setFont(mediumBoldSystemFont);
            type.getTextAllStyles().setFgColor(0x2866C7);
            
             date.setText(f.getDate());
            date.getTextAllStyles().setFont(mediumBoldSystemFont);
            date.getTextAllStyles().setFgColor(0x2866C7);
            
            datefin.setText(f.getDatefin());
            datefin.getTextAllStyles().setFont(mediumBoldSystemFont);
            datefin.getTextAllStyles().setFgColor(0x2866C7);
            
            Label descfor = new Label("Description: ");
            descfor.setUIID("wasf");
            description.add(FlowLayout.encloseLeftMiddle(descfor));
            description.add(FlowLayout.encloseCenter(desc));
            
            Label aaa = new Label("Prix: ");
            aaa.setUIID("esmou");
            prix.add(FlowLayout.encloseLeftMiddle(aaa));
            prix.add(FlowLayout.encloseCenter(prenom));
            
            Label name = new Label("Nom: ");
             name.setUIID("3onwenn");
            noms.add(FlowLayout.encloseLeftMiddle(name));
            noms.add(FlowLayout.encloseCenter(nom));
            
              Label labelstat = new Label("Lieu: ");
             labelstat.setUIID("lieu");
            lieux.add(FlowLayout.encloseLeftMiddle(labelstat));
            lieux.add(FlowLayout.encloseCenter(statut));
            
             Label labeltype = new Label("Type: ");
             labeltype.setUIID("type");
            types.add(FlowLayout.encloseLeftMiddle(labeltype));
            types.add(FlowLayout.encloseCenter(type));
            
            
             Label labeldate = new Label("Date de début: ");
             labeldate.setUIID("date");
            dates.add(FlowLayout.encloseLeftMiddle(labeldate));
            dates.add(FlowLayout.encloseCenter(date));
            
            Label labeldatefin = new Label("Date fin: ");
             labeldate.setUIID("datefin");
            datesfin.add(FlowLayout.encloseLeftMiddle(labeldatefin));
            datesfin.add(FlowLayout.encloseCenter(datefin));


       
            together.add(FlowLayout.encloseCenter(i));
            together.add(description);
            together.add(prix);
            together.add(noms);
            together.add(lieux);
            together.add(types);
            together.add(dates);
            together.add(datesfin);
            
            Container buttons = new Container(new BoxLayout(BoxLayout.X_AXIS));
            FloatingActionButton join = FloatingActionButton.createFAB(FontImage.MATERIAL_PAYMENT);
            join.getAllStyles().setFgColor(0x408948);
            join.setText("Joindre");
            
            FloatingActionButton delete = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
            delete.getAllStyles().setFgColor(0x408948);
            delete.setText("Supprimer");   
            add(FlowLayout.encloseCenter(together));
            
            FloatingActionButton edit = FloatingActionButton.createFAB(FontImage.MATERIAL_EDIT);
            delete.getAllStyles().setFgColor(0x408948);
            delete.setText("Modifier");
            
            
            
            if(!isAdmin){
            buttons.add(join);
            }
            else {
                buttons.add(edit);
            buttons.add(delete);
            }
           
            add(FlowLayout.encloseCenter(buttons));
            
            
            join.addActionListener(l ->{
                JoinEventPage jep = new JoinEventPage(res);
                jep.show();
          /*  responseAddPage rap = new responseAddPage(res, id);
             Toolbar tb = rap.getToolbar();
                    tb.addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        new reclamationsPage(res).showBack();
                        
                    });
                    
                    rap.show();*/
            
            });
            
            edit.addActionListener(l1 ->{
            EditEventPage ep = new EditEventPage(res, f);
            ep.show();
            });
            
            delete.addActionListener(l -> {
                eventService ser = new eventService();
                ser.supprimerEvent(f.getId());
           
                ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("suppression effectuée avec succés!");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
             eventsPage rp = new eventsPage(res, true);
             rp.showBack();
            });
            
             Toolbar tb = getToolbar();
                    tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        if(isAdmin){
                        new eventsPage(res,true).showBack();
                        }
                        else {
                        new eventsPage(res,false).showBack();
                        }
                       
                       
                    });
    
    }

   
    

}
