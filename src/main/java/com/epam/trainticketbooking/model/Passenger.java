package com.epam.trainticketbooking.model;

public class Passenger {
	private long id;
	private String name;
	private String gender;
	private String mobile;
	
	public Passenger(long id, String name, String gender, String mobile) {
		this(name, gender, mobile);
		this.id = id;
	}
	public Passenger(String name, String gender, String mobile) {
		super();
		this.name = name;
		this.gender = gender;
		this.mobile = mobile;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", name=" + name + ", gender=" + gender + ", mobile=" + mobile + "]";
	}

	@Override
	public int hashCode() {
		return (int)this.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if (!(obj instanceof Passenger)) {
			return false;
		}
		Passenger other = (Passenger) obj;
		return other.getId() == this.getId();
	}
}

