package com.xanxamobile.androidavanzado.data;

import java.util.Calendar;

/**
 * Representa un artículo del RSS.
 * @author Manuel Sánchez Palacios
 *
 */
public class RSSArticle {
	/**
	 * Título de la noticia.
	 * Cuerpo de la noticia
	 * Url de la Imagen de la noticia
	 */
	private String title, description, urlImage, link, author;
	
	/**
	 * Momento en el que se escribió la noticia
	 */
	private Calendar date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return la ruta completa de la imagen.
	 */
	public String getImage() {
		return urlImage;
	}

	public void setImage(String image) {
		this.urlImage = image;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getLink()
	{
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String toString(){
		return title + " " + description;
	}
	
	
}
