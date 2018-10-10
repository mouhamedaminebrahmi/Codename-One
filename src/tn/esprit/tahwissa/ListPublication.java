/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.WebBrowser;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entity.Publication;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import service.PubService;
import util.MenuManager;

/**
 *
 * @author BRAHMI
 */
public class ListPublication {

    private Form current;
    private Resources theme;

    public void init(Object context) {

        theme = UIManager.initFirstTheme("/theme_1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Form hi = new Form(new BoxLayout(BoxLayout.Y_AXIS));
        hi.getToolbar().setUIID("MouveButton");
        hi.getToolbar().addCommandToLeftBar("Back", theme.getImage("back-command.png"),(event) ->{
        MyApplication mp = new MyApplication();
            mp.init(current);
            mp.start();           
            });
        
        
        
        PubService dt = new PubService();
        List<Publication> s = PubService.getPublication();

        for (int i = 0; i < s.size(); i++) {
            final Publication publication = s.get(i);
            
            

            Container pubContainer = new Container(new BorderLayout());
            pubContainer.setUIID("publicationContainer");
            Label titre=  new Label(publication.getTitre());
            pubContainer.add(BorderLayout.NORTH, titre);

            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 200), true);
            String path = publication.getImages().get(0).getChemin();
            if (publication.getImages().get(0).getChemin().equals("") == false) {
                path = path.substring(path.lastIndexOf("/") + 1, path.length() - 1);
            }
            Image image = URLImage.createToStorage(placeholder, path, "http://localhost/tahwissa-web/web/" + publication.getImages().get(0).getChemin());
            SpanLabel contenu = new SpanLabel();
            contenu.setText(publication.getContenu());
            contenu.add(BorderLayout.NORTH, image.scaled(Display.getInstance().getDisplayWidth(), 200));
            Button btnOk = new Button("Lire la Suite ..."); btnOk.setUIID("MouveButton");
            btnOk.addPointerPressedListener((evt) -> {
                
             
                
                detailsPublication mp = new detailsPublication(publication);
            mp.init(current);
            mp.start();  
            });
            //titre.setFocusable(true);
            
            //contenu.setTextBlockAlign(Component.BOTTOM);

            pubContainer.add(BorderLayout.SOUTH, contenu);

            pubContainer.add(BorderLayout.CENTER, BorderLayout.east(new Label(publication.getDateHeurePublication().toString())));
            //pubContainer.setLeadComponent(titre);
            hi.add(pubContainer);

            
            hi.add(btnOk);

        }

        hi.show();

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

}
