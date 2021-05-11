package com.example.challenge_farm_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Animal implements Parcelable {

        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("parcode")
        private String parcode;
    @SerializedName("farm_number")
        private String farm_number;
    @SerializedName("date_of_birth")
        private String date_of_birth;
    @SerializedName("age_y")
    private Integer age_y;
    @SerializedName("age_m")
    private Integer age_m;
    @SerializedName("age_d")
    private Integer age_d;
    @SerializedName("weight")
    private Double weight;
    @SerializedName("sex")
    private String sex;
    @SerializedName("mother_num")
    private String mother_num;
    @SerializedName("childern_num")
    private Integer childern_num;
    @SerializedName("farm_id")
    private Integer farm_id;
    @SerializedName("solala_id")
    private Integer solala_id;
    @SerializedName("jawda_id")
    private Integer jawda_id;
    @SerializedName("sell_price")
    private Double sell_price;
    @SerializedName("cost_price")
    private Double cost_price;
    @SerializedName("fetam_weight")
    private Double fetam_weight;
    @SerializedName("milk_amount")
    private Double milk_amount;
    @SerializedName("weladat_num")
    private Double weladat_num;
    @SerializedName("twins_avarage")
    private Double twins_avarage;
    @SerializedName("status1")
    private String status1;
    @SerializedName("status2")
    private String status2;
    @SerializedName("note")
    private String note;
    @SerializedName("status_date")
    private String status_date;
    @SerializedName("fetam_done")
    private String fetam_done;
    @SerializedName("to3om_done")
    private String to3om_done;

    public Animal(Integer id, String name, String parcode, String farm_number, String date_of_birth, Integer age_y, Integer age_m, Integer age_d, Double weight, String sex, String mother_num, Integer childern_num, Integer farm_id, Integer solala_id, Integer jawda_id, Double sell_price, Double cost_price, Double fetam_weight, Double milk_amount, Double weladat_num, Double twins_avarage, String status1, String status2, String note, String status_date, String fetam_done, String to3om_done) {
        this.id = id;
        this.name = name;
        this.parcode = parcode;
        this.farm_number = farm_number;
        this.date_of_birth = date_of_birth;
        this.age_y = age_y;
        this.age_m = age_m;
        this.age_d = age_d;
        this.weight = weight;
        this.sex = sex;
        this.mother_num = mother_num;
        this.childern_num = childern_num;
        this.farm_id = farm_id;
        this.solala_id = solala_id;
        this.jawda_id = jawda_id;
        this.sell_price = sell_price;
        this.cost_price = cost_price;
        this.fetam_weight = fetam_weight;
        this.milk_amount = milk_amount;
        this.weladat_num = weladat_num;
        this.twins_avarage = twins_avarage;
        this.status1 = status1;
        this.status2 = status2;
        this.note = note;
        this.status_date = status_date;
        this.fetam_done = fetam_done;
        this.to3om_done = to3om_done;
    }

    protected Animal(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        parcode = in.readString();
        farm_number = in.readString();
        date_of_birth = in.readString();
        if (in.readByte() == 0) {
            age_y = null;
        } else {
            age_y = in.readInt();
        }
        if (in.readByte() == 0) {
            age_m = null;
        } else {
            age_m = in.readInt();
        }
        if (in.readByte() == 0) {
            age_d = null;
        } else {
            age_d = in.readInt();
        }
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readDouble();
        }
        sex = in.readString();
        mother_num = in.readString();
        if (in.readByte() == 0) {
            childern_num = null;
        } else {
            childern_num = in.readInt();
        }
        if (in.readByte() == 0) {
            farm_id = null;
        } else {
            farm_id = in.readInt();
        }
        if (in.readByte() == 0) {
            solala_id = null;
        } else {
            solala_id = in.readInt();
        }
        if (in.readByte() == 0) {
            jawda_id = null;
        } else {
            jawda_id = in.readInt();
        }
        if (in.readByte() == 0) {
            sell_price = null;
        } else {
            sell_price = in.readDouble();
        }
        if (in.readByte() == 0) {
            cost_price = null;
        } else {
            cost_price = in.readDouble();
        }
        if (in.readByte() == 0) {
            fetam_weight = null;
        } else {
            fetam_weight = in.readDouble();
        }
        if (in.readByte() == 0) {
            milk_amount = null;
        } else {
            milk_amount = in.readDouble();
        }
        if (in.readByte() == 0) {
            weladat_num = null;
        } else {
            weladat_num = in.readDouble();
        }
        if (in.readByte() == 0) {
            twins_avarage = null;
        } else {
            twins_avarage = in.readDouble();
        }
        status1 = in.readString();
        status2 = in.readString();
        note = in.readString();
        status_date = in.readString();
        fetam_done = in.readString();
        to3om_done = in.readString();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    @Override
    public String toString() {
        return  "id=" + id +
                "\nname='" + name + '\'' +
                "\nparcode='" + parcode + '\'' +
                "\nfarm_number='" + farm_number + '\'' +
                "\ndate_of_birth='" + date_of_birth + '\'' +
                "\nage_y=" + age_y +
                "\nage_m=" + age_m +
                "\nage_d=" + age_d +
                "\nweight=" + weight +
                "\nsex='" + sex + '\'' +
                "\nmother_num='" + mother_num + '\'' +
                "\nchildern_num=" + childern_num +
                "\nfarm_id=" + farm_id +
                "\nsolala_id=" + solala_id +
                "\njawda_id=" + jawda_id +
                "\nsell_price=" + sell_price +
                "\ncost_price=" + cost_price +
                "\nfetam_weight=" + fetam_weight +
                "\nmilk_amount=" + milk_amount +
                "\nweladat_num=" + weladat_num +
                "\ntwins_avarage=" + twins_avarage +
                "\nstatus1='" + status1 + '\'' +
                "\nstatus2='" + status2 + '\'' +
                "\nnote='" + note + '\'' +
                "\nstatus_date='" + status_date + '\'' +
                "\nfetam_done='" + fetam_done + '\'' +
                "\nto3om_done='" + to3om_done + '\'';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParcode() {
        return parcode;
    }

    public void setParcode(String parcode) {
        this.parcode = parcode;
    }

    public String getFarm_number() {
        return farm_number;
    }

    public void setFarm_number(String farm_number) {
        this.farm_number = farm_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Integer getAge_y() {
        return age_y;
    }

    public void setAge_y(Integer age_y) {
        this.age_y = age_y;
    }

    public Integer getAge_m() {
        return age_m;
    }

    public void setAge_m(Integer age_m) {
        this.age_m = age_m;
    }

    public Integer getAge_d() {
        return age_d;
    }

    public void setAge_d(Integer age_d) {
        this.age_d = age_d;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMother_num() {
        return mother_num;
    }

    public void setMother_num(String mother_num) {
        this.mother_num = mother_num;
    }

    public Integer getChildern_num() {
        return childern_num;
    }

    public void setChildern_num(Integer childern_num) {
        this.childern_num = childern_num;
    }

    public Integer getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(Integer farm_id) {
        this.farm_id = farm_id;
    }

    public Integer getSolala_id() {
        return solala_id;
    }

    public void setSolala_id(Integer solala_id) {
        this.solala_id = solala_id;
    }

    public Integer getJawda_id() {
        return jawda_id;
    }

    public void setJawda_id(Integer jawda_id) {
        this.jawda_id = jawda_id;
    }

    public Double getSell_price() {
        return sell_price;
    }

    public void setSell_price(Double sell_price) {
        this.sell_price = sell_price;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }

    public Double getFetam_weight() {
        return fetam_weight;
    }

    public void setFetam_weight(Double fetam_weight) {
        this.fetam_weight = fetam_weight;
    }

    public Double getMilk_amount() {
        return milk_amount;
    }

    public void setMilk_amount(Double milk_amount) {
        this.milk_amount = milk_amount;
    }

    public Double getWeladat_num() {
        return weladat_num;
    }

    public void setWeladat_num(Double weladat_num) {
        this.weladat_num = weladat_num;
    }

    public Double getTwins_avarage() {
        return twins_avarage;
    }

    public void setTwins_avarage(Double twins_avarage) {
        this.twins_avarage = twins_avarage;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus_date() {
        return status_date;
    }

    public void setStatus_date(String status_date) {
        this.status_date = status_date;
    }

    public String getFetam_done() {
        return fetam_done;
    }

    public void setFetam_done(String fetam_done) {
        this.fetam_done = fetam_done;
    }

    public String getTo3om_done() {
        return to3om_done;
    }

    public void setTo3om_done(String to3om_done) {
        this.to3om_done = to3om_done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        parcel.writeString(parcode);
        parcel.writeString(farm_number);
        parcel.writeString(date_of_birth);
        if (age_y == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(age_y);
        }
        if (age_m == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(age_m);
        }
        if (age_d == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(age_d);
        }
        if (weight == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(weight);
        }
        parcel.writeString(sex);
        parcel.writeString(mother_num);
        if (childern_num == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(childern_num);
        }
        if (farm_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(farm_id);
        }
        if (solala_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(solala_id);
        }
        if (jawda_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(jawda_id);
        }
        if (sell_price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(sell_price);
        }
        if (cost_price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(cost_price);
        }
        if (fetam_weight == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(fetam_weight);
        }
        if (milk_amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(milk_amount);
        }
        if (weladat_num == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(weladat_num);
        }
        if (twins_avarage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(twins_avarage);
        }
        parcel.writeString(status1);
        parcel.writeString(status2);
        parcel.writeString(note);
        parcel.writeString(status_date);
        parcel.writeString(fetam_done);
        parcel.writeString(to3om_done);
    }
}
