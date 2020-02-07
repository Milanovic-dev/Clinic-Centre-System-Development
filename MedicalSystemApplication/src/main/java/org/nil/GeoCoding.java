package org.nil;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GeoCoding {
	String status;
	String error_message;
	
	@JsonProperty(value="results")
	GeoCodingResult[] geoCodingResults;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public GeoCodingResult[] getGeoCodingResults() {
		return geoCodingResults;
	}

	public void setGeoCodingResults(GeoCodingResult[] geoCodingResults) {
		this.geoCodingResults = geoCodingResults;
	}

	@Override
	public String toString() {
		return "GeoCoding [errorMessage="+error_message+", status=" + status + ", geoCodingResults=" + Arrays.toString(geoCodingResults) + "]";
	}
}
