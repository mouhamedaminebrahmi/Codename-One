/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import entity.Publication;
import service.PubService;

/**
 *
 * @author BRAHMI
 */
public class AjouterPublication {

    private Form current;
    private Resources theme;
    Publication publication;
    Component c;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme_1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    String pathp;
    Image img;

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

        Form f1;
        UIBuilder uib = new UIBuilder();
        Container cnt = uib.createContainer(theme, "ajoutPublication");
        f1 = (Form) cnt;
        f1.getToolbar().setUIID("MouveButton");
        f1.getToolbar().addCommandToLeftBar("Back", theme.getImage("back-command.png"), (event) -> {
            MyApplication mp = new MyApplication();
            mp.init(current);
            mp.start();
        });

        f1.setTitle("Ajouter Publication");

        TextField titre = (TextField) uib.findByName("titre", cnt);
        TextArea contenu = (TextArea) uib.findByName("contenu", cnt);
        TextField videoUrl = (TextField) uib.findByName("video", cnt);

        Button btnimage = (Button) uib.findByName("submit", cnt);

        Label lbl = (Label) uib.findByName("Label4", cnt);
        Button btnchoisir = (Button) uib.findByName("choisir", cnt);
        btnchoisir.addActionListener(l -> {
            pathp = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);

            if (pathp != null) {
                try {
                    img = Image.createImage(pathp);
                    lbl.setIcon(img);
                } catch (Exception e) {
                }
            }
        });

        btnimage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
              
                Publication p = new Publication();
                PubService pub = new PubService();
                p.setTitre(titre.getText());
                p.setContenu(contenu.getText());
                p.setVideo_url(videoUrl.getText());
                p.setPublicitaire_id(LoginManager.getUser().getId());
                if (p.equals("success")) {
                    Dialog.show("Confirmation", "ajout avec succées", "Ok", null);
                    return;
                }
                pub.addPub(p, pathp);
            }
        });

        f1.show();
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
