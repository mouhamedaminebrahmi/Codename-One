/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.util.Iterator;
import tn.esprit.tahwissa.MyApplication;

/**
 *
 * @author Meedoch
 */
public class MenuManager {

    public static void createMenu(Form f, Resources theme) {
        String uuid = "sideMenuCommandCenter";

        f.getToolbar().addCommandToSideMenu("", theme.getImage("user.png").scaled(150, 150), (evt) -> {

        });
        f.getToolbar().addCommandToSideMenu("Mehdi Ben Hemdene", null, (evt) -> {

        });

        //System.out.println(f.getToolbar().getComponentAt(3).getUIID());
        //System.out.println(b);
        f.getToolbar().addCommandToSideMenu("Evenements", theme.getImage("event.png").scaled(20, 20), (evt) -> {
            MyApplication m = new MyApplication();
            m.init(f);
            m.start();
        });
        f.getToolbar().addCommandToSideMenu("Publications", theme.getImage("publication.png").scaled(20, 20), (evt) -> {
            MyApplication m = new MyApplication();
            m.init(f);
            m.start();

        });
        f.getToolbar().addCommandToSideMenu("Boutique", theme.getImage("boutique.png").scaled(20, 20), (evt) -> {

        });
        f.getToolbar().addCommandToSideMenu("Messages", theme.getImage("messages.png").scaled(20, 20), (evt) -> {

        });
        f.getToolbar().addCommandToSideMenu("Profile", theme.getImage("profile.png").scaled(20, 20), (evt) -> {

        });
        
       
    }

}
