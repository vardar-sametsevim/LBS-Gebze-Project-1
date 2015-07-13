package com.lbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lbs.service.JobService;
import com.lbs.service.TeamService;
import com.lbs.service.UserService;
import com.lbs.vaadin.view.JobView;
import com.lbs.vaadin.view.TeamView;
import com.lbs.vaadin.view.UserView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")  // vaadin theme
@Widgetset("com.lbs.LBSWidgetSet")
@SpringUI(path="lbs")  //spring boot & vaadin
@Component(value="ui")  // spring component
@PreserveOnRefresh
public class VaadinUI extends UI{

	@Autowired
	private UserService userService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private JobService jobService;

	private Navigator navigator;



	@Override
	protected void init(VaadinRequest request) {


		VerticalLayout mainLayout = new VerticalLayout();
		HorizontalLayout headerLayout = new HorizontalLayout();
		VerticalLayout contentLayout = new VerticalLayout();

		//Header Buttons
		Button userPageButon = new Button("User");
		Button teamPageButton = new Button("Team");

		//add buttons to headerlayout
		headerLayout.addComponent(userPageButon);
		headerLayout.addComponent(teamPageButton);

		//headerlayout alignment

		//		headerLayout.setWidth("200px");
		//		headerLayout.setWidth(100, Unit.INCH);

		headerLayout.setWidth("100%");
		headerLayout.setComponentAlignment(userPageButon, Alignment.MIDDLE_LEFT);
		headerLayout.setComponentAlignment(teamPageButton, Alignment.MIDDLE_CENTER);

		//style
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);


		//add layouts to mainlayout



		mainLayout.addComponent(headerLayout);
		mainLayout.addComponent(contentLayout);


		navigator = new Navigator(VaadinUI.this,
				contentLayout);

		navigator.addView("team", TeamView.class);
		navigator.addView("user", UserView.class);
		navigator.addView("job", JobView.class);

		// not found page
		navigator.setErrorView(UserView.class);

		Button jobPageButton = new Button("Job");
		headerLayout.addComponent(jobPageButton);
		headerLayout.setComponentAlignment(jobPageButton, Alignment.MIDDLE_CENTER);
		jobPageButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("job");
			}
		});
		
		
		Button customJobPageButton = new Button("Custom Job");
		headerLayout.addComponent(customJobPageButton);
		headerLayout.setComponentAlignment(customJobPageButton, Alignment.MIDDLE_RIGHT);
		customJobPageButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("job/custom");
			}
		});
		
		


		//button listeners
		userPageButon.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				navigator.navigateTo("user");
			}
		});

		teamPageButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("team");

			}
		});




		//view change event- asnyc tasks


		//		navigator.addViewChangeListener(new ViewChangeListener() {
		//			
		//			@Override
		//			public boolean beforeViewChange(ViewChangeEvent event) {
		//				// TODO Auto-generated method stub
		//				return false;
		//			}
		//			
		//			@Override
		//			public void afterViewChange(ViewChangeEvent event) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});

		//set window content
		setContent(mainLayout);
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public TeamService getTeamService() {
		return teamService;
	}


	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public static VaadinUI get(){
		return (VaadinUI) VaadinUI.getCurrent();
	}


	public JobService getJobService() {
		return jobService;
	}


	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}


	public Navigator getNavigator() {
		return navigator;
	}


	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
