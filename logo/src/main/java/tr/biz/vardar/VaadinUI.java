package tr.biz.vardar;

import org.tepi.filtertable.FilterTable;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;

import tr.biz.vardar.model.Item;



@Theme("valo")
@Title("Vaadin")
@Widgetset("tr.biz.vardar.LogoWidgetSet")
@SpringUI(path="vaadinui")
public class VaadinUI extends UI{


	private FilterTable table;
	private Window window;

	@Override
	protected void init(VaadinRequest request) {
		//		setContent(createTableBeanItemContainer());
		setContent(createContentLayot());
	}


	private VerticalLayout createContentLayot(){
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		Button newItemButton = new Button("New Item -Modal Window");
		verticalLayout.addComponent(newItemButton);

		//		newItemButton.addClickListener(e->createCrudLayout());

		newItemButton.addClickListener(new Button.ClickListener() {



			@Override
			public void buttonClick(ClickEvent event) {
				window = new Window("New Item CRUD Window");
				window.setContent(createCrudLayout());
				window.setModal(true);
				window.center();
				window.setResizable(false);

				VaadinUI.getCurrent().addWindow(window);

			}
		});

		table = new FilterTable();

		
		table.setFilterBarVisible(true);
		table.addContainerProperty("name", String.class, null);
		table.addContainerProperty("count", Label.class, null);
		table.addContainerProperty("action", HorizontalLayout.class, null);

		// invisible column
		table.addContainerProperty("obj", Item.class, null);
		//set visible columns
		table.setVisibleColumns(new Object[]{"name","count","action"});

		table.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {

				// !! vaadin data Item
				com.vaadin.data.Item item = event.getItem();


				//tr.biz.vardar.model.Item
				Item projectItem = (Item) item.getItemProperty("obj").getValue();


				// Way 1
				FormLayout formLayout = new FormLayout();
				TextField textField = new TextField("name");
				TextField textField2 = new TextField("count");
				textField.setValue(projectItem.getName());
				textField2.setValue(projectItem.getCount()+"");
				formLayout.addComponent(textField);
				formLayout.addComponent(textField2);
				Window window = new Window();
				window.setContent(formLayout);
				window.center();
				VaadinUI.getCurrent().addWindow(window);

				// Way 2 viritin
			}
		});


		table.setWidth("100%");
		table.setPageLength(5);
		table.setColumnHeader("name", "Item name");


		for(Item item : Item.createDummyData(100)){
			Object id = table.addItem();
			table.getItem(id).getItemProperty("obj").setValue(item);
			table.getItem(id).getItemProperty("name").setValue(item.getName());
			Label label = new Label(item.getCount()+"");
			if(item.getCount()%2==0){
				label.setStyleName(ValoTheme.LABEL_SUCCESS);
			}
			else{
				label.setStyleName(ValoTheme.LABEL_FAILURE);
			}
			
			label.setDescription(item.toString());
			table.getItem(id).getItemProperty("count").setValue(label);

			HorizontalLayout horizontalLayout = new HorizontalLayout();
			Button b1 = new Button("Edit");
			Button b2 = new Button("Delete");
			horizontalLayout.addComponent(b1);
			horizontalLayout.addComponent(b2);
			table.getItem(id).getItemProperty("action").setValue(horizontalLayout);

		}

		table.resetFilters();
		verticalLayout.addComponent(table);
		return verticalLayout;
	}


	private FormLayout createCrudLayout(){
		FormLayout formLayout = new FormLayout();
		TextField textField = new TextField("Item name");
		TextField textField2 = new TextField("Item Count");

		ComboBox comboBox = new ComboBox("Type");
		comboBox.addContainerProperty("caption", String.class, "");
		comboBox.addContainerProperty("dbId", String.class, "");
		comboBox.setItemCaptionPropertyId("caption");

		Object id = comboBox.addItem();
		comboBox.getContainerProperty(id, "caption").setValue("SAMPLE");
		comboBox.getContainerProperty(id, "dbId").setValue("1");


		id = comboBox.addItem();
		comboBox.getContainerProperty(id, "caption").setValue("PRODUCTION");
		comboBox.getContainerProperty(id, "dbId").setValue("2");


		id = comboBox.addItem();
		comboBox.getContainerProperty(id, "caption").setValue("DEMO");
		comboBox.getContainerProperty(id, "dbId").setValue("3");

		formLayout.addComponent(textField);
		formLayout.addComponent(textField2);
		formLayout.addComponent(comboBox);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		Button clearButton = new Button("Clear");
		Button saveButton = new Button("Save");

		horizontalLayout.setWidth("100%");

		horizontalLayout.addComponent(clearButton);
		horizontalLayout.addComponent(saveButton);

		//component allignment
		horizontalLayout.setComponentAlignment(clearButton, Alignment.MIDDLE_LEFT);
		horizontalLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_RIGHT);

		formLayout.addComponent(horizontalLayout);

		saveButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Item item = new Item();
				item.setName(textField.getValue()+"");
				item.setCount(Integer.parseInt(textField2.getValue()));
				String dbId = comboBox.getItem(comboBox.getValue()).getItemProperty("dbId").getValue()+"";
				item.setType(dbId);
				addItemToTable(item);
			}
		});

		textField2.setImmediate(true);
		textField2.addValidator(new IntegerRangeValidator("Number Required", 0, 9));

		TextField name = new TextField();

		return formLayout;
	}

	public void addItemToTable(Item item){
		Object id = table.addItem();
		table.getContainerProperty(id, "obj").setValue(item);
		table.getContainerProperty(id, "name").setValue(item.getName());
		table.getContainerProperty(id, "count").setValue(new Label(item.getCount()+""));
		window.addCloseListener(new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				
			}
		});
	}

}
