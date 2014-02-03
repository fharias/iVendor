package com.fabioarias.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Data is a simple Java Bean that is used to hold Name, Detail and
 * image pairs.
 */
public class Data
{

	/** The title. */
	private String title;

	/** The description. */
	private String desc;

	/** The image resource id. */
	private int image;

	/**
	 * Instantiates a new feed class.
	 * 
	 * @param title
	 *            the title
	 * @param desc
	 *            the desc
	 * @param image
	 *            the image
	 */
	public Data(String title, String desc, int image)
	{
		this.title = title;
		this.desc = desc;
		this.image = image;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle()
	{

		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title)
	{

		this.title = title;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDesc()
	{

		return desc;
	}

	/**
	 * Sets the description.
	 * 
	 * @param desc
	 *            the new description
	 */
	public void setDesc(String desc)
	{

		this.desc = desc;
	}

	/**
	 * Gets the image resource id..
	 * 
	 * @return the image resource id.
	 */
	public int getImage()
	{

		return image;
	}

	/**
	 * Sets the image resource id..
	 * 
	 * @param image
	 *            the new image resource id.
	 */
	public void setImage(int image)
	{

		this.image = image;
	}

}
