package com.lbs.vaadin.form;

import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.lbs.model.Job;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class JobForm extends AbstractForm<Job>{

	
	private TextField name = new TextField("Name");
	private TextArea description = new TextArea("Description");
	private DateField startDate = new DateField("StartDate");
	private DateField endDate = new DateField("EndDate");
	private UserSelect user = new UserSelect("User");
	public JobForm() {
		
	}
	
	@Override
	protected Component createContent() {
		return new MVerticalLayout
				(new FormLayout
						(name,description,startDate,
								endDate,user,createButtonLayout()));
	}
	
	private HorizontalLayout createButtonLayout(){
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Button saveButon = new Button("Save");
		saveButon.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {

				save(event);
			}
		});
		horizontalLayout.addComponent(saveButon);
		return horizontalLayout;
	}
	
	

}
