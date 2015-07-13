package com.lbs.vaadin.view;

import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MValueChangeEvent;
import org.vaadin.viritin.fields.MValueChangeListener;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.lbs.VaadinUI;
import com.lbs.model.User;
import com.lbs.service.UserService;
import com.lbs.vaadin.form.UserForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserView extends VerticalLayout implements View{

	private UserForm userForm;
	private UserService userService;
	public UserView() {

		initLayout();
	}

	private void initLayout(){
		userService = VaadinUI.get().getUserService();
		userForm = new UserForm();
		removeAllComponents();
		// test label
		//		Label label = new Label("User View works");
		//		addComponent(label);

		Button newUser = new Button("New User");
		newUser.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				userForm.setEntity(new User());
				userForm.openInModalPopup();
			}
		});
		addComponent(newUser);

		MTable<User> userTable = 
				new MTable<User>(User.class).
				withProperties("firstname","lastname"
						,"birthdate","team");
		userTable.setWidth("100%");
		userTable.setBeans(userService.findAll());
		addComponent(userTable);


		userForm.setSavedHandler(new SavedHandler<User>() {

			@Override
			public void onSave(User entity) {

				userService.saveUser(entity);
				userForm.getPopup().close();
				userTable.setBeans(userService.findAll());
			}
		});
		userTable.addMValueChangeListener(new MValueChangeListener<User>() {

			@Override
			public void valueChange(MValueChangeEvent<User> event) {
				if(event.getValue()!=null){
					userForm.setEntity(event.getValue());
					userForm.openInModalPopup();
				}
			}
		});

	}


	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		String parameter = event.getParameters();
		if(parameter.length()>0){
			User user = VaadinUI.get().getUserService().findUser(Long.parseLong(parameter));
			if(user!=null){
				Label label1 = new Label(user.toString());
				Label label2 = new Label(user.getTeam().getTeamname());
				label1.setStyleName(ValoTheme.LABEL_TINY);
				label2.setStyleName(ValoTheme.LABEL_BOLD);
				addComponent(label1);
				addComponent(label2);
			}
			else{
				Label label = new Label("Unknown User");
				label.setStyleName(ValoTheme.LABEL_FAILURE);
				addComponent(label);
			}
		}
		else{
			initLayout();	
		}

	}

}
