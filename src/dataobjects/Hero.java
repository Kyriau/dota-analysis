package dataobjects;

import org.json.JSONObject;

public class Hero {

	private int id;
	private String name;
	private String localizedName;
	
	private String primaryAttribute;
	private String attackType;
	
	public Hero(
		int id,
		String name,
		String localizedName,
		String primaryAttribute,
		String attackType
	) {
		this.id = id;
		this.name = name;
		this.localizedName = localizedName;
		this.primaryAttribute = primaryAttribute;
		this.attackType = attackType;
	}
	
	public Hero(JSONObject heroJson) {
		id = heroJson.getInt("id");
		name = heroJson.getString("name");
		localizedName = heroJson.getString("localized_name");
		primaryAttribute = heroJson.getString("primary_attr");
		attackType = heroJson.getString("attack_type");
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocalizedName() {
		return localizedName;
	}
	
	public String getPrimaryAttribute() {
		return primaryAttribute;
	}
	
	public String getAttackType() {
		return attackType;
	}
	
}