package tr.biz.vardar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Item {

	
	private String name;
	
	private int count;
	
	private String type;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Item(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}
	
	public Item() {
		super();
	}
	
	public static List<Item> createDummyData
	(int count){
		List<Item> itemList = new 
				ArrayList<Item>();
		
		for(int i = 0; i<count ; i++){
			Item item = new Item
					(UUID.randomUUID() +"",
							i+1);
			itemList.add(item);
		}
		return itemList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", count=" + count + ", type=" + type + "]";
	}
	
}
