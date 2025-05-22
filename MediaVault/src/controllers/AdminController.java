package controllers;

import models.AdminModel;

public class AdminController {
	
    private AdminModel model;

    public AdminController() {
        model = new AdminModel();
    }

    public boolean autenticar(String user, String pass) {
        return model.access(user, pass);
    }
}