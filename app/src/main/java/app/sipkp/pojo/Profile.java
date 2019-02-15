package app.sipkp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Profile {
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("instansi")
    @Expose
    private String instansi;
    @SerializedName("pembimbing")
    @Expose
    private String pembimbing;
    @SerializedName("mulai_kp")
    @Expose
    private String mulaiKp;
    @SerializedName("selesai_kp")
    @Expose
    private String selesaiKp;
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }

    public String getPembimbing() {
        return pembimbing;
    }

    public void setPembimbing(String pembimbing) {
        this.pembimbing = pembimbing;
    }

    public String getMulaiKp() {
        return mulaiKp;
    }

    public void setMulaiKp(String mulaiKp) {
        this.mulaiKp = mulaiKp;
    }

    public String getSelesaiKp() {
        return selesaiKp;
    }

    public void setSelesaiKp(String selesaiKp) {
        this.selesaiKp = selesaiKp;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
