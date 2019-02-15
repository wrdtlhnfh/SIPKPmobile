package app.sipkp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Task {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("kegiatan")
    @Expose
    private String kegiatan;
    @SerializedName("catatan")
    @Expose
    private String catatan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
