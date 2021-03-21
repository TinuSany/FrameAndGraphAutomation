package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//POJO class used for deserialization of the driver information

//@JsonIgnoreProperties({ "permanentNumber", "code", "season", "round" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Driver {

	private String driverId;
	private String url;
	private String givenName;
	private String familyName;
	private String dateOfBirth;
	private String nationality;
	// @JsonIgnore
	private String permanentNumber;
	private String code;

	public Driver(String driverId, String url, String givenName, String familyName, String dateOfBirth,
			String nationality, String permanentNumber, String code) {
		super();
		this.driverId = driverId;
		this.url = url;
		this.givenName = givenName;
		this.familyName = familyName;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
		this.permanentNumber = permanentNumber;
		this.code = code;
	}

	public Driver() {
	}

	public String getPermanentNumber() {
		return permanentNumber;
	}

	public void setPermanentNumber(String permanentNumber) {
		this.permanentNumber = permanentNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public boolean equals(Object o) {
		Driver p = (Driver) o;
		boolean result = false;
		if (this.driverId.equals(p.driverId) && this.url.equals(p.url) && this.givenName.equals(p.givenName)
				&& this.familyName.equals(p.familyName) && this.dateOfBirth.equals(p.dateOfBirth)
				&& this.nationality.equals(p.nationality))
			result = true;
		if (this.permanentNumber == null) {
			if (this.permanentNumber != p.permanentNumber)
				result = false;
		} else {
			if (!this.permanentNumber.equals(p.permanentNumber))
				result = false;
		}

		if (this.code == null) {
			if (this.code != p.code)
				result = false;
		} else {
			if (!this.code.equals(p.code))
				result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (driverId != null) {
			result = 31 * result + driverId.hashCode();
		}
		if (url != null) {
			result = 31 * result + url.hashCode();
		}

		if (givenName != null) {
			result = 31 * result + givenName.hashCode();
		}

		if (familyName != null) {
			result = 31 * result + familyName.hashCode();
		}

		if (dateOfBirth != null) {
			result = 31 * result + dateOfBirth.hashCode();
		}

		if (nationality != null) {
			result = 31 * result + nationality.hashCode();
		}

		if (permanentNumber != null) {
			result = 31 * result + permanentNumber.hashCode();
		}

		if (code != null) {
			result = 31 * result + code.hashCode();
		}
		return result;
	}

}
