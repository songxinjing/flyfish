package com.songxinjing.flyfish.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Joom商品实体类
 * 
 * @author songxinjing
 *
 */
@Entity
public class JoomProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 32)
	private String parentSku;
	
	@Column(length = 64)
	private String joomId;
	
	@Column(length = 256)
	private String mainImage;
	
	@Column(length = 8)
	private String isPromoted;
	
	@Column(length = 512)
	private String name;
	
	@Column(length = 1024)
	private String tags;
	
	@Column(length = 16)
	private String reviewStatus;
	
	@Column(length = 2048)
	private String extraImages;
	
	@Column(length = 8)
	private String numberSaves;
	
	@Column(length = 8)
	private String numberSold;
	
	@Column(length = 32)
	private String dateUploaded;
	
	@Column(length = 102400)
	private String description;
	
	@ManyToOne
	@JoinColumn
	private JoomStore store;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	private Set<JoomVariant> variants;

	public String getParentSku() {
		return parentSku;
	}

	public void setParentSku(String parentSku) {
		this.parentSku = parentSku;
	}

	public String getJoomId() {
		return joomId;
	}

	public void setJoomId(String joomId) {
		this.joomId = joomId;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getIsPromoted() {
		return isPromoted;
	}

	public void setIsPromoted(String isPromoted) {
		this.isPromoted = isPromoted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getExtraImages() {
		return extraImages;
	}

	public void setExtraImages(String extraImages) {
		this.extraImages = extraImages;
	}

	public String getNumberSaves() {
		return numberSaves;
	}

	public void setNumberSaves(String numberSaves) {
		this.numberSaves = numberSaves;
	}

	public String getNumberSold() {
		return numberSold;
	}

	public void setNumberSold(String numberSold) {
		this.numberSold = numberSold;
	}

	public String getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(String dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<JoomVariant> getVariants() {
		return variants;
	}

	public void setVariants(Set<JoomVariant> variants) {
		this.variants = variants;
	}

	public JoomStore getStore() {
		return store;
	}

	public void setStore(JoomStore store) {
		this.store = store;
	}

}