package com.sarahlatifahmarif.pantauconrona.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DayEachKabResponse{

	@SerializedName("results")
	private List<ResultsItem> results;

	public List<ResultsItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"DayEachKabResponse{" + 
			"results = '" + results + '\'' + 
			"}";
		}
}