public class Design {
    private String title_design;
    private String image_design;
    private String description_design;
    private String patologies;
    private String title_family;

    public Design(String title_design, String image_design, String description_design, String patologies) {
        this.title_design = title_design;
        this.image_design = image_design;
        this.description_design = description_design;
        this.patologies = patologies;
    }

    public String getTitle_design() {
        return title_design;
    }

    public String getImage_design() {
        return image_design;
    }

    public String getDescription_design() {
        return description_design;
    }

    public String getPatologies() {
        return patologies;
    }

    public String getTitle_family() {
        return title_family;
    }

    public void setTitle_family(String title_family) {
        this.title_family = title_family;
    }
}
