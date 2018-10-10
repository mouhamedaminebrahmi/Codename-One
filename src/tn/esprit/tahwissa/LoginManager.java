/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import entity.Compte;
import entity.User;

/**
 *
 * @author Meedoch
 */
public class LoginManager {
    public static User getUser(){
        User u = new User();
        u.setId(1);
        u.setEmail("aymen@esprit.tn");
        Compte c = new Compte();
        c.setId(1);
        c.setPasscode("1234");
        c.setSolde(30.0);
        u.setCompte(c);
        return u;
    }
}
