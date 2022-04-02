package com.daryGym.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Entities.Reclamation;

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
import com.daryGym.services.reclamationService;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class DetailsReclamation extends Form{
    public DetailsReclamation(Resources res, int id) throws ParseException {
        reclamationService sf = new reclamationService();
        
        Reclamation f = sf.DetailReclamation(id);
                setLayout(BoxLayout.y());

        setTitle(f.getTitre());
        Container together = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
         Label i = new Label();
            Image img;
            try {
                img = Image.createImage("/reclamation.jpg");
                i.setHeight(1);
                i.setWidth(1);
                i.setIcon(img);
            } catch (IOException ex) {
                //  Logger.getLogger(HomeForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Container description = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container prenoms = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container noms = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container statuts = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container types = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container dates = new Container(new BoxLayout(BoxLayout.X_AXIS));
            
            SpanLabel desc = new SpanLabel();
            SpanLabel prenom = new SpanLabel();
             SpanLabel nom = new SpanLabel();
            SpanLabel statut = new SpanLabel();
            SpanLabel type = new SpanLabel();
            SpanLabel date = new SpanLabel();
            Font mediumBoldSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
            Font small = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
            desc.setText(f.getDescription());
            desc.getTextAllStyles().setFont(mediumBoldSystemFont);
            desc.getTextAllStyles().setFgColor(0x2866C7);
            
            prenom.setText(f.getPrenom());
            prenom.getTextAllStyles().setFont(mediumBoldSystemFont);
            prenom.getTextAllStyles().setFgColor(0x2866C7);
            
            nom.setText(f.getNom());
            nom.getTextAllStyles().setFont(mediumBoldSystemFont);
            nom.getTextAllStyles().setFgColor(0x2866C7);
            
            statut.setText(textStatut(f.getStatut()));
            statut.getTextAllStyles().setFont(mediumBoldSystemFont);
            statut.getTextAllStyles().setFgColor(0x2866C7);
            
            type.setText(f.getType());
            type.getTextAllStyles().setFont(mediumBoldSystemFont);
            type.getTextAllStyles().setFgColor(0x2866C7);
            
             date.setText(f.getCreated_At());
            date.getTextAllStyles().setFont(mediumBoldSystemFont);
            date.getTextAllStyles().setFgColor(0x2866C7);
            
            Label descfor = new Label("Description: ");
            descfor.setUIID("wasf");
            description.add(FlowLayout.encloseLeftMiddle(descfor));
            description.add(FlowLayout.encloseCenter(desc));
            
            Label aaa = new Label("Prénom: ");
            aaa.setUIID("esmou");
            prenoms.add(FlowLayout.encloseLeftMiddle(aaa));
            prenoms.add(FlowLayout.encloseCenter(prenom));
            
            Label name = new Label("Nom: ");
             name.setUIID("3onwenn");
            noms.add(FlowLayout.encloseLeftMiddle(name));
            noms.add(FlowLayout.encloseCenter(nom));
            
              Label labelstat = new Label("Statut: ");
             labelstat.setUIID("statut");
            statuts.add(FlowLayout.encloseLeftMiddle(labelstat));
            statuts.add(FlowLayout.encloseCenter(statut));
            
             Label labeltype = new Label("Type: ");
             labeltype.setUIID("type");
            types.add(FlowLayout.encloseLeftMiddle(labeltype));
            types.add(FlowLayout.encloseCenter(type));
            
            
             Label labeldate = new Label("Date: ");
             labeldate.setUIID("date");
            dates.add(FlowLayout.encloseLeftMiddle(labeldate));
            dates.add(FlowLayout.encloseCenter(date));


       
            together.add(FlowLayout.encloseCenter(i));
            together.add(description);
            together.add(prenoms);
            together.add(noms);
            together.add(statuts);
            together.add(types);
            together.add(dates);
            
            Container buttons = new Container(new BoxLayout(BoxLayout.X_AXIS));
            FloatingActionButton view = FloatingActionButton.createFAB(FontImage.MATERIAL_QUESTION_ANSWER);
            view.getAllStyles().setFgColor(0x408948);
            view.setText("Repondre");
            
            FloatingActionButton delete = FloatingActionButton.createFAB(FontImage.MATERIAL_DELETE);
            delete.getAllStyles().setFgColor(0x408948);
            delete.setText("Supprimer");   
            add(FlowLayout.encloseCenter(together));
            
            buttons.add(view);
            buttons.add(delete);
            add(FlowLayout.encloseCenter(buttons));
            
            
            view.addActionListener(l ->{
            responseAddPage rap = new responseAddPage(res, id);
             Toolbar tb = rap.getToolbar();
                    tb.addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, e2 -> {
                        new reclamationsPage(res).showBack();
                        
                    });
                    
                    rap.show();
            
            });
            
            delete.addActionListener(l -> {
             reclamationService ser = new reclamationService();
                ser.supprimerReclamation(id);
                ToastBar.Status s = ToastBar.getInstance().createStatus();
            s.setExpires(3000);
            s.setMessage("suppression effectuée avec succés!");
            s.setIcon(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Label", 3));
            s.show();
             reclamationsPage rp = new reclamationsPage(res);
             rp.showBack();
            });
            
            
    
    }
    
    private String textStatut(int statut){
        if (statut == 0) return "Non repondue";
    return "repondue";
    }

    private Date getRealDate(String date) throws ParseException{
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }
}
