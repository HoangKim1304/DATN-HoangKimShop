package com.shoppingwithme.hoangkimshop.common;

import java.util.Objects;

public class ImageInfo {

	private String name;
	  private String url;

	  public ImageInfo(String name, String url) {
	    this.name = name;
	    this.url = url;
	  }

	  public String getName() {
	    return this.name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getUrl() {
	    return this.url;
	  }

	  public void setUrl(String url) {
	    this.url = url;
	  }

	@Override
	public int hashCode() {
		return Objects.hash(name, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageInfo other = (ImageInfo) obj;
		return Objects.equals(name, other.name);
	}
	  
}
