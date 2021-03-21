package pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//POJO class to retrieve list of drivers
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drivers {

	private List<Driver> drivers;

	@JsonProperty("Drivers")
	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

}
