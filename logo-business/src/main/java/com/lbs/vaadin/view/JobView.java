package com.lbs.vaadin.view;

import java.util.Date;
import java.util.Random;

import org.tepi.filtertable.FilterTable;
import org.vaadin.alump.distributionbar.DistributionBar;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;

import com.lbs.VaadinUI;
import com.lbs.model.Job;
import com.lbs.model.JobViewModel;
import com.lbs.service.JobService;
import com.lbs.vaadin.form.JobForm;
import com.lbs.vaadin.form.UserSelect;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class JobView extends VerticalLayout implements View{


	private JobService jobService;
	private FilterTable table;
	private JobForm jobForm;
	public JobView() {
		initLayout();
	}

	private void initLayout(){

		jobService = VaadinUI.get().getJobService();
		jobForm = new JobForm();
		Button button = new Button("New Job");
		button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				jobForm.setEntity(new Job());
				jobForm.openInModalPopup();
			}
		});
		addComponent(button);

		jobForm.setSavedHandler(new SavedHandler<Job>() {
			@Override
			public void onSave(Job entity) {
				jobService.saveJob(entity);
				jobForm.getPopup().close();
				fillTable();
			}
		});

		table = new FilterTable();
		table.setFilterBarVisible(true);


		table.addContainerProperty("status", DistributionBar.class, null);




		table.addContainerProperty("name", String.class, null);
		table.addContainerProperty("description", Label.class, null);
		table.addContainerProperty("start", Date.class, null);
		table.addContainerProperty("end", Date.class, null);
		table.addContainerProperty("username", Button.class, null);
		table.addContainerProperty("team", String.class, null);
		table.addContainerProperty("userview", Button.class, null);

		fillTable();
		table.setWidth("100%");
		addComponent(table);

	}

	private void fillTable(){
		table.removeAllItems();
		for(Job job : jobService.findAllJobs()){
			Object id = table.addItem();
			table.getContainerProperty(id, "name").setValue(job.getName());
			Label label = new Label(job.getDescription().length()>20 ? 
					job.getDescription().substring(0, 19) : job.getDescription());

			label.setDescription(job.getDescription());

			DistributionBar distributionBar = new DistributionBar(2);
			distributionBar.setWidth("100%");
			int total = 100;
			Random random = new Random();
			int completed = random.nextInt(100);
			distributionBar.setPartSize(0, completed);
			distributionBar.setPartSize(1, total-completed);
			distributionBar.setPartTooltip(0, "Toplam işin şu ana kadar %" + completed + " kısmı tamamlandı");
			distributionBar.setPartTooltip(1, "Geriye kalan iş, tüm işin %" + (100-completed) +" kısmı");
			table.getContainerProperty(id, "status").setValue(distributionBar);



			table.getContainerProperty(id, "description").setValue(label);
			table.getContainerProperty(id, "start").setValue(job.getStartDate());
			table.getContainerProperty(id, "end").setValue(job.getEndDate());


			Button usernameButton = new Button(job.getUser()+" -Detail");
			usernameButton.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {

					VerticalLayout contentLayout = new VerticalLayout();
					contentLayout.setSpacing(true);
					contentLayout.setMargin(true);
					Window window = new Window("User Edit", contentLayout);
					window.center();
					UserSelect userSelect = new UserSelect("Users");
					userSelect.setBeans(VaadinUI.get().getUserService().findAll());
					Button saveButton = new Button("Update");
					saveButton.addClickListener(new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {
							if(userSelect.getValue()!=null){
								job.setUser(userSelect.getValue());
								VaadinUI.get().getJobService().saveJob(job);
								window.close();
								fillTable();
							}
						}
					});
					contentLayout.addComponent(userSelect);
					contentLayout.addComponent(saveButton);

					VaadinUI.get().addWindow(window);
				}
			});
			table.getContainerProperty(id, "username").setValue(usernameButton);
			table.getContainerProperty(id, "team").setValue(job.getUser().getTeam()+"");


			Button userViewButton = new Button("User Detail View");
			userViewButton.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					VaadinUI.get().
					getNavigator().
					navigateTo
					("user/"+job.getUser().getId());
				}
			});
			table.getContainerProperty(id, "userview").setValue(userViewButton);

		}
		table.resetFilters();
	}
	@Override
	public void enter(ViewChangeEvent event) {
		String parameters = event.getParameters();
		System.out.println(parameters);
		if(parameters.length()>0 && parameters.equals("custom")){
			removeAllComponents();
			MTable<JobViewModel> viewTable = new MTable<JobViewModel>(JobViewModel.class).
					                            withProperties("name","description","username","teamname");
			viewTable.setWidth("100%");
			viewTable.setBeans(jobService.customQueryResults());
			addComponent(viewTable);
			
		}
		else{
			removeAllComponents();
			initLayout();
			fillTable();
		}


	}

}
