package com.essentia.essentiauser.dto;

public class PerfumeSummaryDto {

    private int id;
    private String name;
    private String brandName;
    private String imageUrl;

    public PerfumeSummaryDto() {}

    public PerfumeSummaryDto(int id, String name, String brandName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
